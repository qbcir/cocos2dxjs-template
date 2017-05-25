import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Settings {
  val name = """cocos2dxjs-template"""
  val version = "0.0.1"

  object versions {
    val scala = "2.12.2"
    val scalaJsDom = "0.9.2"
    val scalatags = "0.6.5"
  }

  val jsDependencies = Def.setting(Seq(

  ))

  val scalaJsDependencies = Def.setting(Seq(
    "com.github.qbcir" % "scalajs-cocos2dx_sjs0.6_2.12" % "0.0.1-SNAPSHOT" changing(),
    "org.scala-js" %%% "scalajs-dom" % versions.scalaJsDom,
    "com.lihaoyi" %%% "scalatags" % versions.scalatags
  ))
}