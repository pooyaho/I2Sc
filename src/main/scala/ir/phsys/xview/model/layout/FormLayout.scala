package ir.phsys.xview.layout

import ir.phsys.xview.view.Widget
import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:27 PM
 */
case class FormLayout(var attributes: Map[String, String] = Map.empty[String, String],
                      var gridType: GridType = null) extends BaseModel(attributes)

case class GridType(var attributes: Map[String, String] = Map.empty[String, String],
                    var rows: List[Row] = List.empty[Row]) extends BaseModel(attributes)

case class Row(var attributes: Map[String, String] = Map.empty[String, String],
               var cells: List[Cell] = List.empty[Cell]) extends BaseModel(attributes)

case class Cell(var attributes: Map[String, String] = Map.empty[String, String],
                var widgets: List[Widget] = List.empty[Widget],
                var formLayout: FormLayout = null) extends BaseModel(attributes)