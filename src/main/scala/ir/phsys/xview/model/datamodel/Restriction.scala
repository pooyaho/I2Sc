package ir.phsys.xview.model.datamodel

import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 11:18 AM
 */
class Restriction(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                  var values: Map[String, String] = Map.empty[String, String]) extends BaseModel
