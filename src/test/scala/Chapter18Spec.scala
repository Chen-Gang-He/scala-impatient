import Chapter18._
import TestUtils.withOutput
import org.scalatest.{FlatSpec, Matchers}

class Chapter18Spec extends FlatSpec with Matchers {

  "Bug" should "has move, turn, and show methods" in {
    //given
    val bugsy = new Bug()

    //when
    val out = withOutput {
      bugsy.move(4).show().move(6).show().turn().move(5).show()
    }

    //then
    out shouldBe " 4 10 5"
  }
}
