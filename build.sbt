import sbt.util

name := "web3"
version := "0.1"
scalaVersion := "2.13.7"

val classMain = "nicoburniske.web3.Main"
val web3jVersion = "4.8.7"
val circeVersion = "0.15.0-M1"
resolvers += Resolver.sonatypeRepo("releases")

val dependencies = Seq(
  "org.web3j" % "core" % web3jVersion,
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "io.monix" %% "monix" % "3.4.0",
  "org.rogach" %% "scallop" % "4.1.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
) ++ Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

mainClass in(Compile, run) := Some(classMain)
mainClass in(Compile, packageBin) := Some(classMain)

lazy val app = (project in file("."))
  .settings(
    libraryDependencies ++= dependencies,
    assembly / assemblyJarName := "web3-logger.jar",
    assembly / mainClass := Some(classMain),
    assembly / logLevel := util.Level.Info
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}
