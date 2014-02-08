package ir.phsys.xview.analyze.actor

import akka.actor.Actor
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.analyze.exception.AttributeNotFoundException
import ir.phsys.xview.model.view.Page
import ir.phsys.xview.model.datamodel.DataModel
import ir.phsys.xview.model.layout.Layout
import ir.phsys.xview.model.BaseModel
import scala.util.{Failure, Success, Try}
import ir.phsys.xview.xml.XmlObjectifyActor.{OperationSucceed, OperationFailed}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/3/14
 *         Time: 10:49 AM
 */


object AnalyzerActor {

  case class Analyze(project: Project)

}

class AnalyzerActor(id: Int) extends Actor {

  import AnalyzerActor._
  import ir.phsys.xview.analyze.ConstantAttributes._

  def receive: Actor.Receive = {
    case Analyze(p) =>
      Try(analyzeProject(p)) match {
        case Success(s) => sender ! OperationSucceed(id)

        case Failure(f) => sender ! OperationFailed(f)
      }
  }

  def analyzeProject(project: Project) = {

    val pages = project.getPages.allPages
    //    val pages = (Map(application.getUniqueName -> application) ++ project.getPages).values.toList

    def checkAttributesValidity() = {
      checkPageAttributes(pages)
      checkLayoutAttributes(project.getLayouts.values.toList)
      checkDataModelAttributes(project.getDataModels.values.toList)
    }

    pages.foreach(page => {
      val dm = page.attributes("dataModel")
      val layout = page.attributes("layout")
      project.getDataModels.contains(dm, recursive = true)
      project.getLayouts.contains(layout, recursive = true)
    })


    //    pages.foreach(page => {
    //      val dm = page.attributes("dataModel")
    //      if (!project.getDataModels.contains(dm)) {
    //        val inners = project.getDataModels.filter {
    //          case (k, v) => v.hasInnerClass
    //        }.map {
    //                       case (k, v) => k + "." + v.getUniqueName
    //                     }.toList
    //
    //        if (!inners.contains(dm)) {
    //          throw new DataModelNotFoundException(s"Data model $dm in page ${page.getUniqueName} is not found!")
    //        }
    //
    //      }
    //    })



    checkAttributesValidity()

    //    foreach (page => {
    //      val dm = page.attributes("dataModel")
    //      if (!p.getDataModelMap.contains(dm)) {
    //        throw new DataModelNotFoundException(s"Data model $dm in page ${page.getUniqueName} is not found!")
    //      }
    //    })


  }

  private def checkPageAttributes(p: List[Page]) = {
    p.foreach(page => {
      PageMandatoryAttributes.intersect(page.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))
      checkElementAttributes(page.widgets)
      page.widgets.foreach(widget => {
        widget.layout match {
          case Some(layout) => checkLayoutAttributes(List(layout))
          case None =>
        }
      })
    })
  }

  private def checkDataModelAttributes(models: List[DataModel]): Unit = {
    models foreach (model => {
      DataModelMandatoryAttributes.intersect(model.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))
      checkElementAttributes(model.elements)
      checkDataModelAttributes(model.dataModels)
    })
  }

  private def checkLayoutAttributes(ls: List[Layout]): Unit = {
    ls.foreach(l => {
      LayoutMandatoryAttributes.intersect(l.attributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))
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
      ElementMandatoryAttributes.intersect(elem.getAttributes.keys.toList)
      .foreach(m => throw new AttributeNotFoundException(m))

    })
  }

}