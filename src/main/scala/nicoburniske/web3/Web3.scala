package nicoburniske.web3

import java.math.BigInteger

import monix.eval.Task
import nicoburniske.web3.contracts.{MEMO, WMEMO}
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
 * Queries Web3 RPC API for AVAX C-Chain.
 */
object Web3 {
  // Type alias to resolve name conflict with Scala standard library.
  type Web3Function = _root_.org.web3j.abi.datatypes.Function

  val AVALANCHE_C_CHAIN = "https://api.avax.network/ext/bc/C/rpc"
  val ETHEREUM_CHAIN    = "https://mainnet.infura.io/v3/5fe4e62a874540818356146a14d478b9"

  object Contracts {
    val WMEMO_ADDRESS = "0x0da67235dd5787d67955420c84ca1cecd4e5bb3b"
    val MEMO_ADDRESS  = "0x136acd46c134e8269052c62a67042d6bdedde3c9"
  }

  val avaxWeb3 = Web3j.build(new HttpService(AVALANCHE_C_CHAIN))
  val ethWeb3  = Web3j.build(new HttpService(ETHEREUM_CHAIN))

  val bunkAddress        = "0x" + (0 until 40).map(_ => 0).mkString
  val transactionManager = new ClientTransactionManager(avaxWeb3, bunkAddress)
  val gasProvider        = new DefaultGasProvider()
  val wMemoContract      = WMEMO.load(Contracts.WMEMO_ADDRESS, avaxWeb3, transactionManager, gasProvider)
  val memoContract       = MEMO.load(Contracts.MEMO_ADDRESS, avaxWeb3, transactionManager, gasProvider)

  val defaultBlockParameter = DefaultBlockParameter.valueOf("latest")

  /**
   * Retrieves TIME Balance for the given wallet. Accounts for wrapped and non-wrapped wallet balance.
   *
   * @param walletAddress
   *   wallet with TIME Balance.
   * @return
   *   Human-readable format of TIME Balance.
   */
  def getWalletTimeBalance(walletAddress: String): Task[BigDecimal] = {
    val nonWrappedTask = Task(memoContract.balanceOf(walletAddress).send())
    val wrappedTask    = Task(wMemoContract.balanceOf(walletAddress).send())
    val oneAsWei       = Convert.toWei("1", Convert.Unit.ETHER)
    val conversionTask = Task(wMemoContract.wMEMOToMEMO(oneAsWei.toBigInteger).send())
    Task.parZip3(nonWrappedTask, wrappedTask, conversionTask).map {
      case (nonWrapped, wrapped, conversion) =>
        val wrapped2    = Convert.fromWei(new java.math.BigDecimal(wrapped), Convert.Unit.ETHER)
        val conversion2 = Convert.fromWei(new java.math.BigDecimal(conversion), Convert.Unit.GWEI)
        val nonWrapped2 = Convert.fromWei(new java.math.BigDecimal(nonWrapped), Convert.Unit.GWEI)
        wrapped2.multiply(conversion2).add(nonWrapped2)
    }
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
    );
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
    );
    val encoded     = FunctionEncoder.encode(function)
    val transaction = Transaction.createEthCallTransaction(walletAddress, Contracts.WMEMO_ADDRESS, encoded)
    val response    = avaxWeb3.ethCall(transaction, defaultBlockParameter).send().getValue()
    val asWei       = FunctionReturnDecoder.decode(response, function.getOutputParameters).get(0).getValue
    Convert.toWei(asWei.toString, Convert.Unit.WEI) // Wei -> Wei for type safety.
  }
}
