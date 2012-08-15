// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy.thrift;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.TFieldIdEnum;

class ThriftSeriesWriter[T <: TBase[T,F], F <: TFieldIdEnum](directory : String, base : String, numPerFile : Int = 50) {
 
  var written = 0
  var fileNum = -1

  var bufferedOut : Option[BufferedOutputStream] = None
  var binaryOut : Option[TBinaryProtocol] = None

  def advanceFile() {
    close()
    fileNum += 1
    val file = new File(directory, "%s_%04d".format(base, fileNum))
    bufferedOut = Some(new BufferedOutputStream(new FileOutputStream(file), 2048))
    binaryOut = Some(new TBinaryProtocol(new TIOStreamTransport(bufferedOut.get)))
  }

  def write(t : T) {
    if (written % numPerFile == 0) advanceFile()
    written += 1

    try {
      t.write(binaryOut.get);
      bufferedOut.get.flush();
    } catch  {
      case _ => {println("Write ex")}
    }
  }
  
  /**
    * Close the file stream.
    */
  def close() {
    try {
      if (bufferedOut != None) bufferedOut.get.close();
    }
    catch {
      case _ => {println("Could not close")}
    }
  }
}
