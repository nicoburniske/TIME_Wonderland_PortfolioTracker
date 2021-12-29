package nicoburniske.web3

import java.math.BigInteger

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.{FunctionEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert

/**
 * Queries Web3 RPC API for AVAX C-Chain.
 */
object Web3 {
  // Type alias to resolve name conflict with Scala standard library.
  type Web3Function = _root_.org.web3j.abi.datatypes.Function
  val AVALANCHE_C_CHAIN = "https://api.avax.network/ext/bc/C/rpc"

  object Contracts {
    val WMEMO = "0x0da67235dd5787d67955420c84ca1cecd4e5bb3b"
    val MEMO = "0x136acd46c134e8269052c62a67042d6bdedde3c9"
  }

  val web3 = Web3j.build(new HttpService(AVALANCHE_C_CHAIN))
  val defaultBlockParameter = DefaultBlockParameter.valueOf("latest")

  /**
   * Retrieves TIME Balance for the given wallet. Accounts for wrapped and non-wrapped wallet balance.
   *
   * @param walletAddress wallet with TIME Balance.
   * @return Human-readable format of TIME Balance.
   */
  def getWalletTimeBalance(walletAddress: String): BigDecimal = {
    val wrappedBalance = getContractWalletBalance(Contracts.WMEMO, walletAddress)
    val nonWrappedBalance = getContractWalletBalance(Contracts.MEMO, walletAddress)
    val converted = getMemoBalanceFromWMEMO(wrappedBalance, walletAddress).add(nonWrappedBalance)
    Convert.fromWei(converted, Convert.Unit.GWEI)
  }

  def getAVAXBalance(walletAddress: String): BigDecimal = {
    val wei: BigInteger = web3.ethGetBalance(walletAddress, defaultBlockParameter).send().getBalance
    Convert.fromWei(wei.toString, Convert.Unit.ETHER)
  }

  def getContractWalletBalance(contractAddress: String, walletAddress: String): java.math.BigDecimal = {
    val function = new Web3Function("balanceOf",
      java.util.Arrays.asList(new Address(walletAddress)),
      java.util.Arrays.asList(new TypeReference[Uint256]() {}));
    val encoded = FunctionEncoder.encode(function)
    val transaction = Transaction.createEthCallTransaction(walletAddress, contractAddress, encoded)
    val response = web3.ethCall(transaction, defaultBlockParameter).send().getValue()
    val asWei = FunctionReturnDecoder.decode(response, function.getOutputParameters).get(0).getValue()
    Convert.toWei(asWei.toString, Convert.Unit.WEI) // Wei -> Wei for type safety.
  }

  def getMemoBalanceFromWMEMO(balance: java.math.BigDecimal, walletAddress: String): java.math.BigDecimal = {
    val function = new Web3Function("wMEMOToMEMO",
      java.util.Arrays.asList(new Uint256(balance.toBigInteger)),
      java.util.Arrays.asList(new TypeReference[Uint256]() {}));
    val encoded = FunctionEncoder.encode(function)
    val transaction = Transaction.createEthCallTransaction(walletAddress, Contracts.WMEMO, encoded)
    val response = web3.ethCall(transaction, defaultBlockParameter).send().getValue()
    val asWei = FunctionReturnDecoder.decode(response, function.getOutputParameters).get(0).getValue()
    Convert.toWei(asWei.toString, Convert.Unit.WEI) // Wei -> Wei for type safety.
  }
}
