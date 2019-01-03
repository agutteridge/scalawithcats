package sandbox.monad

import cats.Eval

object SaferFold {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(foldRight(tail, acc)(fn)).map(rest => fn(head, rest))
      case Nil =>
        Eval.now(acc)
    }
}

object SaferFoldApp extends App {
  import SaferFold._

  println(foldRight(List.fill(50000000)(1), 0)(_ + _).value)
}
