package researchy;

import java.io.{File, FileWriter}
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.regex.{Matcher, Pattern}
import com.codahale.logula.Logging
import org.apache.log4j.Level

/**
 * Singleton object to provide an Experiment Notebook for your research projects.
 */
object Experiment extends Logging {
  var experimentPool_ : String = "experiments"
  var runExtension_ : String = "run"
  var outputDirectory_ : String = ""
  var outputDirectoryFile_ : File = null

  /**
   * Begin a new experiment.
   *
   * Automatically creates a new working directory to record all log files for
   * this experiment.
   *
   * @param args The args passed to the Scala program.
   * @param commends Any comments for this experiment.
   */
  def begin(args : Array[String] = null, comments : String = "", directory : File = null) {
    if (directory != null) {
      outputDirectoryFile_ = directory
    }
    else {
      outputDirectoryFile_ = createNewExperimentDirectory()
    }
    outputDirectory_ = outputDirectoryFile_.getAbsolutePath()
    configureLog()
    if (args != null) {
      Dump.csv(args.toSeq, "managed/args.csv")
    }
    if (comments != null) {
      Dump.line(comments, "managed/comments.txt")
    }
  }

  def outputDirectory : String = outputDirectory_ 
  def outputDirectoryFile : File = outputDirectoryFile_ 

  def filePath(fileName : String) : String = {
    (new File(outputDirectory, fileName)).toString() 
  }

  def file(path : String) : File = {
    val fullPath = Experiment.filePath(path)
    val file = new File(fullPath)
    val rootDir = file.getParentFile()
    if (! rootDir.exists()) rootDir.mkdirs()
    return file
  }

  /**
   * Creates a new directory for this experiment.
   */
   def createNewExperimentDirectory() : File = {
     val dateFormat= new SimpleDateFormat("YYYY-MM-dd")
     val cal = Calendar.getInstance()
     val dateStr = dateFormat.format(cal.getTime())
     val rootDir = new File(experimentPool_, dateStr)
     if (! rootDir.exists()) rootDir.mkdirs()

     val files = rootDir.listFiles()
     var lastNum = 0
     for (file <- files) {
       val name = file.getName()
       val matcher = Pattern.compile("(\\d+).%s".format(Experiment.runExtension_)).matcher(name)
       if (matcher.matches()) {
         val num = Integer.parseInt(matcher.group(1));
         if (num >= lastNum) lastNum = num + 1;
       }
     }
     val outputDirectoryFile = new File(rootDir, "%d.%s".format(lastNum, runExtension_))
     outputDirectoryFile.mkdir()
     return outputDirectoryFile;
  }

  /**
   * Configure the log to defaults.
   *
   * Precondition: Experiment has already begun.
   */
  def configureLog() {
    val logFile = Experiment.file("log/experiment.log")
    Logging.configure { log =>
      log.registerWithJMX = true
      log.level = Level.INFO
      log.loggers("com.myproject.weebits") = Level.OFF
      log.console.enabled = true
      log.console.threshold = Level.WARN
      log.file.enabled = true
      log.file.filename = logFile.getAbsolutePath()
      log.file.maxSize = 10 * 1024 // KB
      log.file.retainedFiles = 5 // keep five old logs around
    }
  }
}
