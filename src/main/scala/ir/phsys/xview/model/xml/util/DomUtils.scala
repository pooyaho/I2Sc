package ir.phsys.xview.xml.util

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 3:22 PM
 */

import scala.xml._
import ir.phsys.xview.xml.exception.AttributeNotFoundException

object DomUtils {

  implicit class DomUtils(node: Node) {

    def getAttributeValue(key: String): Option[String] = {
      node.attribute(key) match {
        case Some(seq) =>
          seq match {
            case x :: Nil =>
              Some(x.text)
            case x :: y :: xs => None
            case Nil => None
          }
        case None =>
          throw new AttributeNotFoundException
      }
    }

    def getAttributeAsMap: Map[String, String] = {
      node.attributes.map {
        x => x.key -> x.value.head.text
      }.toMap
    }
  }
}