package ir.phsys.xview.model.view.web.angular

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 1:33 PM
 */
//class WidgetConversion(var attributes: Map[String, String] = Map.empty[String, String])

//case class SimpleWidgetConversion(widgetName: String,
//                                  override var attributes: Map[String, String] = Map.empty[String, String],
//                                  var children: List[SimpleWidgetConversion] = List.empty[SimpleWidgetConversion])
//  extends WidgetConversion(attributes)

case class TemplateWidgetConversion(path: String,
                                    var attributes: Map[String, String] = Map.empty[String, String])
//  extends WidgetConversion(attributes)

import ir.phsys.xview.model.view.Widget
import ir.phsys.xview.generator.template.Engine

trait Attribute {
  def toString: String

  def toMap: Map[String, String]
}

case class HtmlAttribute(key: String = null, value: String = null) extends Attribute {
  override def toString: String = (key, value) match {
    case ("", null) => ""
    case (null, null) => ""
    case (k, null) => s"$k"
    case (k, "") => s"$k"
    case (k, v) => s"$k='$v'"
  }

  def toMap: Map[String, String] = Map(key -> value)
}


object WidgetConversion {
  val templatePath = "/home/pooya/projects/I2Sc/src/main/resource/template/web/bootstrap/"

  private lazy val WidgetsMap = Map[String, TemplateWidgetConversion](
    "comboBox" -> TemplateWidgetConversion("widget-select", Map("type" -> "combo")),
    "checkBox" -> TemplateWidgetConversion("widget-input", Map("type" -> "checkBox")),
    "radioButton" -> TemplateWidgetConversion("widget-input", Map("type" -> "radio")),
    "label" -> TemplateWidgetConversion("widget-input", Map("type" -> "static")),
    "listBox" -> TemplateWidgetConversion("widget-select", Map("type" -> "list")),
    "textBox" -> TemplateWidgetConversion("widget-input", Map("type" -> "text")),
    "button" -> TemplateWidgetConversion("widget-input", Map("type" -> "button")),
    "container" -> TemplateWidgetConversion("panel"),
    "image" -> TemplateWidgetConversion("widget-input")
    ,"label" -> TemplateWidgetConversion("widget-input")
    ,"datePicker" -> TemplateWidgetConversion("datePicker")
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

  private lazy val widgetAttributes = Map[String, Function[Attribute, Attribute]](
    "radioButton" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("groupName", v) => HtmlAttribute("groupName", v)
        case _ => HtmlAttribute()
      }
    },
    "label" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case _ => HtmlAttribute()
      }
    },
    "image" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("src", v) => HtmlAttribute("src", v)
        case HtmlAttribute("alt", v) => HtmlAttribute("alt", v)
        case HtmlAttribute("content", v) => HtmlAttribute("content", v)
        case _ => HtmlAttribute()
      }
    },
    "checkBox" -> new Function[Attribute, Attribute] {
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
    "container" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match{
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
    },
    "button" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case _ => HtmlAttribute()
      }
    },
    "comboBox" -> new Function[Attribute, Attribute] {
      def apply(v1: Attribute): Attribute = v1 match {
        case HtmlAttribute("source", v) => HtmlAttribute("source", v)
        case _ => HtmlAttribute()
      }
    }
  )

  implicit class WidgetConverter(w: Widget) {
    def convert(): String = {
      val wc = WidgetsMap(w.widgetType)
      var attributes = Map.empty[String,String]
      attributes ++= wc.attributes
//                     .map {
//        case (k, v) => HtmlAttribute(k, v)
//      }
      val tempAttr = w.attributes.map(p => {
        val attr = HtmlAttribute(p._1, p._2)
        List(generalAttributes(attr), widgetAttributes(w.widgetType)(attr))
      }).flatten

      tempAttr.foreach(
      p=>attributes ++= p.toMap
      )

      wc match {
//        case SimpleWidgetConversion(name, _, children) => "" //(name, attributes)
        case TemplateWidgetConversion(path, _) => Engine(templatePath + path+".ssp",  attributes)
      }

    }
  }

}