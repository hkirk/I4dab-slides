
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object EfCoreMigrations {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("EF Core"),
      <.br,
      <.h4("Migrations"),
      <.img(VdomAttr("data-src") := "./img/winnie.png", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Connection to Server"),
        Item.stable("Thw why, what, where"),
        Item.stable("First migration"),
        Item.stable("Update database"),
        Item.stable("Rolling back migrations"),
      ),
    ),
  )

  val dbVsOo = <.table(VdomStyle("fontSize") := "22px",
    <.thead(
      <.tr(
        <.th("Relational database"),
        <.th("Object Oriented langauge"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Table"),
        <.td("Class"),
      ),
      <.tr(
        <.td("Column"),
        <.td("Property"),
      ),
      <.tr(
        <.td("Rows"),
        <.td("Collection of objects"),
      ),
      <.tr(
        <.td("Unique row"),
        <.td("Object"),
      ),
      <.tr(
        <.td("Foreign key"),
        <.td("Reference"),
      ),
      <.tr(
        <.td("SQL - e.q. WHERE"),
        <.td(".NET LINQ - e.g. WHERE(...)"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlideWithColumns("SQLite vs SQL Server")
    (
      Enumeration(
        Item.stable("SQLite is very lightweight"),
        Enumeration(
          Item.stable("Can’t remove columns only add"),
          Item.stable("Mostly used for development / tests"),
        ),
        Item.stable("SQL Server"),
        Enumeration(
          Item.stable("Full weight database"),
          Item.stable("Connections string are non-trivial"),
          Item.stable("→ So from now and onwards we will use this"),
        ),
      ),
    )(
      fadeInFragment(
        <.img(VdomAttr("data-src") := "./img/connection-string.png", VdomStyle("height") := "600px"),
      ),
    ),

    headerSlide("Connection string SQL Server",
      Enumeration(
        Item.stable("'Server Explorer' window in Visual Studio (Menu -> View -> Server Explorer)"),
        Enumeration(
          Item.stable("Connect to database"),
          Item.stable("From properties menu"),
        ),
        Item.stable("Other ways to create connection string"),
        Enumeration(
          Item.stable(<.a(^.href := "https://www.connectionstrings.com/sql-server-2016/", "https://www.connectionstrings.com/sql-server-2016/")),
        ),
        Item.stable(<.b("Note"), <.span(": To use SQL Server, install "), <.u("correct"), <.span(" nuget package and use database connection in DbContext.OnConfiguring(..)")),
      ),
    ),

    headerSlide("Video video video",
      <.iframe(
        ^.width := "560px", ^.height := "315px", ^.src := "https://www.youtube.com/embed/1U0cP2rvr2g",
        VdomAttr("frameborder") := "0px", VdomAttr("allow") := "accelerometer; encrypted-media; gyroscope; picture-in-picture"
      ),
      <.a(^.href := "https://www.youtube.com/watch?v=1U0cP2rvr2g", "https://www.youtube.com/watch?v=1U0cP2rvr2g"),
    ),
  )

  val chapter3 = chapter(
    headerSlide("Why migrations",
      Enumeration(
        Item.stable("It's not a feasible to delete database every time we make a change"),
        Enumeration(
          Item.stable("Keeping Development / Production envirioment in sync"),
        ),
        Item.stable("Avoid making changes by hand"),
      ),
    ),

    headerSlideLeftAligned("What are migrations", // TODO Is this the what?
      <.h3("Benefist"),
      Enumeration(
        Item.stable("EF Core generates your Migrations files based on your models"),
        Item.stable("Gives you a tool to keep model and database in sync"),
        Item.stable("Edit database schema without losing data (development and production)"),
        Item.stable("Provide a way to make rollbacks (some as in VCS)"),
        Item.stable("Version control for database"),
      ),
      <.h3("Drawback"),
      Enumeration(
        Item.stable("Harder to make merges in larger teams, since migrations files should be handled especially carefully"),
      ),
    ),

    headerSlide("Where do migrations live (1/2)",
      <.span("Lives in: <project-folder>/Migrations/"), <.br,
      <.span("Migration file:"),
      cSharp("""public partial class AddContactPhoneNumber : Migration
              |{
              |    protected override void Up(MigrationBuilder migrationBuilder)
              |    {
              |        migrationBuilder.AddColumn<string>(
              |            name: "PhoneNumber",
              |            table: "Contacts",
              |            nullable: true);
              |    }
              |
              |    protected override void Down(MigrationBuilder migrationBuilder)
              |    {
              |        migrationBuilder.DropColumn(
              |            name: "PhoneNumber",
              |            table: "Contacts");
              |    }
              |}""".stripMargin),
    ),

    headerSlide("Where do migrations live (2/2)",
      <.span("<ContextClassName>ModelSnapshot.cs:"),
      cSharp("""protected override void BuildModel(ModelBuilder modelBuilder) {
                |  #pragma warning disable 612, 618
                |  modelBuilder
                |      .HasAnnotation("ProductVersion", "2.2.0-rtm-35687");
                |  modelBuilder.Entity("MyFirstEfCoreApp.Models.Contact", b =>
                |      {
                |          b.Property<int>("Id")
                |              .ValueGeneratedOnAdd();
                |          b.Property<string>("Email");
                |          b.Property<string>("FirstName");
                |          b.Property<string>("LastName");
                |          b.Property<string>("PhoneNumber");
                |          b.HasKey("Id");
                |          b.ToTable("Contacts");
                |      });
                | ….
                |}""".stripMargin),
    ),
  )

  val chapter4 = chapter(
    // TODO insert slide about overview 
    headerSlide("Create migration",
      Enumeration(
        Item.stable("In Visual Studio"),
        Enumeration(
          Item.stable("(Tools -> Nuget Package Manager -> Package Manager Console)"),
          Item.stable("PM> Add-Migration InitialMigration"),
        ),
        Item.stable("EF Core cli"),
        Enumeration(
          Item.stable("$ dotnet ef migrations add InitialMigration"),
        ),
        Item.stable(<.b("Note"), <.span(": InitialMigration is the name of the migrations")),
        Item.stable("Creates a CS file with timestamp and name of migration + plus creates/updates Snapshot.cs file in Migrations folder."),
      ),
    ),

    headerSlide("Update database",
      Enumeration(
        Item.stable("In Visual Studio"),
        Enumeration(
          Item.stable("(Tools -> Nuget Package Manager -> Package Manager Console)"),
          Item.stable("PM> Update-Database"),
        ),
        Item.stable("EF Core cli"),
        Enumeration(
          Item.stable("$ dotnet ef database update"),
        ),
        Item.stable(<.span("After this the migrations is "), <.b("applied"))
      ),
    ),
  )

  val chapter5 = chapter(
    headerSlide("DEMO",
      <.img(VdomAttr("data-src") := "./img/demo.jpeg", VdomStyle("height") := "600px"),
    ),
  )

  val chapter6 = chapter(
    headerSlide("Rollback migrations (undo) - in unapplied state",
      Enumeration(
        Item.stable("In Visual Studio"),
        Enumeration(
          Item.stable("(Tools -> Nuget Package Manager -> Package Manager Console)"),
          Item.stable("PM> Remove-Migration <MigrationsNames(s)>"),
        ),
        Item.stable("EF Core cli"),
        Enumeration(
          Item.stable("$ dotnet ef migrations remove <MigrationName(s)>"),
        ),
      ),
    ),

    headerSlide("Rollback migrations (undo) - in applied state",
      Enumeration(
        Item.stable("In Visual Studio"),
        Enumeration(
          Item.stable("(Tools -> Nuget Package Manager -> Package Manager Console)"),
          Item.stable("PM> Update-Database <MigrationName-1>"),
          Item.stable("PM> Remove-Migration <MigrationName>"),
        ),
        Item.stable("EF Core cli"),
        Enumeration(
          Item.stable("$ dotnet ef database update <MigrationName-1>"),
          Item.stable("$ dotnet ef migrations remove <MigrationName>"),
        ),
      ),
    ),

    headerSlide("What happens in roolback",
        //TODO
    ),  
  )


  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/make-homework-fun.jpg", VdomStyle("height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage meme: http://www.developermemes.com/2013/03/27/sql-timing-out-so-raise-timeout/"),
      <.br,
      <.span("Join: https://www.cleanpng.com/png-join-microsoft-sql-server-table-oracle-database-6091970/"),
      <.br,
      <.span("Exercise gif: https://parentsneed.com/7-ways-to-help-make-homework-fun/"),
      <.br, 
    ),



    
  )


  val Talk = ScalaComponent
    .builder[Unit]("Presentation")
    .renderStatic(
      <.div(
        ^.cls := "reveal",
        <.div(
          ^.cls := "slides",
          chapter1,
          chapter2,
          chapter3,
          chapter4,
          chapter5,
          chapter6,
          // chapter7,
          // chapter8,
          chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}