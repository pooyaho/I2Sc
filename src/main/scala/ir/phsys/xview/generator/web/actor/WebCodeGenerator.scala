package ir.phsys.xview.generator.web.actor

import akka.actor.{Props, Actor}
import ir.phsys.xview.generator.CodeGeneratorActor
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.generator.CodeGeneratorActor._
import ir.phsys.xview.generator.template.Engine
import java.io.File
import scala.util.{Failure, Success, Try}
import scala.xml.PrettyPrinter

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 5:28 PM
 */

object WebCodeGenerator {
  def props: Props = Props(new WebCodeGenerator)
}

class WebCodeGenerator extends CodeGeneratorActor {

  import ir.phsys.xview.util.io.FileUtils._

  val viewTemplate = "/home/pooya/projects/I2Sc/src/main/resource/template/web/bootstrap/page.ssp"
  val widgetTemplate = "/home/pooya/projects/I2Sc/src/main/resource/template/web/bootstrap/widget.ssp"

  def receive: Actor.Receive = {
    case GenerateCode(path, p, jid) =>
      Try(
        for (page <- p.getPages.allPages) {
          val result = Engine(viewTemplate, Map("page" -> page))
//          val printer=new PrettyPrinter(90,2)

          new File(s"$path/${page.attributes("name")}.html") <# result
        }
      ) match {
        case Success(s) =>
          sender ! CodeGenSuccess(jid)
        case Failure(f) =>
          sender ! CodeGenFailure(f, jid)
      }
  }

  def generateDataModels(project: Project): Unit = ???

  def generateViewModels(project: Project): Unit = ???

}