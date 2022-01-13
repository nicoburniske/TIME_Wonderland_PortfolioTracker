package nicoburniske.web3

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import com.github.tototoshi.csv.CSVWriter

object CsvLogger {
  val CSV_HEADERS = Seq("Log Time", "TIME Balance", "TIME Price", "TIME USD Value", "ETH Price")
  val formatter   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * Adds log to CSV File.
   *
   * @param csvPath     csv file path
   * @param timeBalance current TIME wallet balance
   * @param ethPrice    current ETH Price
   * @param timePrice   current TIME Price
   */
  def addLog(csvPath: String, timeBalance: BigDecimal, timePrice: Double, ethPrice: Double): Unit = {
    val file         = new File(csvPath)
    if (!file.exists()) {
      setupCSV(file)
    }
    val csvWriter    = CSVWriter.open(file, append = true)
    val time         = formatter.format(Calendar.getInstance().getTime)
    val balanceValue = (timeBalance * timePrice).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    csvWriter.writeRow(Seq(time, timeBalance.toString, timePrice, balanceValue.toString, ethPrice))
    csvWriter.close
  }

  // Creates new file and adds CSV Headers.
  private def setupCSV(file: File): Unit = {
    file.createNewFile()
    val csvWriter = CSVWriter.open(file)
    csvWriter.writeRow(CSV_HEADERS)
    csvWriter.close()
  }
}
