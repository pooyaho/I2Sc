import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:23 AM
 */

import scala.xml._

class XmlParserTest extends FunSuite {
  test("Test xml iteration") {
    val data = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/main.xml")

    for (entry <- data \\ "application") {
      println(entry.attributes)
    }
  }
}