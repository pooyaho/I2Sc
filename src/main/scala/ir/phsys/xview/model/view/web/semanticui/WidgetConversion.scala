package ir.phsys.xview.model.view.web.semanticui

import ir.phsys.xview.model.view.Widget

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 1:33 PM
 */
object WidgetConversion {
  private lazy val WidgetsMap = Map(

  )

  implicit class WidgetConverter(w: Widget) {
    def extractText() = {
      """<hey></hey>"""
    }
  }

}