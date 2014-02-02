package ir.phsys.xview.dom

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:43 AM
 */

import scala.xml._
import ir.phsys.xview.dom.util.DomUtils._
import ir.phsys.xview.model.view.{Widget, ApplicationForm}
import ir.phsys.xview.model.layout.{Cell, Row, GridType, FormLayout}
import ir.phsys.xview.model.datamodel.{Restriction, Element, DataModel}
import ir.phsys.xview.model.project.Project


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
  private val project = new Project

  def apply(path: String): Project = {
    this.path = path
    recursiveIterateDirectory(path)
    project
  }

  def recursiveIterateDirectory(path: String): Unit = {
    new File(path).listFiles().foreach {
      case x if x.isDirectory => recursiveIterateDirectory(x.getCanonicalPath)
      case x if !x.isDirectory =>
        val loadFile = XML.loadFile(x)
        loadFile.label match {
          case "dataModel" =>
            for (dm <- loadFile \\ loadFile.label) {
              val datamodel = generateDataModelGraph(dm)
              project += datamodel
            }
          case "application" =>
            for (dm <- loadFile \\ loadFile.label) {
              val app = generateApplicationModel(dm)
              project += app
            }

          case "layout" =>
            for (dm <- loadFile \\ loadFile.label) {
              val layout = generateLayoutModelGraph(dm)
              project += layout
            }
          case _ =>
        }
    }
  }

  def generateDataModelGraph(dataModelNode: Node): DataModel = {

    val dm = new DataModel
    dm.attributes = dataModelNode.getAttributeAsMap
    println(dm.attributes)
    for (defType <- dataModelNode \ "type") {
      dm.definedTypes +:= generateDataModelGraph(defType)
    }

    for (e <- dataModelNode \ "elements"; domElem <- e.child) domElem match {
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
          element.restrictions = restriction
        }

        dm.elements :+= element
      case x =>
    }
    dm
  }

  def generateApplicationModel(domApp: Node): ApplicationForm = {

    val app = new ApplicationForm
    app.attributes = domApp.getAttributeAsMap
    for (l <- domApp \ "layout") {
      app.layout = generateLayoutModelGraph(l)
    }
    domApp.child.filter(p => p.label != "layout" && p.isInstanceOf[Elem]).foreach(w => {
      val widget = new Widget
      widget.attributes = w.getAttributeAsMap
      widget.widgetType = w.label
      for (l <- w \ "layout") {
        widget.layout = generateLayoutModelGraph(l)
      }
      app.widgets :+= widget
    })
    app
  }

  def generateLayoutModelGraph(node: Node): FormLayout = {

    val layout = new FormLayout
    layout.attributes = node.getAttributeAsMap

    for (g <- node \ "grid") {
      val gridType = new GridType
      gridType.attributes = g.getAttributeAsMap

      for (r <- g \ "row") {
        val row = new Row
        row.attributes = r.getAttributeAsMap
        for (c <- r \ "cell") {
          val cell = new Cell
          cell.attributes = c.getAttributeAsMap
          for (w <- c.child.filter(p => p.isInstanceOf[Elem])) {
            val widget = new Widget
            widget.attributes = w.getAttributeAsMap
            widget.widgetType = w.label
            cell.widgets :+= widget
          }
          for (l <- c \ "layout") {
            cell.formLayout = generateLayoutModelGraph(l)
          }
          row.cells :+= cell
        }
        gridType.rows :+= row
      }
      layout.gridType = gridType
    }
    layout
  }
}