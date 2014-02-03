package ir.phsys.xview.xml

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:43 AM
 */

import scala.xml._
import ir.phsys.xview.xml.util.DomUtils._
import ir.phsys.xview.model.layout._
import ir.phsys.xview.model.datamodel.{Restriction, Element, DataModel}
import ir.phsys.xview.model.project.Project
import akka.actor.Actor
import scala.util.Try
import ir.phsys.xview.model.layout.GridType
import ir.phsys.xview.model.view.Page
import scala.util.Failure
import ir.phsys.xview.model.layout.Cell
import ir.phsys.xview.model.layout.Row
import scala.util.Success
import ir.phsys.xview.model.view.Widget

object XmlObjectifyActor {

  case class Objectify(path: String)

  class OperationReplay

  case class OperationFailed(t: Throwable) extends OperationReplay

  case object OperationSucceed extends OperationReplay

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

class XmlObjectifyActor extends Actor {

  import java.io.File
  import XmlObjectifyActor._

  //  private var path = ""


  //  def apply(path: String): Future[Project] = {
  //    this.path = path
  //    val f = Future {
  //      {
  //        recursiveIterateDirectory(path)
  //        project
  //      }
  //    }
  //    f
  //  }
  def generateProject(path: String): Try[Project] = {
    val project = new Project

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
                val app = generatePageModel(dm)
                project.setApplication(app)
              }

            case "layout" =>
              for (dm <- loadFile \\ loadFile.label) {
                val layout = generateLayoutModelGraph(dm)
                project += layout
              }

            case "page" =>
              for (dm <- loadFile \\ loadFile.label) {
                val app = generatePageModel(dm)
                project += app
              }

            case _ =>

          }
      }
    }
    Try({
      recursiveIterateDirectory(path)
      println("Completed")
      project
    })
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
          element.restrictions = Some(restriction)
        }
        dm.elements :+= element
      case x =>
    }
    dm
  }

  def generatePageModel(domApp: Node): Page = {

    val app = new Page
    app.attributes = domApp.getAttributeAsMap
    for (l <- domApp \ "layout") {
      app.layout = Some(generateLayoutModelGraph(l))
    }
    domApp.child.filter(p => p.label != "layout" && p.isInstanceOf[Elem]).foreach(w => {
      val widget = new Widget
      widget.attributes = w.getAttributeAsMap
      widget.widgetType = w.label
      for (l <- w \ "layout") {
        widget.layout = Some(generateLayoutModelGraph(l))
      }
      app.widgets :+= widget
    })
    app
  }

  def generateLayoutModelGraph(node: Node): Layout = {
    val layout = new Layout
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
            cell.layout = Some(generateLayoutModelGraph(l))
          }
          row.cells :+= cell
        }
        gridType.rows :+= row
      }
      layout.gridType = Some(gridType)
    }
    layout
  }

  def receive: Actor.Receive = {
    case Objectify(path) =>
      println("Hello")
      generateProject(path) match {
        case Success(s) => sender ! OperationSucceed
        case Failure(f) => sender ! OperationFailed(f)
      }

  }
}