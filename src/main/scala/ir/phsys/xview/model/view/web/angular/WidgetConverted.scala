package ir.phsys.xview.model.view.web.angular

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 1:33 PM
 */

case class WidgetConverted(widgetName: String, attributes: Map[String, String])

import ir.phsys.xview.model.view.Widget

trait Attribute {
  def toString: String
}

case class HtmlAttribute(key: String = null, value: String = null) extends Attribute {
  override def toString: String = (key, value) match {
    case ("", null) => ""
    case (null, null) => ""
    case (k, null) => s"$k"
    case (k, "") => s"$k"
    case (k, v) => s"$k='$v'"
  }
}

object WidgetConversion {
  private lazy val WidgetsMap = Map(
    "combo" -> WidgetConverted("form-select", Map("type" -> "combo")),
    "checkBox" -> WidgetConverted("form-input", Map("type" -> "checkBox")),
    "radioButton" -> WidgetConverted("form-input", Map("type" -> "radio")),
    "label" -> WidgetConverted("form-input", Map("type" -> "static")),
    "listBox" -> WidgetConverted("form-select", Map("type" -> "list")),
    "textBox" -> WidgetConverted("form-input", Map("type" -> "text"))
  )

  private def generalAttributes(attr: Attribute): Attribute = attr match {
    case HtmlAttribute("name", v) => HtmlAttribute("id", v)
    case HtmlAttribute("label", v) => HtmlAttribute("label", v)
//    case HtmlAttribute("enable", "true") => HtmlAttribute("")
    case HtmlAttribute("enable", "false") => HtmlAttribute("disabled", "")
    case HtmlAttribute("visible", v) => HtmlAttribute("visible", v)
    case HtmlAttribute("tabindex", v) => HtmlAttribute("tabindex", v)
//    case HtmlAttribute("height", v) => HtmlAttribute("")
//    case HtmlAttribute("width", v) => HtmlAttribute("")
    case _ => HtmlAttribute()
  }

  private lazy val widgetAttributes = Map(
    "radioButton" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("groupName", v) => HtmlAttribute("groupName", v)
        case _ => HtmlAttribute()
      }
    },
    "checked" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("checked", "true") => HtmlAttribute("checked")
        case HtmlAttribute("checked", "false") => HtmlAttribute("")
        case _ => HtmlAttribute()
      }
    },
    "listBox" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("selectionMode", "single") => HtmlAttribute("selection", "single")
        case HtmlAttribute("selectionMode", "multiple") => HtmlAttribute("selection", "multiple")
        case _ => HtmlAttribute()
      }
    },

    "textBox" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("textAlignment", "left") => HtmlAttribute("text-alignment", "left")
        case HtmlAttribute("textAlignment", "right") => HtmlAttribute("text-alignment", "right")
        case HtmlAttribute("textAlignment", "center") => HtmlAttribute("text-alignment", "center")
        case HtmlAttribute("textAlignment", "justified") => HtmlAttribute("text-alignment", "justified")
        case _ => HtmlAttribute()
      }
    }
  )

  implicit class WidgetConverter(w: Widget) {
    def convert(): (String, Set[Attribute]) = {
      val wc = WidgetsMap(w.widgetType)
      var attributes = Set.empty[Attribute]
      attributes ++= wc.attributes.map {
        case (k, v) => HtmlAttribute(k, v)
      }

      val tempAttr= w.attributes.map(p => {
        val attr = HtmlAttribute(p._1, p._2)
        List(generalAttributes(attr), widgetAttributes(w.widgetType)(attr))
      }).flatten

      attributes ++=tempAttr
      (wc.widgetName, attributes)
    }
  }
}