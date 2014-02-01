package ir.phsys.xview.view

import ir.phsys.xview.layout.FormLayout
import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:23 PM
 */
case class ApplicationForm(var attributes: Map[String, String] = Map.empty[String, String],
                           var layout: FormLayout = null,
                           var widgets: List[Widget] = List.empty[Widget]) extends BaseModel(attributes)
