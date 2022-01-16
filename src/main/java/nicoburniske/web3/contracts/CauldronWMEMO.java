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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
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
public class CauldronWMEMO extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_BORROW_OPENING_FEE = "BORROW_OPENING_FEE";

    public static final String FUNC_COLLATERIZATION_RATE = "COLLATERIZATION_RATE";

    public static final String FUNC_LIQUIDATION_MULTIPLIER = "LIQUIDATION_MULTIPLIER";

    public static final String FUNC_ACCRUE = "accrue";

    public static final String FUNC_ACCRUEINFO = "accrueInfo";

    public static final String FUNC_ADDCOLLATERAL = "addCollateral";

    public static final String FUNC_BENTOBOX = "bentoBox";

    public static final String FUNC_BORROW = "borrow";

    public static final String FUNC_CLAIMOWNERSHIP = "claimOwnership";

    public static final String FUNC_COLLATERAL = "collateral";

    public static final String FUNC_COOK = "cook";

    public static final String FUNC_EXCHANGERATE = "exchangeRate";

    public static final String FUNC_FEETO = "feeTo";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_LIQUIDATE = "liquidate";

    public static final String FUNC_MAGICINTERNETMONEY = "magicInternetMoney";

    public static final String FUNC_MASTERCONTRACT = "masterContract";

    public static final String FUNC_ORACLE = "oracle";

    public static final String FUNC_ORACLEDATA = "oracleData";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PENDINGOWNER = "pendingOwner";

    public static final String FUNC_REDUCESUPPLY = "reduceSupply";

    public static final String FUNC_REMOVECOLLATERAL = "removeCollateral";

    public static final String FUNC_REPAY = "repay";

    public static final String FUNC_SETFEETO = "setFeeTo";

    public static final String FUNC_TOTALBORROW = "totalBorrow";

    public static final String FUNC_TOTALCOLLATERALSHARE = "totalCollateralShare";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEEXCHANGERATE = "updateExchangeRate";

    public static final String FUNC_USERBORROWPART = "userBorrowPart";

    public static final String FUNC_USERCOLLATERALSHARE = "userCollateralShare";

    public static final String FUNC_WITHDRAWFEES = "withdrawFees";

    public static final Event LOGACCRUE_EVENT = new Event("LogAccrue",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {
            }));
    ;

    public static final Event LOGADDCOLLATERAL_EVENT = new Event("LogAddCollateral",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGBORROW_EVENT = new Event("LogBorrow",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGEXCHANGERATE_EVENT = new Event("LogExchangeRate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGFEETO_EVENT = new Event("LogFeeTo",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    public static final Event LOGREMOVECOLLATERAL_EVENT = new Event("LogRemoveCollateral",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGREPAY_EVENT = new Event("LogRepay",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event LOGWITHDRAWFEES_EVENT = new Event("LogWithdrawFees",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    @Deprecated
    protected CauldronWMEMO(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CauldronWMEMO(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CauldronWMEMO(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CauldronWMEMO(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<LogAccrueEventResponse> getLogAccrueEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGACCRUE_EVENT, transactionReceipt);
        ArrayList<LogAccrueEventResponse> responses = new ArrayList<LogAccrueEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogAccrueEventResponse typedResponse = new LogAccrueEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accruedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogAccrueEventResponse> logAccrueEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogAccrueEventResponse>() {
            @Override
            public LogAccrueEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGACCRUE_EVENT, log);
                LogAccrueEventResponse typedResponse = new LogAccrueEventResponse();
                typedResponse.log = log;
                typedResponse.accruedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogAccrueEventResponse> logAccrueEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGACCRUE_EVENT));
        return logAccrueEventFlowable(filter);
    }

    public List<LogAddCollateralEventResponse> getLogAddCollateralEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGADDCOLLATERAL_EVENT, transactionReceipt);
        ArrayList<LogAddCollateralEventResponse> responses = new ArrayList<LogAddCollateralEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogAddCollateralEventResponse typedResponse = new LogAddCollateralEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogAddCollateralEventResponse> logAddCollateralEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogAddCollateralEventResponse>() {
            @Override
            public LogAddCollateralEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGADDCOLLATERAL_EVENT, log);
                LogAddCollateralEventResponse typedResponse = new LogAddCollateralEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogAddCollateralEventResponse> logAddCollateralEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGADDCOLLATERAL_EVENT));
        return logAddCollateralEventFlowable(filter);
    }

    public List<LogBorrowEventResponse> getLogBorrowEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGBORROW_EVENT, transactionReceipt);
        ArrayList<LogBorrowEventResponse> responses = new ArrayList<LogBorrowEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogBorrowEventResponse typedResponse = new LogBorrowEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.part = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogBorrowEventResponse> logBorrowEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogBorrowEventResponse>() {
            @Override
            public LogBorrowEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGBORROW_EVENT, log);
                LogBorrowEventResponse typedResponse = new LogBorrowEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.part = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogBorrowEventResponse> logBorrowEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGBORROW_EVENT));
        return logBorrowEventFlowable(filter);
    }

    public List<LogExchangeRateEventResponse> getLogExchangeRateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGEXCHANGERATE_EVENT, transactionReceipt);
        ArrayList<LogExchangeRateEventResponse> responses = new ArrayList<LogExchangeRateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogExchangeRateEventResponse typedResponse = new LogExchangeRateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogExchangeRateEventResponse> logExchangeRateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogExchangeRateEventResponse>() {
            @Override
            public LogExchangeRateEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGEXCHANGERATE_EVENT, log);
                LogExchangeRateEventResponse typedResponse = new LogExchangeRateEventResponse();
                typedResponse.log = log;
                typedResponse.rate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogExchangeRateEventResponse> logExchangeRateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGEXCHANGERATE_EVENT));
        return logExchangeRateEventFlowable(filter);
    }

    public List<LogFeeToEventResponse> getLogFeeToEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGFEETO_EVENT, transactionReceipt);
        ArrayList<LogFeeToEventResponse> responses = new ArrayList<LogFeeToEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogFeeToEventResponse typedResponse = new LogFeeToEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newFeeTo = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogFeeToEventResponse> logFeeToEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogFeeToEventResponse>() {
            @Override
            public LogFeeToEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGFEETO_EVENT, log);
                LogFeeToEventResponse typedResponse = new LogFeeToEventResponse();
                typedResponse.log = log;
                typedResponse.newFeeTo = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogFeeToEventResponse> logFeeToEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGFEETO_EVENT));
        return logFeeToEventFlowable(filter);
    }

    public List<LogRemoveCollateralEventResponse> getLogRemoveCollateralEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGREMOVECOLLATERAL_EVENT, transactionReceipt);
        ArrayList<LogRemoveCollateralEventResponse> responses = new ArrayList<LogRemoveCollateralEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogRemoveCollateralEventResponse typedResponse = new LogRemoveCollateralEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogRemoveCollateralEventResponse> logRemoveCollateralEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogRemoveCollateralEventResponse>() {
            @Override
            public LogRemoveCollateralEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGREMOVECOLLATERAL_EVENT, log);
                LogRemoveCollateralEventResponse typedResponse = new LogRemoveCollateralEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.share = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogRemoveCollateralEventResponse> logRemoveCollateralEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGREMOVECOLLATERAL_EVENT));
        return logRemoveCollateralEventFlowable(filter);
    }

    public List<LogRepayEventResponse> getLogRepayEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGREPAY_EVENT, transactionReceipt);
        ArrayList<LogRepayEventResponse> responses = new ArrayList<LogRepayEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogRepayEventResponse typedResponse = new LogRepayEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.part = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogRepayEventResponse> logRepayEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogRepayEventResponse>() {
            @Override
            public LogRepayEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGREPAY_EVENT, log);
                LogRepayEventResponse typedResponse = new LogRepayEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.part = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogRepayEventResponse> logRepayEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGREPAY_EVENT));
        return logRepayEventFlowable(filter);
    }

    public List<LogWithdrawFeesEventResponse> getLogWithdrawFeesEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWITHDRAWFEES_EVENT, transactionReceipt);
        ArrayList<LogWithdrawFeesEventResponse> responses = new ArrayList<LogWithdrawFeesEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogWithdrawFeesEventResponse typedResponse = new LogWithdrawFeesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.feeTo = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.feesEarnedFraction = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWithdrawFeesEventResponse> logWithdrawFeesEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogWithdrawFeesEventResponse>() {
            @Override
            public LogWithdrawFeesEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWITHDRAWFEES_EVENT, log);
                LogWithdrawFeesEventResponse typedResponse = new LogWithdrawFeesEventResponse();
                typedResponse.log = log;
                typedResponse.feeTo = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.feesEarnedFraction = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWithdrawFeesEventResponse> logWithdrawFeesEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWITHDRAWFEES_EVENT));
        return logWithdrawFeesEventFlowable(filter);
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

    public RemoteFunctionCall<BigInteger> BORROW_OPENING_FEE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BORROW_OPENING_FEE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> COLLATERIZATION_RATE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COLLATERIZATION_RATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> LIQUIDATION_MULTIPLIER() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LIQUIDATION_MULTIPLIER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> accrue() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACCRUE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> accrueInfo() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACCRUEINFO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
                }, new TypeReference<Uint128>() {
                }, new TypeReference<Uint64>() {
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

    public RemoteFunctionCall<TransactionReceipt> addCollateral(String to, Boolean skim, BigInteger share) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCOLLATERAL,
                Arrays.<Type>asList(new Address(160, to),
                        new org.web3j.abi.datatypes.Bool(skim),
                        new Uint256(share)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> bentoBox() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BENTOBOX,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> borrow(String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BORROW,
                Arrays.<Type>asList(new Address(160, to),
                        new Uint256(amount)),
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

    public RemoteFunctionCall<String> collateral() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COLLATERAL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cook(List<BigInteger> actions, List<BigInteger> values, List<byte[]> datas) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COOK,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                                org.web3j.abi.datatypes.generated.Uint8.class,
                                org.web3j.abi.Utils.typeMap(actions, org.web3j.abi.datatypes.generated.Uint8.class)),
                        new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(values, Uint256.class)),
                        new org.web3j.abi.datatypes.DynamicArray<DynamicBytes>(
                                DynamicBytes.class,
                                org.web3j.abi.Utils.typeMap(datas, DynamicBytes.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> exchangeRate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXCHANGERATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> feeTo() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FEETO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> init(byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INIT,
                Arrays.<Type>asList(new DynamicBytes(data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> liquidate(List<String> users, List<BigInteger> maxBorrowParts, String to, String swapper) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LIQUIDATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<Address>(
                                Address.class,
                                org.web3j.abi.Utils.typeMap(users, Address.class)),
                        new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(maxBorrowParts, Uint256.class)),
                        new Address(160, to),
                        new Address(160, swapper)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> magicInternetMoney() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAGICINTERNETMONEY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> masterContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MASTERCONTRACT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> oracle() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORACLE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> oracleData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORACLEDATA,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
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

    public RemoteFunctionCall<TransactionReceipt> reduceSupply(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REDUCESUPPLY,
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeCollateral(String to, BigInteger share) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVECOLLATERAL,
                Arrays.<Type>asList(new Address(160, to),
                        new Uint256(share)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> repay(String to, Boolean skim, BigInteger part) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REPAY,
                Arrays.<Type>asList(new Address(160, to),
                        new org.web3j.abi.datatypes.Bool(skim),
                        new Uint256(part)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFeeTo(String newFeeTo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFEETO,
                Arrays.<Type>asList(new Address(160, newFeeTo)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> totalBorrow() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALBORROW,
                Arrays.<Type>asList(),
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

    public RemoteFunctionCall<BigInteger> totalCollateralShare() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALCOLLATERALSHARE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner, Boolean direct, Boolean renounce) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(160, newOwner),
                        new org.web3j.abi.datatypes.Bool(direct),
                        new org.web3j.abi.datatypes.Bool(renounce)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateExchangeRate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEEXCHANGERATE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> userBorrowPart(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERBORROWPART,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> userCollateralShare(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERCOLLATERALSHARE,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawFees() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWFEES,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CauldronWMEMO load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CauldronWMEMO(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CauldronWMEMO load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CauldronWMEMO(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CauldronWMEMO load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CauldronWMEMO(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CauldronWMEMO load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CauldronWMEMO(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class LogAccrueEventResponse extends BaseEventResponse {
        public BigInteger accruedAmount;
    }

    public static class LogAddCollateralEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger share;
    }

    public static class LogBorrowEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;

        public BigInteger part;
    }

    public static class LogExchangeRateEventResponse extends BaseEventResponse {
        public BigInteger rate;
    }

    public static class LogFeeToEventResponse extends BaseEventResponse {
        public String newFeeTo;
    }

    public static class LogRemoveCollateralEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger share;
    }

    public static class LogRepayEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;

        public BigInteger part;
    }

    public static class LogWithdrawFeesEventResponse extends BaseEventResponse {
        public String feeTo;

        public BigInteger feesEarnedFraction;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
