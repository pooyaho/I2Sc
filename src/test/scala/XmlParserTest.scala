import ir.phsys.xview.layout.{Cell, Row, GridType, FormLayout}
import ir.phsys.xview.view.{ApplicationForm, Widget}
import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:23 AM
 */


import scala.xml._
import ir.phsys.xview.dom.util.DomUtils._

class XmlParserTest extends FunSuite {
  test("Test layout xml") {
    val file = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/layout.xml")

    for (l <- file \\ "layout") {
      val layout = extractLayout(l)
      println(layout)
    }
  }
  test("Test form xml") {
    val file = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/form.xml")
    for (a <- file \\ "application") {
      val app = new ApplicationForm
      app.attributes = a.getAttributeAsMap
      for (l <- a \ "layout") {
        app.layout = extractLayout(l)
      }
      a.child.filter(p => p.label != "layout").foreach(w => {
        val widget = new Widget
        widget.attributes = w.getAttributeAsMap
        widget.widgetType = w.label
        app.widgets :+= widget
      })

      println(app)
    }
  }

  def extractLayout(node: Node): FormLayout = {
    val layout = new FormLayout
    layout.attributes = node.getAttributeAsMap

    for (g <- node \ "grid") {
      val gridType = new GridType
      gridType.attributes = g.getAttributeAsMap

      for (r <- g \\ "row") {
        val row = new Row
        row.attributes = r.getAttributeAsMap
        for (c <- r \\ "cell") {
          val cell = new Cell
          cell.attributes = c.getAttributeAsMap

          for (w <- c.child) {
            val widget = new Widget
            widget.attributes = w.getAttributeAsMap
            widget.widgetType = w.label
            cell.widgets :+= widget
          }
          row.cells :+= cell
        }

        gridType.rows :+= row
      }
      layout.gridType = gridType
    }
    layout
  }
}