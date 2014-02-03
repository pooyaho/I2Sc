package ir.phsys.xview.xml

import org.scalatest.FunSuite
import ir.phsys.xview.model.ParseSomething

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 7:23 PM
 */
class XmlObjectifyTest extends FunSuite {

  test("Test objectify") {
    //    val o = XmlObjectify("/home/pooya/projects/I2Sc/src/main/resource")
    //
    //    o.onComplete {
    //      case Failure(f) => println(f)
    //      case Success(s) => {
    //        printObject(s)
    //      }
    //
    //    }
    //
    //    def printObject(p: Project) = {
    //      println(p)
    //    }
    //    while (true) {}

  }
  test("Test regex") {
    val Pattern = "(\\w+)\\s(\\w+)".r

    "Mohamad mir" match {
      case Pattern(x, y, z, g, h) => println(x)
      case Pattern(x) => println(x)
    }


  }

  test("Test Parser") {
    val result = ParseSomething.parseAll(ParseSomething.root, "((12-13)+2)")

    println(result)
  }
}