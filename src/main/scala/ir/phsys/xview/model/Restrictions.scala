package ir.phsys.xview.model


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 2:32 PM
 */
//class Restrictions(/*var attributes: Map[String, String] = Map.empty[String, String], */var restriction: Map[String,
//  String] = Map.empty[String, String])


abstract class RestrictionMode

case object Any extends RestrictionMode

case object All extends RestrictionMode

case object None extends RestrictionMode