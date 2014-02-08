package ir.phsys.xview.xml

import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 7:23 PM
 */
class XmlObjectifyTest extends FunSuite {

  test("Test objectify") {

  }
  test("Test regex") {
    val Pattern = "(\\w+)\\s(\\w+)".r

    "Mohamad mir" match {
      case Pattern(x, y, z, g, h) => println(x)
      case Pattern(x) => println(x)
    }
  }

  test("Test Parser") {  }
}
