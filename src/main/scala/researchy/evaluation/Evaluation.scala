// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy.evaluation;

/**
 * Singleton object to provide standard forms of experimental evaluation.
 */
object Evaluate {
  
  def accuracy[A,B](test : Seq[A],
                    gold : Seq[B],
                 correct : (A,B) => Boolean) : Double = {
    require(test.size == gold.size)
    var right = 0
    for ((t,g) <- test zip gold) {
      if (correct(t,g)) right += 1
    }
    return (1.0 * right) / test.size
  }

  def fscore[A,B](test : Seq[A],
                  gold : Seq[B],
                  indiv : (A,B) => FScore) : FScore = {
    require(test.size == gold.size)
    var fscore = new FScore()
    for ((t,g) <- test zip gold) {
      fscore = fscore + indiv(t,g)
    }
    return fscore
  }

}
