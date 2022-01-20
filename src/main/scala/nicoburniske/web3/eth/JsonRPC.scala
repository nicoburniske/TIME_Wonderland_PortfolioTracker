package nicoburniske.web3.eth

import java.math.{BigInteger, RoundingMode}

import monix.eval.Task
import nicoburniske.web3.contracts.{BentoboxV1, CauldronWMEMO, MEMO, WMEMO}
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.{FunctionEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert

/**
 * Queries Web3 JSON-RPC API for Ethereum & AVAX C-Chain.
 */
object JsonRPC {
  // Type alias to resolve name conflict with Scala standard library.
  type Web3Function = _root_.org.web3j.abi.datatypes.Function

  val AVALANCHE_C_CHAIN = "https://api.avax.network/ext/bc/C/rpc"
  val ETHEREUM_CHAIN    = "https://mainnet.infura.io/v3/5fe4e62a874540818356146a14d478b9"

  object Contracts {
    val WMEMO = "0x0da67235dd5787d67955420c84ca1cecd4e5bb3b"
    val MEMO  = "0x136acd46c134e8269052c62a67042d6bdedde3c9"
    val MIM   = "0x130966628846bfd36ff31a822705796e8cb8c18d"

    val BENTOBOX       = "0xf4F46382C2bE1603Dc817551Ff9A7b333Ed1D18f"
    val WMEMO_CAULDRON = "0x35fA7A723B3B39f15623Ff1Eb26D8701E7D6bB21"
  }

  val avaxWeb3 = Web3j.build(new HttpService(AVALANCHE_C_CHAIN))
  val ethWeb3  = Web3j.build(new HttpService(ETHEREUM_CHAIN))

  val bunkAddress           = "0x" + (0 until 40).map(_ => 0).mkString
  val transactionManager    = new ClientTransactionManager(avaxWeb3, bunkAddress)
  val gasProvider           = new DefaultGasProvider()
  val wMemoContract         = WMEMO.load(Contracts.WMEMO, avaxWeb3, transactionManager, gasProvider)
  val memoContract          = MEMO.load(Contracts.MEMO, avaxWeb3, transactionManager, gasProvider)
  val bentoboxContract      = BentoboxV1.load(Contracts.BENTOBOX, avaxWeb3, transactionManager, gasProvider)
  val wMemoCauldronContract = CauldronWMEMO.load(Contracts.WMEMO_CAULDRON, avaxWeb3, transactionManager, gasProvider)

  val defaultBlockParameter = DefaultBlockParameter.valueOf("latest")

  /**
   * Retrieves TIME Balance for the given wallet.
   *
   * Accounts for wrapped and non-wrapped wallet balance + wMEMO deposited into Abracadabra.money
   *
   * @param walletAddress
   *   wallet with TIME Balance.
   * @return
   *   Human-readable format of TIME Balance.
   */
  def getWalletTimeBalance(walletAddress: String): Task[BigDecimal] = {
    val oneAsWei       = Convert.toWei("1", Convert.Unit.ETHER)
    val nonWrappedTask = Task.from(memoContract.balanceOf(walletAddress).sendAsync())
    val wrappedTask    = Task.from(wMemoContract.balanceOf(walletAddress).sendAsync())
    val conversionTask = Task.from(wMemoContract.wMEMOToMEMO(oneAsWei.toBigInteger).sendAsync())
    val cauldronTask   = Task.from(wMemoCauldronContract.userCollateralShare(walletAddress).sendAsync())
    val bentoboxTask   = Task.from(bentoboxContract.balanceOf(Contracts.WMEMO, walletAddress).sendAsync())

    for {
      balances <- Task.parZip5(nonWrappedTask, wrappedTask, conversionTask, cauldronTask, bentoboxTask)

      (nonWrapped, wrapped, conversion, cauldron, bento) = balances

      bentoboxRequest     = bentoboxContract.toAmount(Contracts.WMEMO, cauldron.add(bento), false)
      bentoWrappedAmount <- Task.from(bentoboxRequest.sendAsync())
    } yield {
      val allWrapped  = bentoWrappedAmount.add(wrapped)
      val allWrapped2 = Convert.fromWei(allWrapped.toString, Convert.Unit.ETHER)
      val conversion2 = Convert.fromWei(conversion.toString, Convert.Unit.GWEI)
      val nonWrapped2 = Convert.fromWei(nonWrapped.toString, Convert.Unit.GWEI)

      val total = allWrapped2.multiply(conversion2).add(nonWrapped2)
      total
    }
  }

  def getMimsAvailable: Task[BigDecimal] = {
    Task
      .from(bentoboxContract.balanceOf(Contracts.MIM, Contracts.WMEMO_CAULDRON).sendAsync())
      .map(_.toString)
      .map(Convert.fromWei(_, Convert.Unit.ETHER))
      .map(_.setScale(3, RoundingMode.UP))
  }

  def getAVAXBalance(walletAddress: String): BigDecimal = getMainBalance(avaxWeb3, walletAddress)

  def getETHBalance(walletAddress: String): BigDecimal = getMainBalance(ethWeb3, walletAddress)

  def getMainBalance(web3: Web3j, walletAddress: String): BigDecimal = {
    val wei: BigInteger = web3.ethGetBalance(walletAddress, defaultBlockParameter).send().getBalance
    Convert.fromWei(wei.toString, Convert.Unit.ETHER)
  }

  def getContractWalletBalance(contractAddress: String, walletAddress: String): java.math.BigDecimal = {
    val function    = new Web3Function(
      "balanceOf",
      java.util.Arrays.asList(new Address(walletAddress)),
      java.util.Arrays.asList(new TypeReference[Uint256]() {})
    )
    val encoded     = FunctionEncoder.encode(function)
    val transaction = Transaction.createEthCallTransaction(walletAddress, contractAddress, encoded)
    val response    = avaxWeb3.ethCall(transaction, defaultBlockParameter).send().getValue()
    val asWei       = FunctionReturnDecoder.decode(response, function.getOutputParameters).get(0).getValue
    Convert.toWei(asWei.toString, Convert.Unit.WEI) // Wei -> Wei for type safety.
  }

  def getMemoBalanceFromWMEMO(balance: java.math.BigDecimal, walletAddress: String): java.math.BigDecimal = {
    val function    = new Web3Function(
      "wMEMOToMEMO",
      java.util.Arrays.asList(new Uint256(balance.toBigInteger)),
      java.util.Arrays.asList(new TypeReference[Uint256]() {})
    )
    val encoded     = FunctionEncoder.encode(function)
    val transaction = Transaction.createEthCallTransaction(walletAddress, Contracts.WMEMO, encoded)
    val response    = avaxWeb3.ethCall(transaction, defaultBlockParameter).send().getValue()
    val asWei       = FunctionReturnDecoder.decode(response, function.getOutputParameters).get(0).getValue
    Convert.toWei(asWei.toString, Convert.Unit.WEI) // Wei -> Wei for type safety.
  }
}
