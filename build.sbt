import sbt.Keys.licenses

val supportedScalaVersions = Seq(
  "2.12.8",
  "2.13.0"
)
lazy val globalSettings = Seq(
  organization := "com.alessandromarrella",
  name := "fs2-elastic",
  scalaVersion := "2.12.8",
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  scmInfo:= Some(
    ScmInfo(
      url("https://github.com/amarrella/fs2-elastic"),
      "scm:git@github.com:amarrella/fs2-elastic.git"
    )
  ),
  crossScalaVersions := supportedScalaVersions,
  homepage := Some(url("https://github.com/amarrella/fs2-elastic")),
  licenses += "MIT" -> url(
  "https://github.com/amarrella/fs2-mongodb/blob/master/LICENSE"),
  developers += Developer("amarrella",
    "Alessandro Marrella",
    "hello@alessandromarrella.com",
    url("https://www.alessandromarrella.com/"))
)

lazy val fs2Version = "1.1.0-M1"
lazy val elasticVersion = "6.4.2"

lazy val root = (project in file("."))
  .settings(globalSettings)
  .settings(
  libraryDependencies ++= Seq(
    "co.fs2" %% "fs2-core" % fs2Version,
    "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % elasticVersion
  )
)

import ReleaseTransformations._

releaseCrossBuild := true 
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)