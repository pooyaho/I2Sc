package ir.phsys.xview.model.view

import ir.phsys.xview.model.layout.Layout
import ir.phsys.xview.model.{ModelContainer, BaseModel}
import scala.beans.BeanProperty
import ir.phsys.xview.model.exception.{ApplicationAlreadyDefinedException, PageAlreadyDefinedException}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:23 PM
 */

case class Page(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                var layout: Option[Layout] = None,
                var widgets: List[Widget] = List.empty[Widget])
  extends BaseModel

class Pages extends ModelContainer[String, Page] {
  private var application: Option[Page] = None

  def +=(m: Page): Unit = {
    // it does not check inner classes
    if (map.contains(m.getUniqueName) || (application.isDefined && application.get.getUniqueName == m.getUniqueName))
      throw new PageAlreadyDefinedException(s"Page ${m.getUniqueName}")

    nameSet += m.getUniqueName
    map ++= Map(m.getUniqueName -> m)
  }

  def setApplication(m: Page) = application match {
    case None =>
      if (map.contains(m.getUniqueName))
        throw new PageAlreadyDefinedException(s"Application ${m.getUniqueName}")
      application = Some(m)
    case Some(x) => throw new ApplicationAlreadyDefinedException(s"Application ${m.getUniqueName}")
  }

  def isApplicationDefined = application.isDefined

  def getApplication = application

  def allPages: List[Page] = (if (isApplicationDefined) {
    Map(application.get.getUniqueName -> application.get) ++ map
  } else {
    map
  }).values.toList

}