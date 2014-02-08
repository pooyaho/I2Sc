package ir.phsys.xview.model.project

import ir.phsys.xview.model.datamodel.DataModels
import ir.phsys.xview.model.layout.Layouts
import ir.phsys.xview.model.view.Pages

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/2/14
 *         Time: 12:08 PM
 */
class Project {

  private val layouts = new Layouts
  private val pages = new Pages
  private val dataModels = new DataModels

  def getLayouts = layouts

  def getDataModels = dataModels

  def getPages = pages
}