// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy;

import com.codahale.logula.Logging

/**
 * Record timing information.
 */
object Timers extends Logging {
  var _times = Map[String, Long]()
  var _timingFile = "managed/timing.csv"

  def tick(name : String) {
    if (_times.contains(name)) {
      log.warn("Called tick tick with no tock! For key: %s", name)
    }
    _times = _times + (name -> System.currentTimeMillis())
  }

  def tock(name : String) : Double = {
   val now = System.currentTimeMillis()
   var seconds = -1.0     
   var then = -1.0
   if (_times.contains(name)) {
     then = _times(name)
     seconds = (now - then) / 1000.0
     _times = _times - name
   }
   else {
     log.warn("Called tock with no tick: %s", name)
   }
   Dump.csv(Seq(name, then.toString, now.toString, seconds.toString), _timingFile)
   return seconds
  }
}
