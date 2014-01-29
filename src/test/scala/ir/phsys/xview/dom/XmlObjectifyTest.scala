package ir.phsys.xview.dom

import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 7:23 PM
 */
class XmlObjectifyTest extends FunSuite {

  test("Test objectify") {
    val o = XmlObjectify("/home/pooya/projects/I2Sc/src/main/resource/main.xml")
    val dm = o.generateDataModelGraph()
    dm foreach println

  }



}
