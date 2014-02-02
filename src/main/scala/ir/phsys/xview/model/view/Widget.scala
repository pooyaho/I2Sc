package ir.phsys.xview.model.view

import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty
import ir.phsys.xview.model.layout.FormLayout

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:27 PM
 */
case class Widget(var widgetType: String = "",
                  @BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                  var layout: FormLayout = null)
  extends BaseModel
