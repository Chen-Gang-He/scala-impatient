import Chapter19._
import java.util.{Calendar, Date}
import org.scalatest.{FlatSpec, Matchers}

class Chapter19Spec extends FlatSpec with Matchers {

  "ExprEvaluator" should "handle / and % operations" in {
    //given
    val e = new ExprEvaluator

    //when & then
    e.eval("3 - 4 * 5") shouldBe -17
    e.eval("3 - 8 / 4 * 5 + 3 % 4") shouldBe -4
  }

  "ExprEvaluator2" should "handle ^ operator" in {
    //given
    val e = new ExprEvaluator2

    //when & then
    e.eval("3 - 4 * 5") shouldBe -17
    e.eval("3 - 8 / 4 * 5 + 3 % 4") shouldBe -4
    e.eval("4 ^ 2 ^ 3") shouldBe 65536
    e.eval("1 + 2 * 4 ^ 2 ^ 3 * 2 - 1") shouldBe 262144
  }

  "IntListParser" should "parse a list of integers into a List[Int]" in {
    //given
    val p = new IntListParser

    //when & then
    p.parse("()") shouldBe List()
    p.parse("(1)") shouldBe List(1)
    p.parse("(1, 23, -79)") shouldBe List(1, 23, -79)
  }

  "DateTimeParser" should "date and time expressions in ISO 8601" in {
    //given
    val p = new DateTimeParser

    //when & then
    p.parse("2005-08-09T18:31:42.123") shouldBe date(2005, 8, 9, 18, 31, 42, 123)
    p.parse("20050809T183142123") shouldBe date(2005, 8, 9, 18, 31, 42, 123)
    p.parse("2005-08-09T18:31:42") shouldBe date(2005, 8, 9, 18, 31, 42, 0)
    p.parse("20050809T183142") shouldBe date(2005, 8, 9, 18, 31, 42, 0)
    p.parse("2005-08-09") shouldBe date(2005, 8, 9, 0, 0, 0, 0)
    p.parse("20050809") shouldBe date(2005, 8, 9, 0, 0, 0, 0)
  }

  private def date(y: Int, m: Int, d: Int, h: Int, mm: Int, s: Int, ss: Int): Date = {
    val cal = Calendar.getInstance()
    cal.set(y, m - 1, d, h, mm, s)
    cal.set(Calendar.MILLISECOND, ss)
    cal.getTime
  }
}
