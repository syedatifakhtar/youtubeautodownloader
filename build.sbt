import play.PlayImport._
import play.PlayScala
import sbtassembly.Plugin._
import AssemblyKeys._

name := "YoutubeScraper"

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

net.virtualvoid.sbt.graph.Plugin.graphSettings

assemblySettings

jarName in assembly := "YoutubeAutoDownloader.jar"

mainClass in assembly := Some("play.core.server.NettyServer")

fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

resolvers ++= Seq(
  "Atlassian Releases" at "https://maven.atlassian.com/public/",
  "JCenter repo" at "https://bintray.com/bintray/jcenter/",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

mergeStrategy in assembly := {
  case PathList("play", "core", "server", "ServerWithStop.class") => MergeStrategy.last
  case x =>
    val oldStrategy = (mergeStrategy in assembly).value
    oldStrategy(x)
}

libraryDependencies ++= Seq(
  ws exclude("commons-logging", "commons-logging"),
  jdbc,
  anorm,
  "com.softwaremill.macwire" %% "macros" % "2.1.0" %"provided",
  "com.softwaremill.macwire" %% "util" % "2.1.0",
  "com.softwaremill.macwire" %% "proxy" % "2.1.0",
  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.14",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.11",
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
  "com.softwaremill.macwire" %% "macros" % "2.1.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "com.typesafe.play" %% "play" % "2.3.6",
  "net.ceedubs" %% "ficus" % "1.1.2"
)

fork in run := true
