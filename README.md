# Portfolio Tracker
- Tracks portfolio by adding a log entry to csv file every 8 hours (after TIME Wonderland rebase times)

### Basic Execution
```
java -jar web3-logger.jar -w  <your wallet address>
```

### CLI Options
```
java -jar web3-logger.jar -h                                             
  -c, --csvPath  <arg>         Path to csv file for logs. Default is log.csv in
                               working directory
  -r, --runAtStart             Will execute first log immediately
  -w, --walletAddress  <arg>   Wallet address on AVAX C-Chain
  -h, --help                   Show help message
```
