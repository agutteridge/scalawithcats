package sandbox.printable

import sandbox.domain.Box

trait Printable[A] { self =>
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      override def format(value: B): String =
        self.format(func(value))
    }
}

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(value: String): String = s"a String: $value"
  }

  implicit val printableInt = new Printable[Int] {
    override def format(value: Int): String = s"an Int: ${value.toString}"
  }

  implicit val printableBoolean = new Printable[Boolean] {
    override def format(value: Boolean): String = if (value) "yes" else "no"
  }

  implicit def printableBox[A](implicit printableInstance: Printable[A]) =
    printableInstance.contramap[Box[A]](_.value)
}

object Printable {
  def format[A](value: A)(implicit printable: Printable[A]): String = printable.format(value)

  def print[A](value: A)(implicit printable: Printable[A]): Unit = println(printable.format(value))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit printable: Printable[A]): String = printable.format(value)

    def print()(implicit printable: Printable[A]): Unit = println(printable.format(value))
  }
}