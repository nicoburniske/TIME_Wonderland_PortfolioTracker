package nicoburniske.web3.csv

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import com.github.tototoshi.csv.CSVWriter

// TODO: Use cats.effect.Resource for IO management.
object CsvLogger {
  val CSV_HEADERS = Seq("Log Time", "TIME Balance")
  val DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  /**
   * Adds log to CSV File.
   *
   * @param csvPath
   *   csv file path
   * @param timeBalance
   *   current TIME wallet balance
   */
  def addLog(csvPath: String, timeBalance: BigDecimal, prices: Seq[(String, Double)]): Unit = {
    val file      = new File(csvPath)
    if (!file.exists()) {
      setupCSV(file, prices.map(_._1))
    }
    val csvWriter = CSVWriter.open(file, append = true)
    val time      = DATE_FORMAT.format(Calendar.getInstance().getTime)
    csvWriter.writeRow(Seq(time, timeBalance.toString) ++ prices.map(_._2))
    csvWriter.close
  }

  // Creates new file and adds CSV Headers.
  private def setupCSV(file: File, prices: Seq[String]): Unit = {
    file.createNewFile()
    val csvWriter = CSVWriter.open(file)
    csvWriter.writeRow(CSV_HEADERS ++ prices)
    csvWriter.close()
  }
}
