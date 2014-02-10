package ir.phsys.xview.analyze.actor

import akka.actor.{Props, ActorRef, Actor}
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.analyze.exception.AttributeNotFoundException
import ir.phsys.xview.model.view.Page
import ir.phsys.xview.model.datamodel.DataModel
import ir.phsys.xview.model.layout.Layout
import ir.phsys.xview.model.BaseModel
import scala.util.{Failure, Success, Try}
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor
import XmlObjectifyActor.{OperationSucceed, OperationFailed}
import ir.phsys.xview.generator.CodeGenerator.Generate
import grizzled.slf4j.Logger

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/3/14
 *         Time: 10:49 AM
 */


object AnalyzerActor {

  case class Analyze(project: Project)

  def props(codeGeneratorActor: ActorRef, id: Int): Props = Props(new AnalyzerActor(codeGeneratorActor, id))
}

class AnalyzerActor(codeGeneratorActor: ActorRef, id: Int) extends Actor {
  val logger = Logger[this.type]

  import AnalyzerActor._
  import ir.phsys.xview.analyze.ConstantAttributes._

  def receive: Actor.Receive = {
    case Analyze(p) =>
      Try(analyzeProject(p)) match {
        case Success(s) => sender ! OperationSucceed(id)
          codeGeneratorActor ! Generate(p)
        case Failure(f) =>
          logger.warn("Failure during analyzing!", f)
          sender ! OperationFailed(f)
      }
  }

  def transformWidgetToLayout(project: Project) = {
    for (p <- project.getPages.allPages;
         layoutName = p.attributes("layout");
         layout = project.getLayouts.get(layoutName)) {

    }

  }

  def analyzeProject(project: Project) = {
    checkPageLayoutAndDataModelAvailability(project)
    checkPageAttributes(project.getPages.allPages)
    checkLayoutAttributes(project.getLayouts.values.toList)
    checkDataModelAttributes(project.getDataModels.values.toList)

    transformWidgetToLayout(project)
  }

  def checkPageLayoutAndDataModelAvailability(project: Project) {
    project.getPages.allPages.foreach(page => {
      val dm = page.attributes("dataModel")
      val layout = page.attributes("layout")
      project.getDataModels.contains(dm, recursive = true)
      project.getLayouts.contains(layout, recursive = true)
    })
  }

  private def checkPageAttributes(pages: List[Page]) = {
    pages.foreach(page => {
      PageMandatoryAttributes.diff(page.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))

      //      checkElementAttributes(page.widgets)
      //      page.widgets.foreach(widget => {
      //        widget.layout match {
      //          case Some(layout) => checkLayoutAttributes(List(layout))
      //          case None =>
      //        }
      //      })
    })
  }

  private def checkDataModelAttributes(models: List[DataModel]): Unit = {
    models foreach (model => {
      DataModelMandatoryAttributes.diff(model.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))
      checkElementAttributes(model.elements)
      checkDataModelAttributes(model.dataModels)
    })
  }

  private def checkLayoutAttributes(ls: List[Layout]): Unit = {
    ls.foreach(l => {
      LayoutMandatoryAttributes.diff(l.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(s"In layout ${l.attributes} and attribute $m"))
      l.gridType match {
        case Some(x) =>
          x.rows.foreach(r => {
            r.cells.foreach(c => {
              c.layout match {
                case Some(layout) => checkLayoutAttributes(List(layout))
                case None =>
              }
              checkElementAttributes(c.widgets)
              c.widgets.foreach(widget => {
                widget.layout match {
                  case Some(layout) => checkLayoutAttributes(List(layout))
                  case None =>
                }

              })
            })

          })
        case None =>
      }
    })
  }

  private def checkElementAttributes(p: List[BaseModel]) = {
    p foreach (elem => {
      ElementMandatoryAttributes.diff(elem.getAttributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))
    })
  }

}