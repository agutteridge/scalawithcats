package sandbox.monad

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object Factorial {

  type FactorialLogger[A] = Writer[Vector[String], A]

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): FactorialLogger[Int] =
    for {
      ans <-
        if (n == 0)
          1.pure[FactorialLogger]
        else
          slowly(factorial(n - 1).map(_ * n))
      withLog <- ans.writer(Vector(s"fact $n $ans"))
    } yield withLog
}

object FactorialApp extends App {
  import Factorial._

  val result: Vector[FactorialLogger[Int]] = Await.result(Future.sequence(Vector(
    Future(factorial(3)),
    Future(factorial(4))
  )), 5.seconds)

  result.foreach { fl =>
    println(fl.run)
  }
}