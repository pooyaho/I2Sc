package ir.phsys.xview.dom

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:43 AM
 */

import scala.xml._
import ir.phsys.xview.model.{Restriction, DataModel, Element}
import ir.phsys.xview.dom.util.DomUtils._

class XmlObjectify {

  //    lst
  //    (XmlObjectify.loadFile \\ "dataModel").map {
  //      case model => {
  //        //      val name = DomUtils.getAttributeValue(dm, "name")
  //        //      val domain = DomUtils.getAttributeValue(dm, "domain")
  //
  //        val elements = (model \\ "elements").map {
  //          case e => {
  //            e.child.map {
  //              case elem=>
  //              val restrictions = (elem \ "restriction").map {
  //                case rest => {
  //                  val restMap = rest.child.map {
  //                    case Elem(prefix, label, attribs, scope, Text(text)) => label -> text
  //                    case _ => "" -> ""
  //                  }.toMap -- List("")
  //                  new Restrictions(rest.getAttributeAsMap, restMap)
  //                }
  //              }
  //              new Element(elem.label, restrictions.head, elem.getAttributeAsMap)
  //            }
  //          }
  //        }.toList
  //        new DataModel(model.getAttributeAsMap, elements.flatten)
  //      }
  //    }

}

object XmlObjectify {

  import java.io.File

  private var path = ""
  private var dataModelMap = Map.empty[String, DataModel]

  def apply(path: String): XmlObjectify = {
    this.path = path
    recursiveIterateDirectory(path)

    new XmlObjectify
  }

  def recursiveIterateDirectory(path: String): Unit = {
    new File(path).listFiles().foreach {
      case x if x.isDirectory => recursiveIterateDirectory(x.getCanonicalPath)
      case x if !x.isDirectory =>
        val loadFile = XML.loadFile(x)
        if ((loadFile \\ "dataModel").length > 0) {
          val datamodel = generateDataModelGraph(loadFile)
          datamodel foreach (x => {
            dataModelMap ++= Map(x.getUniqueName -> x)
          })

        } else if ((loadFile \\ "application").length > 0) {

        } else if ((loadFile \\ "layout").length > 0) {

        } else {
//          throw new UndefinedXmlDataException
        }

    }
  }

  def generateDataModelGraph(loadedFile: Elem): List[DataModel] = {
    var lst = List.empty[DataModel]

    for (domModel <- loadedFile \\ "dataModel") {

      val dm = new DataModel
      dm.attributes = domModel.getAttributeAsMap
      for (e <- domModel \ "elements"; domElem <- e.child) domElem match {
        case x: Elem =>
          val element = new Element

          element.attributes = x.getAttributeAsMap
          element.elemType = x.label

          for (r <- x \ "restriction"; attrs = r.getAttributeAsMap) {

            val restriction = new Restriction()
            restriction.attributes = attrs
            for (rest <- r.child) rest match {
              case x: Elem =>
                restriction.values ++= Map(rest.label -> rest.text)

              case _ =>
            }
            element.restrictions :+= restriction
          }

          dm.elements :+= element
        case x =>
      }
      lst :+= dm
    }
    lst
  }
}