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

class ThriftWriter[T <: TBase[T,F], F <: TFieldIdEnum](file : File) {
  
  val bufferedOut = new BufferedOutputStream(new FileOutputStream(file), 2048)
  val binaryOut = new TBinaryProtocol(new TIOStreamTransport(bufferedOut))
  
  def write(t : T) = {
    try {
      t.write(binaryOut);
      bufferedOut.flush();
    } catch  {
      case _ => {println("Write ex")}
    }
  }
  
  /**
    * Close the file stream.
    */
  def close() {
    try {
      bufferedOut.close();
    }
    catch {
      case _ => {println("Could not close")}
    }
  }
}
