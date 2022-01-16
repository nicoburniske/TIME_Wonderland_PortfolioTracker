package nicoburniske.web3.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class BentoboxV1 extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DOMAIN_SEPARATOR = "DOMAIN_SEPARATOR";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BATCH = "batch";

    public static final String FUNC_BATCHFLASHLOAN = "batchFlashLoan";

    public static final String FUNC_CLAIMOWNERSHIP = "claimOwnership";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_FLASHLOAN = "flashLoan";

    public static final String FUNC_HARVEST = "harvest";

    public static final String FUNC_MASTERCONTRACTAPPROVED = "masterContractApproved";

    public static final String FUNC_MASTERCONTRACTOF = "masterContractOf";

    public static final String FUNC_NONCES = "nonces";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PENDINGOWNER = "pendingOwner";

    public static final String FUNC_PENDINGSTRATEGY = "pendingStrategy";

    public static final String FUNC_PERMITTOKEN = "permitToken";

    public static final String FUNC_REGISTERPROTOCOL = "registerProtocol";

    public static final String FUNC_SETMASTERCONTRACTAPPROVAL = "setMasterContractApproval";

    public static final String FUNC_SETSTRATEGY = "setStrategy";

    public static final String FUNC_SETSTRATEGYTARGETPERCENTAGE = "setStrategyTargetPercentage";

    public static final String FUNC_STRATEGY = "strategy";

    public static final String FUNC_STRATEGYDATA = "strategyData";

    public static final String FUNC_TOAMOUNT = "toAmount";

    public static final String FUNC_TOSHARE = "toShare";

    public static final String FUNC_TOTALS = "totals";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERMULTIPLE = "transferMultiple";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WHITELISTMASTERCONTRACT = "whitelistMasterContract";

    public static final String FUNC_WHITELISTEDMASTERCONTRACTS = "whitelistedMasterContracts";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event LOGDEPLOY_EVENT = new Event("LogDeploy",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<DynamicBytes>() {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGDEPOSIT_EVENT = new Event("LogDeposit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGFLASHLOAN_EVENT = new Event("LogFlashLoan",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGREGISTERPROTOCOL_EVENT = new Event("LogRegisterProtocol",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGSETMASTERCONTRACTAPPROVAL_EVENT = new Event("LogSetMasterContractApproval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bool>() {
            }));
    ;

    public static final Event LOGSTRATEGYDIVEST_EVENT = new Event("LogStrategyDivest",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGSTRATEGYINVEST_EVENT = new Event("LogStrategyInvest",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGSTRATEGYLOSS_EVENT = new Event("LogStrategyLoss",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGSTRATEGYPROFIT_EVENT = new Event("LogStrategyProfit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGSTRATEGYQUEUED_EVENT = new Event("LogStrategyQueued",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGSTRATEGYSET_EVENT = new Event("LogStrategySet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGSTRATEGYTARGETPERCENTAGE_EVENT = new Event("LogStrategyTargetPercentage",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGTRANSFER_EVENT = new Event("LogTransfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGWHITELISTMASTERCONTRACT_EVENT = new Event("LogWhiteListMasterContract",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bool>() {
            }));
    ;

    public static final Event LOGWITHDRAW_EVENT = new Event("LogWithdraw",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    @Deprecated
    protected BentoboxV1(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BentoboxV1(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BentoboxV1(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BentoboxV1(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<LogDeployEventResponse> getLogDeployEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPLOY_EVENT, transactionReceipt);
        ArrayList<LogDeployEventResponse> responses = new ArrayList<LogDeployEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDeployEventResponse typedResponse = new LogDeployEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.cloneAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDeployEventResponse> logDeployEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDeployEventResponse>() {
            @Override
            public LogDeployEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPLOY_EVENT, log);
                LogDeployEventResponse typedResponse = new LogDeployEventResponse();
                typedResponse.log = log;
                typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.cloneAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDeployEventResponse> logDeployEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPLOY_EVENT));
        return logDeployEventFlowable(filter);
    }

    public List<LogDepositEventResponse> getLogDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSIT_EVENT, transactionReceipt);
        ArrayList<LogDepositEventResponse> responses = new ArrayList<LogDepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDepositEventResponse typedResponse = new LogDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositEventResponse> logDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDepositEventResponse>() {
            @Override
            public LogDepositEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSIT_EVENT, log);
                LogDepositEventResponse typedResponse = new LogDepositEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositEventResponse> logDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSIT_EVENT));
        return logDepositEventFlowable(filter);
    }

    public List<LogFlashLoanEventResponse> getLogFlashLoanEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGFLASHLOAN_EVENT, transactionReceipt);
        ArrayList<LogFlashLoanEventResponse> responses = new ArrayList<LogFlashLoanEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogFlashLoanEventResponse typedResponse = new LogFlashLoanEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.borrower = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.feeAmount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogFlashLoanEventResponse> logFlashLoanEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogFlashLoanEventResponse>() {
            @Override
            public LogFlashLoanEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGFLASHLOAN_EVENT, log);
                LogFlashLoanEventResponse typedResponse = new LogFlashLoanEventResponse();
                typedResponse.log = log;
                typedResponse.borrower = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.token = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.feeAmount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogFlashLoanEventResponse> logFlashLoanEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGFLASHLOAN_EVENT));
        return logFlashLoanEventFlowable(filter);
    }

    public List<LogRegisterProtocolEventResponse> getLogRegisterProtocolEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGREGISTERPROTOCOL_EVENT, transactionReceipt);
        ArrayList<LogRegisterProtocolEventResponse> responses = new ArrayList<LogRegisterProtocolEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogRegisterProtocolEventResponse typedResponse = new LogRegisterProtocolEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.protocol = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogRegisterProtocolEventResponse> logRegisterProtocolEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogRegisterProtocolEventResponse>() {
            @Override
            public LogRegisterProtocolEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGREGISTERPROTOCOL_EVENT, log);
                LogRegisterProtocolEventResponse typedResponse = new LogRegisterProtocolEventResponse();
                typedResponse.log = log;
                typedResponse.protocol = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogRegisterProtocolEventResponse> logRegisterProtocolEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGREGISTERPROTOCOL_EVENT));
        return logRegisterProtocolEventFlowable(filter);
    }

    public List<LogSetMasterContractApprovalEventResponse> getLogSetMasterContractApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSETMASTERCONTRACTAPPROVAL_EVENT, transactionReceipt);
        ArrayList<LogSetMasterContractApprovalEventResponse> responses = new ArrayList<LogSetMasterContractApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogSetMasterContractApprovalEventResponse typedResponse = new LogSetMasterContractApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogSetMasterContractApprovalEventResponse> logSetMasterContractApprovalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogSetMasterContractApprovalEventResponse>() {
            @Override
            public LogSetMasterContractApprovalEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSETMASTERCONTRACTAPPROVAL_EVENT, log);
                LogSetMasterContractApprovalEventResponse typedResponse = new LogSetMasterContractApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogSetMasterContractApprovalEventResponse> logSetMasterContractApprovalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSETMASTERCONTRACTAPPROVAL_EVENT));
        return logSetMasterContractApprovalEventFlowable(filter);
    }

    public List<LogStrategyDivestEventResponse> getLogStrategyDivestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYDIVEST_EVENT, transactionReceipt);
        ArrayList<LogStrategyDivestEventResponse> responses = new ArrayList<LogStrategyDivestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyDivestEventResponse typedResponse = new LogStrategyDivestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyDivestEventResponse> logStrategyDivestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyDivestEventResponse>() {
            @Override
            public LogStrategyDivestEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYDIVEST_EVENT, log);
                LogStrategyDivestEventResponse typedResponse = new LogStrategyDivestEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyDivestEventResponse> logStrategyDivestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYDIVEST_EVENT));
        return logStrategyDivestEventFlowable(filter);
    }

    public List<LogStrategyInvestEventResponse> getLogStrategyInvestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYINVEST_EVENT, transactionReceipt);
        ArrayList<LogStrategyInvestEventResponse> responses = new ArrayList<LogStrategyInvestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyInvestEventResponse typedResponse = new LogStrategyInvestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyInvestEventResponse> logStrategyInvestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyInvestEventResponse>() {
            @Override
            public LogStrategyInvestEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYINVEST_EVENT, log);
                LogStrategyInvestEventResponse typedResponse = new LogStrategyInvestEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyInvestEventResponse> logStrategyInvestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYINVEST_EVENT));
        return logStrategyInvestEventFlowable(filter);
    }

    public List<LogStrategyLossEventResponse> getLogStrategyLossEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYLOSS_EVENT, transactionReceipt);
        ArrayList<LogStrategyLossEventResponse> responses = new ArrayList<LogStrategyLossEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyLossEventResponse typedResponse = new LogStrategyLossEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyLossEventResponse> logStrategyLossEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyLossEventResponse>() {
            @Override
            public LogStrategyLossEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYLOSS_EVENT, log);
                LogStrategyLossEventResponse typedResponse = new LogStrategyLossEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyLossEventResponse> logStrategyLossEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYLOSS_EVENT));
        return logStrategyLossEventFlowable(filter);
    }

    public List<LogStrategyProfitEventResponse> getLogStrategyProfitEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYPROFIT_EVENT, transactionReceipt);
        ArrayList<LogStrategyProfitEventResponse> responses = new ArrayList<LogStrategyProfitEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyProfitEventResponse typedResponse = new LogStrategyProfitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyProfitEventResponse> logStrategyProfitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyProfitEventResponse>() {
            @Override
            public LogStrategyProfitEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYPROFIT_EVENT, log);
                LogStrategyProfitEventResponse typedResponse = new LogStrategyProfitEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyProfitEventResponse> logStrategyProfitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYPROFIT_EVENT));
        return logStrategyProfitEventFlowable(filter);
    }

    public List<LogStrategyQueuedEventResponse> getLogStrategyQueuedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYQUEUED_EVENT, transactionReceipt);
        ArrayList<LogStrategyQueuedEventResponse> responses = new ArrayList<LogStrategyQueuedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyQueuedEventResponse typedResponse = new LogStrategyQueuedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.strategy = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyQueuedEventResponse> logStrategyQueuedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyQueuedEventResponse>() {
            @Override
            public LogStrategyQueuedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYQUEUED_EVENT, log);
                LogStrategyQueuedEventResponse typedResponse = new LogStrategyQueuedEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.strategy = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyQueuedEventResponse> logStrategyQueuedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYQUEUED_EVENT));
        return logStrategyQueuedEventFlowable(filter);
    }

    public List<LogStrategySetEventResponse> getLogStrategySetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYSET_EVENT, transactionReceipt);
        ArrayList<LogStrategySetEventResponse> responses = new ArrayList<LogStrategySetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategySetEventResponse typedResponse = new LogStrategySetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.strategy = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategySetEventResponse> logStrategySetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategySetEventResponse>() {
            @Override
            public LogStrategySetEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYSET_EVENT, log);
                LogStrategySetEventResponse typedResponse = new LogStrategySetEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.strategy = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategySetEventResponse> logStrategySetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYSET_EVENT));
        return logStrategySetEventFlowable(filter);
    }

    public List<LogStrategyTargetPercentageEventResponse> getLogStrategyTargetPercentageEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTRATEGYTARGETPERCENTAGE_EVENT, transactionReceipt);
        ArrayList<LogStrategyTargetPercentageEventResponse> responses = new ArrayList<LogStrategyTargetPercentageEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStrategyTargetPercentageEventResponse typedResponse = new LogStrategyTargetPercentageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.targetPercentage = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStrategyTargetPercentageEventResponse> logStrategyTargetPercentageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStrategyTargetPercentageEventResponse>() {
            @Override
            public LogStrategyTargetPercentageEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTRATEGYTARGETPERCENTAGE_EVENT, log);
                LogStrategyTargetPercentageEventResponse typedResponse = new LogStrategyTargetPercentageEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.targetPercentage = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStrategyTargetPercentageEventResponse> logStrategyTargetPercentageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTRATEGYTARGETPERCENTAGE_EVENT));
        return logStrategyTargetPercentageEventFlowable(filter);
    }

    public List<LogTransferEventResponse> getLogTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGTRANSFER_EVENT, transactionReceipt);
        ArrayList<LogTransferEventResponse> responses = new ArrayList<LogTransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogTransferEventResponse typedResponse = new LogTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogTransferEventResponse> logTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogTransferEventResponse>() {
            @Override
            public LogTransferEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGTRANSFER_EVENT, log);
                LogTransferEventResponse typedResponse = new LogTransferEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogTransferEventResponse> logTransferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGTRANSFER_EVENT));
        return logTransferEventFlowable(filter);
    }

    public List<LogWhiteListMasterContractEventResponse> getLogWhiteListMasterContractEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWHITELISTMASTERCONTRACT_EVENT, transactionReceipt);
        ArrayList<LogWhiteListMasterContractEventResponse> responses = new ArrayList<LogWhiteListMasterContractEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogWhiteListMasterContractEventResponse typedResponse = new LogWhiteListMasterContractEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWhiteListMasterContractEventResponse> logWhiteListMasterContractEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogWhiteListMasterContractEventResponse>() {
            @Override
            public LogWhiteListMasterContractEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWHITELISTMASTERCONTRACT_EVENT, log);
                LogWhiteListMasterContractEventResponse typedResponse = new LogWhiteListMasterContractEventResponse();
                typedResponse.log = log;
                typedResponse.masterContract = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWhiteListMasterContractEventResponse> logWhiteListMasterContractEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWHITELISTMASTERCONTRACT_EVENT));
        return logWhiteListMasterContractEventFlowable(filter);
    }

    public List<LogWithdrawEventResponse> getLogWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWITHDRAW_EVENT, transactionReceipt);
        ArrayList<LogWithdrawEventResponse> responses = new ArrayList<LogWithdrawEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogWithdrawEventResponse typedResponse = new LogWithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWithdrawEventResponse> logWithdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogWithdrawEventResponse>() {
            @Override
            public LogWithdrawEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWITHDRAW_EVENT, log);
                LogWithdrawEventResponse typedResponse = new LogWithdrawEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWithdrawEventResponse> logWithdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWITHDRAW_EVENT));
        return logWithdrawEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> DOMAIN_SEPARATOR() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DOMAIN_SEPARATOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(160, param0),
                        new Address(160, param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> batch(List<byte[]> calls, Boolean revertOnFail) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BATCH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<DynamicBytes>(
                                DynamicBytes.class,
                                org.web3j.abi.Utils.typeMap(calls, DynamicBytes.class)),
                        new Bool(revertOnFail)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> batchFlashLoan(String borrower, List<String> receivers, List<String> tokens, List<BigInteger> amounts, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BATCHFLASHLOAN,
                Arrays.<Type>asList(new Address(160, borrower),
                        new org.web3j.abi.datatypes.DynamicArray<Address>(
                                Address.class,
                                org.web3j.abi.Utils.typeMap(receivers, Address.class)),
                        new org.web3j.abi.datatypes.DynamicArray<Address>(
                                Address.class,
                                org.web3j.abi.Utils.typeMap(tokens, Address.class)),
                        new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(amounts, Uint256.class)),
                        new DynamicBytes(data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deploy(String masterContract, byte[] data, Boolean useCreate2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPLOY,
                Arrays.<Type>asList(new Address(160, masterContract),
                        new DynamicBytes(data),
                        new Bool(useCreate2)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String token_, String from, String to, BigInteger amount, BigInteger share) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT,
                Arrays.<Type>asList(new Address(160, token_),
                        new Address(160, from),
                        new Address(160, to),
                        new Uint256(amount),
                        new Uint256(share)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> flashLoan(String borrower, String receiver, String token, BigInteger amount, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FLASHLOAN,
                Arrays.<Type>asList(new Address(160, borrower),
                        new Address(160, receiver),
                        new Address(160, token),
                        new Uint256(amount),
                        new DynamicBytes(data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> harvest(String token, Boolean balance, BigInteger maxChangeAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_HARVEST,
                Arrays.<Type>asList(new Address(160, token),
                        new Bool(balance),
                        new Uint256(maxChangeAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> masterContractApproved(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MASTERCONTRACTAPPROVED,
                Arrays.<Type>asList(new Address(160, param0),
                        new Address(160, param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> masterContractOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MASTERCONTRACTOF,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> nonces(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NONCES,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> pendingOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PENDINGOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> pendingStrategy(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PENDINGSTRATEGY,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> permitToken(String token, String from, String to, BigInteger amount, BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PERMITTOKEN,
                Arrays.<Type>asList(new Address(160, token),
                        new Address(160, from),
                        new Address(160, to),
                        new Uint256(amount),
                        new Uint256(deadline),
                        new org.web3j.abi.datatypes.generated.Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerProtocol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERPROTOCOL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMasterContractApproval(String user, String masterContract, Boolean approved, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETMASTERCONTRACTAPPROVAL,
                Arrays.<Type>asList(new Address(160, user),
                        new Address(160, masterContract),
                        new Bool(approved),
                        new org.web3j.abi.datatypes.generated.Uint8(v),
                        new Bytes32(r),
                        new Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStrategy(String token, String newStrategy) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTRATEGY,
                Arrays.<Type>asList(new Address(160, token),
                        new Address(160, newStrategy)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStrategyTargetPercentage(String token, BigInteger targetPercentage_) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTRATEGYTARGETPERCENTAGE,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint64(targetPercentage_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> strategy(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STRATEGY,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> strategyData(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STRATEGYDATA,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
                }, new TypeReference<Uint64>() {
                }, new TypeReference<Uint128>() {
                }));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> toAmount(String token, BigInteger share, Boolean roundUp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOAMOUNT,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(share),
                        new Bool(roundUp)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> toShare(String token, BigInteger amount, Boolean roundUp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOSHARE,
                Arrays.<Type>asList(new Address(160, token),
                        new Uint256(amount),
                        new Bool(roundUp)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> totals(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALS,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {
                }, new TypeReference<Uint128>() {
                }));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String token, String from, String to, BigInteger share) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(160, token),
                        new Address(160, from),
                        new Address(160, to),
                        new Uint256(share)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferMultiple(String token, String from, List<String> tos, List<BigInteger> shares) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERMULTIPLE,
                Arrays.<Type>asList(new Address(160, token),
                        new Address(160, from),
                        new org.web3j.abi.datatypes.DynamicArray<Address>(
                                Address.class,
                                org.web3j.abi.Utils.typeMap(tos, Address.class)),
                        new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(shares, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner, Boolean direct, Boolean renounce) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(160, newOwner),
                        new Bool(direct),
                        new Bool(renounce)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> whitelistMasterContract(String masterContract, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WHITELISTMASTERCONTRACT,
                Arrays.<Type>asList(new Address(160, masterContract),
                        new Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> whitelistedMasterContracts(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WHITELISTEDMASTERCONTRACTS,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String token_, String from, String to, BigInteger amount, BigInteger share) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new Address(160, token_),
                        new Address(160, from),
                        new Address(160, to),
                        new Uint256(amount),
                        new Uint256(share)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static BentoboxV1 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BentoboxV1(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BentoboxV1 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BentoboxV1(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BentoboxV1 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BentoboxV1(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BentoboxV1 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BentoboxV1(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class LogDeployEventResponse extends BaseEventResponse {
        public String masterContract;

        public String cloneAddress;

        public byte[] data;
    }

    public static class LogDepositEventResponse extends BaseEventResponse {
        public String token;

        public String from;

        public String to;

        public BigInteger amount;

        public BigInteger share;
    }

    public static class LogFlashLoanEventResponse extends BaseEventResponse {
        public String borrower;

        public String token;

        public String receiver;

        public BigInteger amount;

        public BigInteger feeAmount;
    }

    public static class LogRegisterProtocolEventResponse extends BaseEventResponse {
        public String protocol;
    }

    public static class LogSetMasterContractApprovalEventResponse extends BaseEventResponse {
        public String masterContract;

        public String user;

        public Boolean approved;
    }

    public static class LogStrategyDivestEventResponse extends BaseEventResponse {
        public String token;

        public BigInteger amount;
    }

    public static class LogStrategyInvestEventResponse extends BaseEventResponse {
        public String token;

        public BigInteger amount;
    }

    public static class LogStrategyLossEventResponse extends BaseEventResponse {
        public String token;

        public BigInteger amount;
    }

    public static class LogStrategyProfitEventResponse extends BaseEventResponse {
        public String token;

        public BigInteger amount;
    }

    public static class LogStrategyQueuedEventResponse extends BaseEventResponse {
        public String token;

        public String strategy;
    }

    public static class LogStrategySetEventResponse extends BaseEventResponse {
        public String token;

        public String strategy;
    }

    public static class LogStrategyTargetPercentageEventResponse extends BaseEventResponse {
        public String token;

        public BigInteger targetPercentage;
    }

    public static class LogTransferEventResponse extends BaseEventResponse {
        public String token;

        public String from;

        public String to;

        public BigInteger share;
    }

    public static class LogWhiteListMasterContractEventResponse extends BaseEventResponse {
        public String masterContract;

        public Boolean approved;
    }

    public static class LogWithdrawEventResponse extends BaseEventResponse {
        public String token;

        public String from;

        public String to;

        public BigInteger amount;

        public BigInteger share;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
