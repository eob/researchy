// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy.evaluation;

class FScore() {

  // These are doubles instead of ints to enable flexible accounting
  // when appropriate.
  var _tp = 0.0
  var _fp = 0.0
  var _fn = 0.0
  var _tn = 0.0

  // Accessors
  def tp = _tp
  def fp = _fp
  def fn = _fn
  def tn = _tn

  // Mutators
  def setTP(tp : Double) {_tp = tp}
  def setFP(fp : Double) {_fp = fp}
  def setFN(fn : Double) {_fn = fn}
  def setTN(tn : Double) {_tn = tn}
  
  // Convenience Mutators
  def incTP(tp : Double = 1.0) {_tp += tp}
  def incFP(fp : Double = 1.0) {_fp += fp}
  def incFN(fn : Double = 1.0) {_fn += fn}
  def incTN(tn : Double = 1.0) {_tn += tn}
  
  def precision : Double = {
    val denom = _tp + _fp
    if (denom == 0) return 0
    else return (_tp / denom)
  }
      
  def recall : Double = {
    val denom = _tp + _fn
    if (denom == 0) return 0
    else return (_tp / denom)
  }

  def size : Double = {
    _tp + _fp + _fn + _tn
  }
      
  def f1 : Double = {
    val denom = precision + recall
    if (denom == 0) return 0
    else return (2 * (precision * recall) / denom)
  }
  
  def +(operand:FScore) : FScore = {
     val out = new FScore()
     out.setTP(this.tp + operand.tp)
     out.setFP(this.fp + operand.fp)
     out.setFN(this.fn + operand.fn)
     out.setTN(this.tn + operand.tn)
     out
   }
  
   override def toString : String = {
     "<N:%1.2f, P:%1.2f, R:%1.2f, F:%1.2f>".format(size, precision * 100.0, recall * 100.0, f1 * 100.0) 
   }
}
