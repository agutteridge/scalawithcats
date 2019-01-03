package sandbox.superadder

import cats.Monoid


object SuperAdder extends App {
  import cats.instances.int._
  import cats.syntax.semigroup._

  private def add[A](items: List[A])(implicit m: Monoid[A]): A =
    items.reduce(_ |+| _)

  println(add(List(1, 2, 3, 4, 5)))

  import cats.instances.option._
  println(add(List[Option[Int]](Some(1), Some(2), Some(3), Some(4), Some(5))))

  val order1 = Order(1.0, 2.0)
  val order2 = Order(3.0, 4.0)

  println(add(List(order1, order2)))
}
