package sandbox.superadder

import cats.kernel.Monoid

case class Order(totalCost: Double, quantity: Double)

object Order {
  implicit val monoidOrder: Monoid[Order] = new Monoid[Order] {
    def empty: Order = Order(0d, 0d)

    def combine(x: Order, y: Order): Order = Order(
      totalCost = x.totalCost + y.totalCost,
      quantity = x.quantity + y.quantity
    )
  }
}
