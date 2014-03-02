package ir.phsys.xview.project

import akka.actor.{Props, ActorRef, Actor}
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor.{ObjectifySuccess, Objectify}
import ir.phsys.xview.generator.CodeGeneratorActor.{CodeGenFailure, GenerateCode, CodeGenSuccess}
import ir.phsys.xview.analyze.actor.AnalyzerActor.{Analyze, AnalyzeSuccess}
import grizzled.slf4j.Logger
import java.io.{FileOutputStream, FileInputStream, File}
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.nio.file.Files.copy
import java.nio.file.Paths.get






/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 23.02.14
 *         Time: 18:55
 */

object ProjectActor {

  class OperationReplay

  case class TotalOperationFailed(t: Throwable, jobId: Int) extends OperationReplay

  //  case object OperationSucceed extends OperationReplay

  case class TotalOperationSucceed(jobId: Int) extends OperationReplay

  case class Initialize(analyzerActor: ActorRef, objectifierActor: ActorRef, codeGenActor: ActorRef)

  case class ChangeAnalyzerActor(analyzerActor: ActorRef)

  case class ChangeObjectifierActor(objectifierActor: ActorRef)

  case class ChangeCodeGenActor(codeGenActor: ActorRef)

  case class ProcessPath(inputPath: String, outputPath: String = null, jobId: Int)

  def props(id: Int): Props = Props(new ProjectActor(id))
}

class ProjectActor(id: Int) extends Actor {

  import ir.phsys.xview.project.ProjectActor._

  //  private var actorsMap = Map.empty[Int, ActorRef]
  private var analyzerActor: Option[ActorRef] = None
  private var objectifierActor: Option[ActorRef] = None
  private var codeGenActor: Option[ActorRef] = None
  private var inputPath = ""
  private var outputPath = ""
  private var sendersMap = Map.empty[Int, ActorRef]
  private val logger = Logger[this.type]

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

    case ProcessPath(input, output, jid) =>
      inputPath = input
      outputPath = output
      objectifierActor.get ! Objectify(input, jid)

      sendersMap ++= Map(jid -> sender)

    case ObjectifySuccess(p, jId) =>
      analyzerActor.get ! Analyze(p, jId)

    case CodeGenSuccess(jid) =>
      logger.info("Total process completes successfully!")
      sendersMap(jid) ! TotalOperationSucceed(jid)

    case CodeGenFailure(t, jid) =>
      logger.warn("Total process failure!")
      sendersMap(jid) ! TotalOperationFailed(t, jid)

    case AnalyzeSuccess(p, jId) =>
      codeGenActor.get ! GenerateCode(outputPath, p, jId)

  }

  def checkActors = objectifierActor.isDefined && analyzerActor.isDefined && codeGenActor.isDefined
  implicit def toPath (filename: String) = get(filename)

//  def copyFilesToOutput(input:String,output:String)={
//    val addresses=new File(input).listFiles().filter(p=> !p.getName.endsWith("xml"))
//
//    addresses.foreach(src=>{
//      val dest=new File(output)
//
//      new FileOutputStream(dest) getChannel() transferFrom(
//        new FileInputStream(src).getChannel, 0, Long.MaxValue )
//    })
//
//  }
}