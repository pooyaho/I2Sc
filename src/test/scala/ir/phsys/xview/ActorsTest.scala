package ir.phsys.xview

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor
import ir.phsys.xview.generator.web.actor.WebCodeGenerator
import ir.phsys.xview.project.ProjectActor
import ir.phsys.xview.project.ProjectActor._

//import ir.phsys.xview.generator.java.JavaCodeGeneratorActor

import ir.phsys.xview.analyze.actor.AnalyzerActor
import scala.concurrent.duration._
/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/8/14
 *         Time: 3:03 PM
 */
class ActorsTest(_system: ActorSystem) extends TestKit(_system)
                                               with FunSuite
                                               with ShouldMatchers
                                               with BeforeAndAfterAll
                                               with ImplicitSender {

  def this() = this(ActorSystem("PostponeSpec"))

  override def afterAll(): Unit = system.shutdown()

  test("Run actors") {
    val generator = system.actorOf(WebCodeGenerator.props(1))
    val analyzer = system.actorOf(AnalyzerActor.props(2))
    val objectifier = system.actorOf(XmlObjectifyActor.props(3))
    val project = system.actorOf(ProjectActor.props(4))

    project ! Initialize(analyzer, objectifier, generator)

    project ! ProcessPath("/home/pooya/projects/I2Sc/src/main/resource/input/simple","/home/pooya/projects/I2Sc/src/main/resource/input/simple")

    //    objectifier ! Objectify("/home/pooya/projects/I2Sc/src/main/resource/input/simple")
    expectMsg(20 seconds,TotalOperationSucceed)
  }
}
