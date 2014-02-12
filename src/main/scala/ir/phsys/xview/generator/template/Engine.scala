package ir.phsys.xview.generator.template

import org.fusesource.scalate.TemplateEngine

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 8:35 PM
 */
object Engine {
  private lazy val engine = new TemplateEngine()

  def apply(path: String, attributes: Map[String, Any]): String = engine.layout(path, attributes)
}
