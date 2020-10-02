
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
    dabDdl,
    sqlvsnosql,
    dabSqlConnection,
    dabDmlAdvanced,
    dabViewsJoins,
    dabNormalization,
    dabEfCoreIntro,
    dabEfCoreMigrations,
    dabEfCoreQueryManipulation,
    dabEfCoreAdvanced,
    dabEfCorePatternsDDD,
    dabScripting,
    dabNoSqlIntro,
    dabNoSqlMongo,
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

lazy val dabDdl = project
  .in(file("dab/2.2-ddl"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val sqlvsnosql = project
  .in(file("dab/SqlVsNoSql"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabSqlConnection = project
  .in(file("dab/3.1-sql-connection"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabdml = project
  .in(file("dab/3.2-dml"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabDmlAdvanced = project
  .in(file("dab/4.1-dml-advanced"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabViewsJoins = project
  .in(file("dab/4.2-sql-view-joins"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabNormalization = project
  .in(file("dab/5.1-Normalization"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabEfCoreIntro = project
  .in(file("dab/5.2-EfCoreIntro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabEfCoreModels = project
  .in(file("dab/6.1-EfCoreModelsRelationships"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabEfCoreMigrations = project
  .in(file("dab/6.2-EfCoreMigrations"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabEfCoreQueryManipulation = project
  .in(file("dab/7.1-EfCoreQueryManipulation"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)


lazy val dabEfCoreAdvanced = project
  .in(file("dab/7.2-EfCoreAdvanced"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabEfCorePatternsDDD = project
  .in(file("dab/8.1-EfCorePatternsDDD"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabScripting = project
  .in(file("dab/9.1-Scripting"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabNoSqlIntro = project
  .in(file("dab/11.1-NoSqlIntro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)

lazy val dabNoSqlMongo = project
  .in(file("dab/11.2-NoSqlMongoDb"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .dependsOn(shared)
