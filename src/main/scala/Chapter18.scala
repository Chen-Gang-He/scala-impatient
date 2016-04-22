import scala.beans.BeanProperty

object Chapter18 {

  /**
   * Task 1:
   *
   * Implement a `Bug` class modeling a bug that moves along a horizontal line.
   * The `move` method moves in the current direction,
   * the `turn` method makes the bug turn around,
   * and the `show` method prints the current position.
   * Make these methods chainable. For example,
   * {{{
   *   bugsy.move(4).show().move(6).show().turn().move(5).show()
   * }}}
   * should display `4 10 5`.
   */
  class Bug {

    private var position: Int = 0
    private var turnedAround: Boolean = false

    def move(steps: Int): this.type = {
      if (turnedAround) position -= steps
      else position += steps
      this
    }

    def turn(): this.type = {
      turnedAround = !turnedAround
      this
    }

    def show(): this.type = {
      print(" ")
      print(position)
      this
    }
  }

  /**
   * Task 2:
   *
   * Provide a fluent interface for the `Bug` class of the preceding exercise, so that one can
   * write
   * {{{
   *   bugsy move 4 and show and then move 6 and show turn around move 5 and show
   * }}}
   */
  trait FluentBug { this: Bug =>

    def and(obj: Show.type): this.type = this.show()

    def and(obj: Then.type): this.type = this

    def turn(obj: Around.type): this.type = this.turn()
  }

  object Show
  object Then
  object Around

  val show = Show
  val then = Then
  val around = Around

  /**
   * Task 3:
   *
   * Complete the fluent interface in Section 18.1, "Singleton Types", on page 246
   * so that one can call
   * {{{
   *   book set Title to "Scala for the Impatient" set Author to "Cay Horstmann"
   * }}}
   */
  trait FluentDocument { this: Document =>

    private var useNextArgsAs: Option[Any] = None

    def set(obj: Title.type): this.type = setNextArgsAs(obj)
    def set(obj: Author.type): this.type = setNextArgsAs(obj)

    def to(arg: String): this.type = {
      for (obj <- useNextArgsAs) obj match {
        case Title => setTitle(arg)
        case Author => setAuthor(arg)
      }

      this
    }

    private def setNextArgsAs(obj: Any): this.type = {
      useNextArgsAs = Some(obj)
      this
    }
  }

  object Title
  object Author

  class Document {

    @BeanProperty var title: String = null
    @BeanProperty var author: String = null
  }

  class Book extends Document with FluentDocument {

    def addChapter(chapter: String): this.type = this
  }

  /**
   * Task 4:
   *
   * Implement the `equals` method for the `Member` class that is nested inside the `Network`
   * class in Section 18.2, "Type Projections", on page 247. For two members to be equal,
   * they need to be in the same network.
   */
  class Network {

    class Member {

      override def equals(that: Any): Boolean = that match {
        case _: Member => true
        case _ => false
      }
    }
  }

  /**
   * Task 5:
   *
   * Consider the type alias
   * {{{
   *   type NetworkMember = n.Member forSome { val n: Network }
   * }}}
   * and the function
   * {{{
   *   def process(m1: NetworkMember, m2: NetworkMember) = (m1, m2)
   * }}}
   * How does this differ from the `process` function in Section 18.8, "Existential Types",
   * on page 252?
   *
   * Solution:
   *
   * The difference is that `NetworkMember` defines type alias for `Member` from any `Network`,
   * which is the same as defining `process` function like this:
   * {{{
   *   def process[M >: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)
   * }}}
   * While the `process` function, from Section 18.8, accepts only members from the same network,
   * and it is defined like this:
   * {{{
   *   def process[M <: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)
   * }}}
   */
  import scala.language.existentials

  type NetworkMember = n.Member forSome { val n: Network }

  def process(m1: NetworkMember, m2: NetworkMember) = (m1, m2)

  def processAny[M >: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)

  def processSame[M <: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)
}
