package ir.phsys.xview.analyze

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/3/14
 *         Time: 11:08 AM
 */
object ConstantAttributes {
  lazy val PageMandatoryAttributes = List("domain", "name", "dataModel")
  lazy val DataModelMandatoryAttributes = List("name")
  lazy val ElementMandatoryAttributes = List("name")
  lazy val LayoutMandatoryAttributes = DataModelMandatoryAttributes

}
