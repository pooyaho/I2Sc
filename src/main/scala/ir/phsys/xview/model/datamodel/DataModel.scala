package ir.phsys.xview.model.datamodel

import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 12:25 PM
 */
class DataModel(var attributes: Map[String, String] = Map.empty[String, String],
                var elements: List[Element] = List.empty[Element],
                var definedTypes: List[DataModel] = List.empty[DataModel]) extends BaseModel(attributes)
