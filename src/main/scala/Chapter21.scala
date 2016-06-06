import scala.annotation.tailrec
import scala.language.implicitConversions


object Chapter21 {

  /**
   * Task 1:
   *
   * How does `->` work? That is, how can `"Hello" -> 42` and `42 -> "Hello"` be pairs
   * `("Hello", 42)` and `(42, "Hello")`? Hint: `Predef.any2ArrowAssoc`.
   *
   * Solution:
   *
   * Currently, as of Scala 2.11.x, its implemented by using `implicit class ArrowAssoc` that
   * enriches any instance with `->` method.
   */

  /**
   * Task 2:
   *
   * Define an operator `+%` that adds a given percentage to a value. For example,
   * `120 +% 10` should be `132`. Hint: Since operators are methods, not functions,
   * you will also need to provide an `implicit`.
   */
  implicit class PercentAdder(private val value: Int) {

    def +%(percent: Int): Int = value + ((value * percent) / 100d).toInt
  }

  // as of Scala 2.11.x not needed any more, since we use implicit class
  //implicit def int2PercentAdder(value: Int): PercentAdder = new PercentAdder(value)

  /**
   * Task 3:
   *
   * Define a `!` operator that computes the factorial of an integer. For example,
   * `5!` is `120`. You will need an enrichment class and an implicit conversion.
   */
  implicit class Int2Factorial(private val value: Int) {

    def ! : Int = {
      @tailrec
      def fact(acc: Int, n: Int): Int = {
        if (n == 1) acc
        else fact(acc * n, n - 1)
      }

      fact(1, value)
    }
  }
}
