package ir.phsys.xview.model

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 12:35 PM
 */

class Element(val elemType: String, var restriction: Restrictions, val attributes: Map[String, String])

abstract class ElementType

case object IntegerType extends ElementType

case object BooleanType extends ElementType

case object ListType extends ElementType

case object MapType extends ElementType

case object CompositionType extends ElementType

case object DateTimeType extends ElementType

case object CurrencyType extends ElementType

case object DecimalType extends ElementType

case object FileType extends ElementType

case object DoubleType extends ElementType

case object StringType extends ElementType

case object Type extends ElementType

