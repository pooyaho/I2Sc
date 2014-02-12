package ir.phsys.xview

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor
import ir.phsys.xview.generator.web.actor.WebCodeGenerator

//import ir.phsys.xview.generator.java.JavaCodeGeneratorActor

import ir.phsys.xview.analyze.actor.AnalyzerActor
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor.Objectify

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/8/14
 *         Time: 3:03 PM
 */
class ActorsTest(_system: ActorSystem) extends TestKit(
  _system)                                     with FunSuite with ShouldMatchers with BeforeAndAfterAll with ImplicitSender {

  def this() = this(ActorSystem("PostponeSpec"))

  override def afterAll(): Unit = system.shutdown()

  test("Run actors") {
    val generator = system.actorOf(Props[WebCodeGenerator])
    val analyzer = system.actorOf(AnalyzerActor.props(generator, 1))
    val objectifier = system.actorOf(XmlObjectifyActor.props(analyzer, 2))

    objectifier ! Objectify("/home/pooya/projects/I2Sc/src/main/resource")

    while (true) {}

  }
}
