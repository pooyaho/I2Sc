package ir.phsys.xview.model


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 12:25 PM
 */
class DataModel(var attributes: Map[String, String] = Map.empty[String, String],
                var elements: List[Element] = List.empty[Element]) {
  def getUniqueName = attributes.get("domain") match {
    case Some(x) => x + "." + attributes.getOrElse("name", "")
    case _ => attributes.getOrElse("name", "")
  }
}
