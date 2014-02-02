package ir.phsys.xview.model.layout

import ir.phsys.xview.model.view.Widget
import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:27 PM
 */
case class FormLayout(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                      var gridType: GridType = null) extends BaseModel

case class GridType(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                    var rows: List[Row] = List.empty[Row]) extends BaseModel

case class Row(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
               var cells: List[Cell] = List.empty[Cell]) extends BaseModel

case class Cell(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                var widgets: List[Widget] = List.empty[Widget],
                var formLayout: FormLayout = null) extends BaseModel