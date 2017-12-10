lazy val globalSettings = Seq(
  organization := "com.alessandromarrella",
  name := "fs2-elastic",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.4",
  useGpg := true
)


lazy val fs2Version = "0.10.0-M8"
lazy val elasticVersion = "6.0.1"

lazy val root = (project in file("."))
  .settings(globalSettings)
  .settings(
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-io" % fs2Version,
      "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % elasticVersion
    )
  )

homepage in Global := Some(url("https://github.com/amarrella/fs2-elastic"))
licenses in Global += "MIT" -> url(
  "https://github.com/amarrella/fs2-mongodb/blob/master/LICENSE")
developers in Global += Developer("amarrella",
                                  "Alessandro Marrella",
                                  "hello@alessandromarrella.com",
                                  url("https://www.alessandromarrella.com/"))
scmInfo in Global := Some(
  ScmInfo(
    url("https://github.com/amarrella/fs2-elastic"),
    "scm:git@github.com:amarrella/fs2-elastic.git"
  )
)

publishTo in Global := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

