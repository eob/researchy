// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy.thrift;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

class ThriftReader[T <: TBase[T,F], F <: TFieldIdEnum](file : File) {
  
  val bufferedIn = new BufferedInputStream(new FileInputStream(file), 2048)
  val binaryIn = new TBinaryProtocol(new TIOStreamTransport(bufferedIn))

  def hasNext() : Boolean = {
    try {
      bufferedIn.mark(1)
      val check = bufferedIn.read()
      bufferedIn.reset()
      return check != -1
    }
    catch {
      case _ => {
        println("Couldn't do hasNext")
        return false
      }
    }
  }

  def read(f : () => T) : Option[T] = {
    try {
      val t = f()
      t.read(binaryIn);
      return Some(t)
    }
    catch {
      case _ => {
        println("Read exception")
        return None
      }
    }
  }

  def close() {
    try {
      bufferedIn.close();
    }
    catch {
      case _ => {
        println("could not close")
      }
    }
  }
}

