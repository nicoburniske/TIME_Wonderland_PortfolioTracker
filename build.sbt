import sbt.util

name         := "web3"
version      := "0.1"
scalaVersion := "2.13.7"

val classMain    = "nicoburniske.web3.Main"
val web3jVersion = "4.8.7"
val circeVersion = "0.15.0-M1"
val sttpVersion  = "3.3.18"
resolvers += Resolver.sonatypeRepo("releases")
enablePlugins(CalibanPlugin)

val dependencies = Seq(
  "org.web3j"                      % "core"                            % web3jVersion,
  "org.scalaj"                    %% "scalaj-http"                     % "2.4.2",
  "com.github.tototoshi"          %% "scala-csv"                       % "1.3.10",
  "io.monix"                      %% "monix"                           % "3.4.0",
  "org.rogach"                    %% "scallop"                         % "4.1.0",
  "com.typesafe.scala-logging"    %% "scala-logging"                   % "3.9.4",
  "ch.qos.logback"                 % "logback-classic"                 % "1.2.3",
  "com.github.ghostdogpr"         %% "caliban-client"                  % "1.3.2",
  "com.softwaremill.sttp.client3" %% "core"                            % sttpVersion,
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-monix" % sttpVersion,
  "com.bot4s"                     %% "telegram-core"                   % "5.3.0"
) ++ Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_                            % circeVersion)

lazy val app = (project in file(".")).settings(
  libraryDependencies ++= dependencies,
  Compile / run / mainClass        := Some(classMain),
  Compile / run / logLevel         := util.Level.Debug,
  Compile / packageBin / mainClass := Some(classMain),
  assembly / logLevel              := util.Level.Info,
  assembly / mainClass             := Some(classMain),
  assembly / assemblyJarName       := "web3-logger.jar",
  Compile / caliban / calibanSettings += calibanSetting(file("SushiExchangeSchema.graphql"))(cs =>
    cs.splitFiles(true)
      .packageName("generated.sushi.exchange")
      .genView(true)
      .imports("nicoburniske.web3.utils.Implicits._")
      .scalarMapping("Bytes" -> "String"))
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}
