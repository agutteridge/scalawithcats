package sandbox.printable

import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.kernel.{Eq, Monoid}
import cats.syntax.eq._

object Cat {
  implicit val printableCat = new Printable[Cat] {
    override def format(cat: Cat): String = s"${cat.name} is a ${cat.age} year-old ${cat.colour} cat."
  }

  implicit val showCat: Show[Cat] =
    Show.show(cat => s"${cat.name} is a ${cat.age} year-old ${cat.colour} cat.")


  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      cat1.name.toLowerCase === cat2.name.toLowerCase &&
      cat1.age === cat2.age &&
      cat1.colour.toLowerCase === cat2.colour.toLowerCase
    }
}

final case class Cat(name: String, age: Int, colour: String)


