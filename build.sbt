
val reactV = "16.2.0"

lazy val common = Seq(
  version      := "-",
  libraryDependencies ++= Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % "1.2.3",
    "org.scala-js" %%% "scalajs-dom" % "0.9.2"
  ),
  jsDependencies ++= Seq(
    "org.webjars.bower" % "react" % "15.2.1" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
    "org.webjars.bower" % "react" % "15.2.1" / "react-dom.js"         minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"
  ),

  scalaJSUseMainModuleInitializer := true,
  
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings"),
)


lazy val root = project
  .in(file("."))
  .aggregate(
    dabIntro,
    dabModeling,
    dabModelingPrinciples,
    shared
  )

lazy val shared = project
  .in(file("shared"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)

lazy val dabIntro = project
  .in(file("dab/1.1-intro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabModeling = project
  .in(file("dab/1.2-modeling"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabModelingPrinciples = project
  .in(file("dab/2.1-modeling"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)