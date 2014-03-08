

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 11:23 AM
 */


import scala.xml._
import ir.phsys.xview.xml.util.DomUtils._
import ir.phsys.xview.model.layout.{Cell, Row, GridType, Layout}
import ir.phsys.xview.model.view.{Page, Widget}
import org.scalatest.FunSuite

class XmlParserTest extends FunSuite {
  test("Test layout xml") {
    val file = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/input/layout.xml")

    for (l <- file \\ "_" filterNot (_.label == "cell")) {
      //      val layout = extractLayout(l)
      println(l)
    }
  }
  test("Test form xml") {
    val file = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/form.xml")
    for (a <- file \\ "application") {
      val app = new Page
      app.attributes = a.getAttributeAsMap
      for (l <- a \ "layout") {
        app.layout = Some(extractLayout(l))
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

  def extractLayout(node: Node): Layout = {
    val layout = new Layout
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
      layout.gridType = Some(gridType)
    }
    layout
  }
}