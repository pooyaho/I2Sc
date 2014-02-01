package ir.phsys.xview.view

import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:27 PM
 */
case class Widget(var widgetType: String = "",
                  var attributes: Map[String, String] = Map.empty[String, String])
  extends BaseModel(attributes)
