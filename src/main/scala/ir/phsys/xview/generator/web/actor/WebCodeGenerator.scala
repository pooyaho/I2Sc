package ir.phsys.xview.generator.web.actor

import akka.actor.Actor
import ir.phsys.xview.generator.CodeGeneratorActor
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.generator.CodeGeneratorActor._
import ir.phsys.xview.generator.template.Engine

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 5:28 PM
 */
class WebCodeGenerator extends CodeGeneratorActor {
  val viewModel = "/home/pooya/projects/I2Sc/src/main/resource/template/web/page.ssp"

  def receive: Actor.Receive = {
    case Generate(p) =>
      for (page <- p.getPages.allPages){
        val result=Engine(viewModel, Map("page" -> page))
        println(result)
      }
  }

  def generateDataModels(project: Project): Unit = ???

  def generateViewModels(project: Project): Unit = ???
}
