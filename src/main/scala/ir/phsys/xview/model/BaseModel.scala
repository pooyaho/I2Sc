package ir.phsys.xview.model

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 9:24 PM
 */
class BaseModel(var attrs: Map[String, String] = Map.empty[String, String]) {
  def getUniqueName = attrs.get("domain") match {
    case Some(x) => x + "." + attrs.getOrElse("name", "")
    case _ => attrs.getOrElse("name", "")
  }
}
