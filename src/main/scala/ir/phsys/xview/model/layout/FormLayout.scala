package ir.phsys.xview.model.layout

import ir.phsys.xview.model.view.Widget
import ir.phsys.xview.model.{ModelContainer, BaseModel}
import scala.beans.BeanProperty
import ir.phsys.xview.model.exception.LayoutAlreadyDefinedException
import ir.phsys.xview.analyze.exception.LayoutCellNotFoundException

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:27 PM
 */
class Layout(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                  var gridType: Option[GridType] = None) extends BaseModel {

  def putWidgetToCell(cellName: String, w: Widget) = gridType match {
    case None =>
    case Some(g) =>
      g.rows.flatMap(c => c.cells.filter(_.attributes.contains(cellName))) match {
        case Nil => throw new LayoutCellNotFoundException(s"Cell $cellName in layout $getUniqueName")
        case x :: xs => x.widgets :+= w
      }
  }
}


class GridType(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                    var rows: List[Row] = List.empty[Row]) extends BaseModel

class Row(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
               var cells: List[Cell] = List.empty[Cell]) extends BaseModel

class Cell(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                var widgets: List[Widget] = List.empty[Widget],
                var layout: Option[Layout] = None) extends BaseModel

class Layouts extends ModelContainer[String, Layout] {

  def +=(m: Layout) = {
    // it does not check inner classes
    if (map.contains(m.getUniqueName))
      throw new LayoutAlreadyDefinedException(s"Layout ${m.getUniqueName}")
    nameSet += m.getUniqueName
    map ++= Map(m.getUniqueName -> m)
  }
}