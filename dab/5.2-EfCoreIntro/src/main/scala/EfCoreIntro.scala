
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object EfCoreIntro {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("Entity Framework Core"),
      <.br,
      <.h4("Introduction"),
      <.img(VdomAttr("data-src") := "./img/learn_efcore.jpeg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Object Relational Mapper"),
        Item.stable("Starting a project"),
        Item.stable("Database context"),
        Item.stable("Creating database"),
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
    headerSlideWithColumns("What is Object relational mapper")
    (
      Enumeration(
        Item.stable(<.span("Object Relational Mapper = O/RM")),
        Enumeration(
          Item.stable("object - plain OOP objects"),
          Item.stable("relational - Relational database"),
          Item.stable("mapper - bridge between"),
        ),
      ),
    )(
      fadeInFragment(dbVsOo),
    ),

    headerSlide("Why use O/RM",
      Enumeration(
        Item.stable("Avoid writing all database queries in hand. This work is tedious and error prone"),
        Item.stable("Can generate a database scheme from you OOP model"),
        Item.stable("or generate OOP model from database"),
        Item.stable("Security"),
        Item.stable("Avoid SQL inside code (e.g. C#)")
      ),
    ),

    headerSlide("Downsides",
      Enumeration(
        Item.stable("Different paradigms in OOP and Relational database"),
        Item.stable("Pollution of OOP classes with annotations etc."),
        Item.stable("‘Forget’ that data is saved in database, meaning you write code that works in test, but not in production"),
      ),
    ),
  )

  val chapter3 = chapter(
    headerSlideLeftAligned("Entity Framework Core",
      Enumeration(
        Item.stable("Is an O/RM"),
        Item.stable("Open source"),
        Item.stable("Cross platform"),
        Item.stable("Build to support NoSQL"),
      ),
      fadeInFragment(
        <.div(
          <.b("Note"), <.span(": EF6/7 is not build to support NoSQL but is extended to do so")
        ),
      ),
    ),
  )

  val chapter4 = chapter(
    headerSlide("EF Core Guide",
      <.span("From here its more or less a getting started tutorial"),<.br,
      fadeInFragment(
        <.img(VdomAttr("data-src") := "./img/party.jpeg", VdomStyle("height") := "400px"),
      )
    ),

    headerSlideWithColumns("Creating project (`) (1/2)")
    (
      <.div(VdomStyle("lineHeight") := "40px",
        <.span("First time: dotnet tool install --global dotnet-ef --version <3.1.1>"), <.br,
        OrderedList(
          Item.stable(
            <.span("Creating a .Net 2.0 Core console Project for Sqlite"), <.br,
            <.i(VdomStyle("fontSize") := "22px", "$ mkdir MyFirstEFCoreProject"), <.br,
            <.i(VdomStyle("fontSize") := "22px", "$ cd MyFirstEFCoreProject"), <.br,
            <.i(VdomStyle("fontSize") := "22px", "$ dotnet new console"), <.br,
          ),
          fadeInFragment(
            Item.stable(
              <.span("Install Entity Framework Core"), <.br,
              <.i(VdomStyle("fontSize") := "22px", "$ dotnet add package Microsoft.EntityFrameworkCore.Sqlite"), <.br,
              <.i(VdomStyle("fontSize") := "22px", "$ dotnet add package Microsoft.EntityFrameworkCore.Design"), <.br,
              // TODO One more tools maybe
            ),
          ),
          fadeInFragment(
            Item.stable(
              <.span("Adding a connection string (in .cs file)"), <.br,
              <.i(VdomStyle("fontSize") := "22px", "In class Context inherit from DbContext and add the following code"), <.br,
              
            ),
          ),
        ),
      ),
    )(
      fadeInFragment(
        <.div(
          cSharp("""// Class you make
                  |public class Context : DbContext {
                  | protected override void
                  |      OnConfiguring(
                  |        DbContextOptionsBuilder ob) {
                  |  // For SQLite file, this is 
                  |  ob.UseSqlite("Data Source=d.db");
                  |}}""".stripMargin),
        )
      ),
    ),

    headerSlide("Creating project (VS studio)",
      OrderedList(
        Item.stable(
          <.span("Creating a .Net 2.0 Core console Project for Sqlite"), <.br,
          <.i(VdomStyle("fontSize") := "22px", "Create new .NET Core - Console App project in UI")
        ),
        Item.stable(
          <.span("Install Entity Framework Core"),
          <.div(VdomStyle("fontSize") := "22px",
            <.i("From the Visual Studio menu, select Project > Manage NuGet Packages"), <.br,
            <.i("Select Microsoft.EntityFrameworkCore.Sqlite"), <.b(" and "), <.i("Microsoft.EntityFrameworkCore.Design"), <.b(" and "), <.i("Microsoft.EntityFrameworkCore.Tools packages"),
          )
        ),
        Item.stable(
          <.span("Adding a connection string (in .cs file)"), <.br,
          <.i(VdomStyle("fontSize") := "22px", "In class Context inherit from DbContext and add the following code - see previous slideg")
          // cSharp("""protected override void OnConfiguring(
          //          |            DbContextOptionsBuilder optionsBuilder) {
          //          | optionsBuilder.UseSqlite("Data Source=door.db");
          //          |}""".stripMargin),
        ),
        Item.stable(
          <.span("Setup working directory"), <.br,
          <.a(VdomStyle("fontSize") := "22px", ^.href := "https://docs.microsoft.com/en-us/ef/core/get-started/netcore/new-db-sqlite#run-from-visual-studio", "https://docs.microsoft.com/en-us/ef/core/get-started/netcore/new-db-sqlite#run-from-visual-studio"),
        ),
      )
    ),

    headerSlideLeftAligned("Database context",
      Enumeration(
        Item.stable("A class that inherits from EF Cores DbContext"),
        Item.stable("Contain information that EF Core needs to configure database mappings"),
        Item.stable("Class you use to access database"),
      ), <.br,

      <.span("Connection to database is created through:"), <.br,
      OrderedList(
        Item.stable("Override method OnConfiguring and supply connection string"),
        Item.stable("Add optionsBuilder.UseSqlite(ConnectionString);"),
      )
    ),

    headerSlideLeftAligned("Creating model classes")
    (
      Enumeration(
        Item.stable("Create class Door"),
        Item.stable("Add property with public getter and setter"),
        Item.stable("Primary key is by convention named Id or <class name>Id (case insensitive)"),
      ),

      cSharp("""public class Door
               |{
               |  public int DoorId {get;set;}
               |  public string Location {get;set;}
               |  public string Type {get;set;}
               |}""".stripMargin),
      Enumeration(
        Item.stable("Add DbSet to AppDbContext"),
      ),
        
      cSharp("public DbSet<Door> doors { get; set; }"),
    ),

    headerSlide("Create database database (CLI)",
      OrderedList(
        Item.stable(
          <.span("Doing code first - you can make Entity Framework create you database"), <.br,
          <.i("$ dotnet ef migrations add InitialMigration"), <.br,
          <.i("$ dotnet ef database update"), <.br,
        ),
        Item.stable(
          <.span("If creating an error - undo"), <.br,
          <.i("Delete door.db file"), <.br,
          <.i("$ dotnet ef migrations remove"), <.br,
          <.i("Make changes in code"), <.br,
          <.i("Goto 1)"), <.br,
        ),
      ),
    ),

    headerSlide("Create database (Visual studio)",
      OrderedList(
        Item.stable(
          <.span("Doing code first - you can make Entity Framework create you database. In VS - Open PowerShell (Tools -> Manage Nuget -> Package Manager Console"), <.br,
          <.i("> Install-Package Microsoft.EntityFrameworkCore.Tools (first time)"), <.br,
          <.i("> Add-Migration InitialCreate"), <.br,
          <.i("> Update-Database"), <.br,
        ),
        Item.stable(
          <.span("If creating an error - undo"), <.br,
          <.i("Delete door.db file"), <.br,
          <.i("> Remove-Migration (in Package manager console)"), <.br,
          <.i("Make changes in code"), <.br,
          <.i("Goto 1)"), <.br,
        ),
      ),
    ),

    headerSlide("DbContext in details",
      OrderedList(
        Item.stable("Looks at all DbSet properties"),
        Item.stable("Looks at properties in classes"),
        Item.stable("Looks at linked clases"),
        Item.stable(
          <.span("Runs OnModelCreating"), <.br,
          <.span("-> results in database schema (which can be found in Migrations/AppDbContextModelSnapshot.cs)"), <.br,
        ),
      ),
    ),

    headerSlideWithColumns("Read data")
    (
      <.span("Read data from EF Core"), <.br,
      <.b("In C#"), <.br,
      cSharp("""db.Books.AsNoTracking()
             |    .Include(a => a.Author)""".stripMargin),
      fadeInFragment(
        <.span("Is translated into:"), <.br,
        sql("""SELECT b.BookId, …, a.AuthorId, … 
              |FROM Books AS B
              |INNER JOIN Author AS a
              |  ON b.AuthorId = a.AuthorId""".stripMargin),
      )
    )(
      fadeInFragment(
        OrderedList(
          Item.stable("LINQ is translated into SQL* and cached"),
          Item.stable("Data is read in one command"),
          Item.stable("Data is turned into instances of .NET classes"),
          Item.stable("No tracking snapshot is created in this instance"),
        ),
      ),
    ),

    headerSlideWithColumns("Update data")
    (
      <.span("Update data via EF Core"), <.br,
      <.b("In C#"), <.br,
      cSharp("""var book = db.Books....;
               |book.Author.WebUrl = 'newUrl';
               |db.SaveChanges()""".stripMargin),
      fadeInFragment(
        <.span("Is translated into:"), <.br,
        sql("""UPDATE Authors SET WebUrl = ‘newUrl’
              |WHERE AuthorId = '...'""".stripMargin),
      )
    )(
      fadeInFragment(
        OrderedList(
          Item.stable("LINQ is translated into SQL*."),
          Item.stable("Tracking snapshopts are created - holding original values"),
          Item.stable("DetectChanges stages works out what has changed"),
          Item.stable("Transaction is started - all or nothing is saved"),
          Item.stable("SQL command is run"),
        ),
      ),
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
          // chapter5,
          // chapter6,
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