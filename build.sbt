name := "iris-classifier"
version := "1.0.0"
scalaVersion := "2.11.8"
exportJars := true

lazy val iris = (project in file("."))
    .settings(
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test",
        libraryDependencies ++= Seq(
            "io.confluent.ksql" % "ksql-udf" % "5.3.2",
            "ml.combust.bundle" % "bundle-ml_2.11" % "0.15.0",
            "ml.combust.mleap" % "mleap-runtime_2.11" % "0.15.0",
            "com.jsuereth" % "scala-arm_2.11" % "2.0",
            "org.scala-lang" % "scala-reflect" % "2.11.8",
            "org.scala-lang" % "scala-library" % "2.11.8"
        ),
        resolvers += "confluent" at "https://packages.confluent.io/maven/",
        assemblyMergeStrategy in assembly := {
            case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
            case x =>
                val oldStrategy = (assemblyMergeStrategy in assembly).value
                oldStrategy(x)
        }

    )
    
