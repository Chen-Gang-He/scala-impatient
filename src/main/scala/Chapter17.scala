

/**
 * Task 1:
 *
 * Define an immutable `class Pair[T, S]` with a method `swap` that returns a new pair with
 * the components swapped.
 */
package task1701 {

class Pair[T, S](val first: T, val second: S) {

  def swap(): Pair[S, T] = new Pair(second, first)
}

}

/**
 * Task 2:
 *
 * Define a mutable `class Pair[T]` with a method `swap` that swaps the components of the pair.
 */
package task1702 {

class Pair[T](var first: T, var second: T) {

  def swap(): Unit = {
    val tmp = first
    first = second
    second = tmp
  }
}

}

/**
 * Task 3:
 *
 * Given a `class Pair[T, S]`, write a generic method `swap` that takes a pair as its argument
 * and returns a new pair with the components swapped.
 */
object Chapter17Task03 {

  import task1701._

  def swap[T, S](pair: Pair[T, S]): Pair[S, T] = new Pair(pair.second, pair.first)

}

/**
 * Task 4:
 *
 * Why don't we need a lower bound for the `replaceFirst` method in Section 17.3,
 * "Bounds for Type Variables”, on page 232 if we want to replace the first component of
 * a `Pair[Person]` with a `Student`?
 *
 * Solution:
 *
 * We don't need a lower bound because we replacing with a sub-class, which is OK, since
 * the result type is still `Pair[Person]`.
 */
package task1704 {

class Person(val name: String)
class Student(name: String) extends Person(name)

class Pair[T](val first: T, val second: T) {

  def replaceFirst(newFirst: T): Pair[T] = new Pair(newFirst, second)
}

/**
 * Task 5:
 *
 * Why does `RichInt` implement `Comparable[Int]` and not `Comparable[RichInt]`?
 *
 * Solution:
 *
 * To be able to use view bounds, like
 * {{{
 *   class Pair[T <% Comparable[T]](val first: T, val second: T)
 * }}}
 * which than can be used with `Int` types, like
 * {{{
 *   new Pair(1, 2)
 * }}}
 * we need to have implicit conversion from `T` to `Comparable[T]`. `RichInt` class
 * implements `Comparable[Int]` and there is implicit conversion from `Int` to `RichInt`.
 * So, we don't use `RichInt` class directly.
 */

}
