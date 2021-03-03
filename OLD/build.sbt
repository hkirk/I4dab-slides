

val reactV = "16.2.0"

lazy val common = Seq(
  version      := "-",
  libraryDependencies ++= Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % "1.7.7",
    "org.scala-js" %%% "scalajs-dom" % "1.1.0"
  ),

  scalaJSUseMainModuleInitializer := true,
  
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings"),
)

val commonJsBackendSettings = JSDependenciesPlugin.projectSettings ++ List(
  jsDependencies ++= Seq(
    "org.webjars.npm" % "react" % "16.13.1" / "umd/react.development.js" minified "umd/react.production.min.js" commonJSName "React", 
    "org.webjars.npm" % "react-dom" % "16.13.1" / "umd/react-dom.development.js" minified  "umd/react-dom.production.min.js" dependsOn "umd/react.development.js" commonJSName "ReactDOM",
    "org.webjars.npm" % "react-dom" % "16.13.1" / "umd/react-dom-server.browser.development.js" minified  "umd/react-dom-server.browser.production.min.js" dependsOn "umd/react-dom.development.js" commonJSName "ReactDOMServer"
  ),
)


lazy val root = project
  .in(file("."))
  .enablePlugins(JSDependenciesPlugin)
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
  .enablePlugins(JSDependenciesPlugin)
  .settings(common ++ commonJsBackendSettings)

lazy val dabIntro = project
  .in(file("dab/1.1-intro"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared)
  .settings(common ++ commonJsBackendSettings)

lazy val dabModeling = project
  .in(file("dab/1.2-modeling"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabModelingPrinciples = project
  .in(file("dab/2.1-modeling"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabDdl = project
  .in(file("dab/2.2-ddl"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val sqlvsnosql = project
  .in(file("dab/SqlVsNoSql"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabSqlConnection = project
  .in(file("dab/3.1-sql-connection"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabdml = project
  .in(file("dab/3.2-dml"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabDmlAdvanced = project
  .in(file("dab/4.1-dml-advanced"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabViewsJoins = project
  .in(file("dab/4.2-sql-view-joins"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabNormalization = project
  .in(file("dab/5.1-Normalization"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabEfCoreIntro = project
  .in(file("dab/5.2-EfCoreIntro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabEfCoreModels = project
  .in(file("dab/6.1-EfCoreModelsRelationships"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabEfCoreMigrations = project
  .in(file("dab/6.2-EfCoreMigrations"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabEfCoreQueryManipulation = project
  .in(file("dab/7.1-EfCoreQueryManipulation"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)


lazy val dabEfCoreAdvanced = project
  .in(file("dab/7.2-EfCoreAdvanced"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabEfCorePatternsDDD = project
  .in(file("dab/8.1-EfCorePatternsDDD"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabScripting = project
  .in(file("dab/9.1-Scripting"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabNoSqlIntro = project
  .in(file("dab/11.1-NoSqlIntro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)

lazy val dabNoSqlMongo = project
  .in(file("dab/11.2-NoSqlMongoDb"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common ++ commonJsBackendSettings)
  .dependsOn(shared)
