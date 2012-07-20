// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy;
  
import java.io.{File, FileWriter}
import com.codahale.logula.Logging

object Dump extends Logging {
  /**
   * Saves a sequence of items to a comma-separated file
   *
   * @param items The sequence of items to log.
   * @param fileName The experiment-local file in which to log.
   */
  def csv(items : Seq[Any], fileName : String) {
    Dump.line(items.mkString(","), fileName)
  }

  /**
   * Saves a line of text to a comma-separated file
   *
   * @param line The line to log
   * @param fileName The experiment-local file in which to log
   */
  def line(line : String, toFile: String) {
    val fw = Dump.newWriter(toFile)
    fw.write(line + "\n")
    fw.close()
  }

  /**
   * @param  experimentLocalFilename the experiment-local filename to write to
   * @return A new FileWriter
   */
  def newWriter(experimentLocalFilename : String) : FileWriter = {
    val fullPath = Experiment.filePath(experimentLocalFilename) 
    val rootDir = (new File(fullPath)).getParentFile()
    if (! rootDir.exists()) rootDir.mkdirs()
    val fw = new FileWriter(fullPath, true)
    fw
  }
}
