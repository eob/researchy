// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy.file;

import java.util.zip._
import java.io._

/**
 * Provides file archiving utilities.
 */
object Archive {

  def zip(files : Seq[String], into : String) {
    zip(files, files, into)
  }

  def zip(files : Seq[String], destinationFiles: Seq[String], into : String) {
    val zipFile = new File(into)
    if (zipFile.exists()) {
      throw new RuntimeException("Zip file already exists")
    }

    val fos = new FileOutputStream(zipFile)
    val zos = new ZipOutputStream(fos)
    
    var bytesRead : Int = 0
    val buffer = new Array[Byte](1024)
    val crc = new CRC32()

    for ((fileName, destination) <- files zip destinationFiles) { 
      val file = new File(fileName)  
      if (!file.exists()) throw new RuntimeException("File for zipping doesn't exist")   
            
      var bis = new BufferedInputStream(new FileInputStream(file))
      crc.reset();
      bytesRead = bis.read(buffer)
      while (bytesRead != -1) {
        crc.update(buffer, 0, bytesRead)
        bytesRead = bis.read(buffer)
      }
      bis.close()
      
      // Reset to beginning of input stream
      bis = new BufferedInputStream(new FileInputStream(file))

      val entry = new ZipEntry(destination)
      entry.setMethod(ZipEntry.STORED)
      entry.setCompressedSize(file.length())
      entry.setSize(file.length())
      entry.setCrc(crc.getValue())
      zos.putNextEntry(entry)
      bytesRead = bis.read(buffer)
      while (bytesRead != -1) {
        zos.write(buffer, 0, bytesRead)
        bytesRead = bis.read(buffer)
      }
      bis.close()
    }
    zos.close()
  }

}
