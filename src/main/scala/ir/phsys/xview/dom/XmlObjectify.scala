package ir.phsys.xview.dom

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:43 AM
 */

import scala.xml._
import ir.phsys.xview.model.{DataModel, Restrictions, Element}
import ir.phsys.xview.dom.util.DomUtils._

class XmlObjectify {
  def generateDataModelGraph() = {
    (XmlObjectify.loadFile \\ "dataModel").map {
      case model => {
        //      val name = DomUtils.getAttributeValue(dm, "name")
        //      val domain = DomUtils.getAttributeValue(dm, "domain")

        val elements = (model \\ "elements").map {
          case e => {
            e.child.map {
              case elem=>
              val restrictions = (elem \ "restriction").map {
                case rest => {
                  val restMap = rest.child.map {
                    case Elem(prefix, label, attribs, scope, Text(text)) => label -> text
                    case _ => "" -> ""
                  }.toMap -- List("")
                  new Restrictions(rest.getAttributeAsMap, restMap)
                }
              }
              new Element(elem.label, restrictions.head, elem.getAttributeAsMap)
            }
          }
        }.toList
        new DataModel(model.getAttributeAsMap, elements.flatten)
      }
    }
  }
}

object XmlObjectify {
  private var path = ""
  private var loadFile: Elem = _

  def apply(path: String): XmlObjectify = {
    this.path = path
    loadFile = XML.loadFile(path)
    new XmlObjectify
  }
}