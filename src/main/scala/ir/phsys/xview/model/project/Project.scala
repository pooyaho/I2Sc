package ir.phsys.xview.model.project

import ir.phsys.xview.model.datamodel.DataModel
import ir.phsys.xview.model.layout.Layout
import ir.phsys.xview.model.view.Page
import ir.phsys.xview.model.exception.{PageAlreadyDefinedException, LayoutAlreadyDefinedException, DataModelAlreadyDefinedException, ApplicationAlreadyDefinedException}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/2/14
 *         Time: 12:08 PM
 */
class Project {
  private var layoutMap = Map.empty[String, Layout]
  private var application: Option[Page] = None
  private var pageMap = Map.empty[String, Page]




  def +=(m: Layout) = {
    if (layoutMap.contains(m.getUniqueName))
      throw new LayoutAlreadyDefinedException(s"Layout ${m.getUniqueName}")
    layoutMap ++= Map(m.getUniqueName -> m)
  }

  def +=(m: Page) = {
    if (pageMap.contains(m.getUniqueName) ||
      (application.isDefined && application.get.getUniqueName == m.getUniqueName))
      throw new PageAlreadyDefinedException(s"Page ${m.getUniqueName}")
    pageMap ++= Map(m.getUniqueName -> m)
  }

  def setApplication(m: Page) = application match {
    case None =>
      if (pageMap.contains(m.getUniqueName))
        throw new PageAlreadyDefinedException(s"Application ${m.getUniqueName}")
      application = Some(m)
    case Some(x) => throw new ApplicationAlreadyDefinedException(s"Application ${m.getUniqueName}")
  }

  def getApplication = application

  def getLayoutMap = layoutMap

  def getDataModelMap = dataModelMap

  def getPageMap = pageMap

}