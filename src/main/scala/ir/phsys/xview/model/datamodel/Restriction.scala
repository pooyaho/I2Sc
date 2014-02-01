package ir.phsys.xview.model.datamodel

import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 11:18 AM
 */
class Restriction(var attributes: Map[String, String] = Map.empty[String, String],
                  var values: Map[String, String] = Map.empty[String, String]) extends BaseModel(attributes)
