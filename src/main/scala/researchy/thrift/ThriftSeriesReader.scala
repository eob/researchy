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

class ThriftSeriesReader[T <: TBase[T,F], F <: TFieldIdEnum](directory : String, base : String) {
  
  var current = -1
  var binaryIn : Option[TBinaryProtocol] = None
  var bufferedIn : Option[BufferedInputStream] = None

  loadNextFile()

  def loadNextFile() : Boolean = {
    close()
    current += 1
    // Does the file exist?
    val fIn = new File(directory, "%s_%04d".format(base, current))
    if (fIn.exists()) {
      bufferedIn = Some(new BufferedInputStream(new FileInputStream(fIn), 2048))
      binaryIn = Some(new TBinaryProtocol(new TIOStreamTransport(bufferedIn.get)))
      return true
    }
    else {
      binaryIn = None
      bufferedIn = None
      return false
    }
  }
  
  def hasNext() : Boolean = {
    bufferedIn match {
      case None => return false 
      case Some(bin) => {
        try {
          bin.mark(1)
          val check = bin.read()
          bin.reset()
          val isANext = check != -1
          if (isANext) return true
          else {
            if (loadNextFile()) {
              return hasNext()
            }
            else {
              return false
            }
          }
        }
        catch {
          case _ => {
            println("Couldn't do hasNext")
            return false
          }
        }
      }
    }
  }

  def read(f : () => T) : Option[T] = {
    binaryIn match {
      case Some(bin) => {
        try {
          val t = f()
          t.read(bin);
          return Some(t)
        }
        catch {
          case _ => {
            println("Read exception")
            return None
          }
        }
      }
      case None => return None
    }
  }

  def close() {
    bufferedIn match {
      case None => {}
      case Some(bin) => {
        try {
          bin.close();
        }
        catch {
          case _ => {
            println("could not close")
          }
        }
      }
    }
  }
}
