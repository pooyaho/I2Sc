package ir.phsys.xview.project

import akka.actor.{ActorRef, Actor}
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor.{ObjectifySuccess, Objectify}
import ir.phsys.xview.generator.CodeGeneratorActor.{CodeGenerate, CodeGenSuccess}
import ir.phsys.xview.analyze.actor.AnalyzerActor.{Analyze, AnalyzeSuccess}


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 23.02.14
 *         Time: 18:55
 */

object ProjectActor {

  class OperationReplay

  case class OperationFailed(t: Throwable) extends OperationReplay

  case class OperationSucceed(id: Int) extends OperationReplay

  case class Initialize(analyzerActor: ActorRef, objectifierActor: ActorRef, codeGenActor: ActorRef)

  case class ChangeAnalyzerActor(analyzerActor: ActorRef)

  case class ChangeObjectifierActor(objectifierActor: ActorRef)

  case class ChangeCodeGenActor(codeGenActor: ActorRef)

  case class ProcessPath(inputPath: String, outputPath: String = null)

}

class ProjectActor extends Actor {

  import ir.phsys.xview.project.ProjectActor._

//  private var actorsMap = Map.empty[Int, ActorRef]
  private var analyzerActor: Option[ActorRef] = None
  private var objectifierActor: Option[ActorRef] = None
  private var codeGenActor: Option[ActorRef] = None

  def receive: Actor.Receive = {
    case Initialize(a, o, c) =>
      analyzerActor = Some(a)
      objectifierActor = Some(o)
      codeGenActor = Some(c)

    case ChangeCodeGenActor(c) =>
      codeGenActor = Some(c)

    case ChangeObjectifierActor(o) =>
      objectifierActor = Some(o)

    case ChangeAnalyzerActor(a) =>
      analyzerActor = Some(a)

    case ProcessPath(input, output) =>
      objectifierActor.get ! Objectify(input)

    case ObjectifySuccess(p, id) =>
      analyzerActor.get ! Analyze(p)

    case CodeGenSuccess(id) =>


    case AnalyzeSuccess(id, p) =>
      codeGenActor.get ! CodeGenerate()
  }

  def checkActors = objectifierActor.isDefined && analyzerActor.isDefined && codeGenActor.isDefined


}
