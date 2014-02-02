package ir.phsys.xview.model

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 9:24 PM
 */
trait BaseModel {
  def  getAttributes: Map[String, String]

  def getUniqueName = getAttributes.get("domain") match {
    case Some(x) => x + "." + getAttributes.getOrElse("name", "")
    case _ => getAttributes.getOrElse("name", "")
  }
}
