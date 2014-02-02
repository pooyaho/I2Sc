package ir.phsys.xview.model.project

import ir.phsys.xview.model.datamodel.DataModel
import ir.phsys.xview.model.layout.FormLayout
import ir.phsys.xview.model.view.Page
import ir.phsys.xview.model.exception.ApplicationAlreadyDefinedException

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/2/14
 *         Time: 12:08 PM
 */
class Project {
  private var dataModelMap = Map.empty[String, DataModel]
  private var layoutMap = Map.empty[String, FormLayout]
  private var application: Option[Page] = None
  private var pageMap = Map.empty[String, Page]

  def +=(m: DataModel) = {
    dataModelMap ++= Map(m.getUniqueName -> m)

  }

  def +=(m: FormLayout) = {
    layoutMap ++= Map(m.getUniqueName -> m)
  }

  def +=(m: Page) = {
    pageMap ++= Map(m.getUniqueName -> m)
  }

  def setApplication(m: Page) = application match {
    case None => application = Some(m)
    case Some(x) => throw new ApplicationAlreadyDefinedException
  }
}