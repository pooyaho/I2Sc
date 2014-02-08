package ir

import org.scalatest.FunSuite
import org.fusesource.scalate.TemplateEngine

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/4/14
 *         Time: 6:34 PM
 */
class ScalateTest extends FunSuite {
  test("Test output of template") {
    val engine = new TemplateEngine()
    val map = Map("name" ->("Pooya", "Husseini"), "city" -> "Tehran")
    val output = engine.layout("/home/pooya/projects/I2Sc/src/main/resource/template/template1.ssp", map)

    assert(output=="Hello Pooya Husseini, from Tehran.")
  }
}