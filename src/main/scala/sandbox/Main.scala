package sandbox

import sandbox.domain.Box

object Main extends App {
  import cats.instances.string._
  import cats.syntax.semigroup._
  println("Hello " |+| "Cats!")

  import sandbox.printable.{Cat, Printable}
  val cat = Cat("moggo", 44, "sparkly")
  Printable.print(cat)

  import sandbox.printable.PrintableSyntax.PrintableOps
  cat.copy(age = 45).print()

  import cats.syntax.show._
  println(cat.copy(age = 46).show)

  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")

  import cats.syntax.eq._
  println(cat1 === cat2)
  println(cat1 === cat2.copy(name = "Garfield", age = 38))

  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  import cats.instances.option._
  println(optionCat1 =!= optionCat2)

  import cats.instances.int._
  println(1 |+| 2)

  import Printable._
  import sandbox.printable.PrintableInstances._
  println(format(Box("hello world")))
  println(format(Box(123)))
  println(format(Box(true)))

  import sandbox.codec.Codec._
  import sandbox.codec.CodecInstances._
  println(encode(123.4))
  println(decode[Double]("123.4"))
  println(encode(Box(123.4)))
  println(decode[Box[Double]]("123.4"))

  import cats.Monad
  // also requires import cats.instances.option._
  val optionMonad1 = Monad[Option].pure(55)
  println(optionMonad1)
  import cats.syntax.applicative._
  val optionMonad2 = 56.pure[Option]
  println(optionMonad2)

  import cats.syntax.either._
  val three = 3.asRight[String]
  println(three)
}
