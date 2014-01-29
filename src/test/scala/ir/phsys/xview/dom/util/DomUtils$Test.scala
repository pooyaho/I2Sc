package ir.phsys.xview.dom.util

import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 7:53 PM
 */
class DomUtils$Test extends FunSuite {

  import DomUtils._
  import scala.xml._

  val content = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/main.xml")

  test("Test converting attributes to map") {
    val map = content.getAttributeAsMap
    println(map)
    assert(map == Map("name" -> "Class1", "domain" -> "com.casp.poo"))

  }

  test("Test xml text and content") {
    (content \ "dataModel").foreach {
      case x => assert(x.label == "dataModel")
    }
    (content \ "elements" \\ "restriction").foreach {
      case x => x.child.foreach {
        case Elem(prefix, label, attribs, scope, Text(text)) => println(label + "->" + text)
        case _ =>
      }
    }
  }

  test("Test data model xml") {
    (content \\ "dataModel").map {
      case model => {
        //      val name = DomUtils.getAttributeValue(dm, "name")
        //      val domain = DomUtils.getAttributeValue(dm, "domain")

        (model \\ "elements").map {
          case e => {
            e.child.map {
              case elem =>
                (elem \\ "restriction").map {
                  case rest => {
                    rest.child.map {
                      case Elem(prefix, label, attribs, scope, Text(text)) => println(elem.label + "  rest is" + label)
//                      case Elem(prefix, label, attribs, scope, child)=>println("Hey")
                      case _ => println("Nothing")
                    }
                    //                    new Restrictions(rest.getAttributeAsMap, restMap)
                  }
                }
              //                new Element(elem.label, restrictions.head, elem.getAttributeAsMap)
            }
          }
        }.toList
        //        new DataModel(model.getAttributeAsMap, elements.flatten)
      }

    }
  }
}
