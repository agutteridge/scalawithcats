package sandbox.branching

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object Tree {
  import cats.Functor

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
      tree match {
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
        case Leaf(value) => Leaf(f(value))
      }
  }

  // smart constructor so that compiler applies implicit tree functor to branches and leaves
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
  def leaf[A](value: A): Tree[A] = Leaf(value)
}


object Branching extends App {
  import cats.implicits._
  println(Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2))
}
