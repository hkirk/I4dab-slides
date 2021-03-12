
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

object EfCoreAdvanced {
  import Enumeration._

  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("EfCore"),
      <.br,
      <.h4("Advanced"),
      <.img(VdomAttr("data-src") := "./img/jedi.jpeg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Existing database"),
        Item.stable("Transactions"),
        Item.stable("Disconnected Entities"),
        Item.stable("Development"),
        Enumeration(
          Item.stable("Data seeding"),
          Item.stable("Testing"),
        ),
      ),
    ),
  )

  val firstMovieTable = <.table(
    <.thead(
      <.tr(
        <.th("title"),
        <.th("year"),
        <.th("length"),
        <.th("genre"),
        <.th("studio"),
        <.th("starName"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st??
        <.td("Fox"),
        <.td("Carrie Fisher"),
      ),
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st
        <.td("Fox"),
        <.td("Mark Hamil"),
      ),
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st
        <.td("Fox"),
        <.td("Harrison Ford"),
      ),
      <.tr(
        <.td("Gone with the Wind"),
        <.td("1939"),
        <.td("231"),
        <.td("drama"),
        <.td("MGM"),
        <.td("Vivien Leigh"),
      ),
      <.tr(
        <.td("Wayne's World"),
        <.td("1992"),
        <.td("95"),
        <.td("comedy"),
        <.td("Paramount"),
        <.td("Dana Carvey"),
      ),
      <.tr(
        <.td("Wayne's World"),
        <.td("1992"),
        <.td("95"),
        <.td("comedy"),
        <.td("Paramount"),
        <.td("Mike Meyers"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlide("Existing database",
      Enumeration(
        Item.stable("Reverse engineering a database through EF Core can be done with Scaffold tool"),
        Enumeration(
          Item.stable(
            <.span("Visual Studio"), <.br,
            <.span(VdomStyle("fontSize") := "22px", "> Scaffold-DbContext 'Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=Chinook' Microsoft.EntityFrameworkCore.SqlServer"),
          ),
          Item.stable(
            <.span(".Net Core CLI"), <.br,
            <.span(VdomStyle("fontSize") := "22px", "$ dotnet ef dbcontext scaffold \"Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=Chinook\" Microsoft.EntityFrameworkCore.SqlServer"),
          ),
        ),
        Item.stable(<.b("Note"), <.span(": Connection string needs to point to the database you want to scaffold"))
      ),
    ),

    headerSlideLeftAligned("Scaffold options",
      Enumeration(
        Item.stable("Skip tables with ‘-Tables’ / ‘--tables’"),
        Item.stable("Preserving names from database ‘-UseDatabaseNames’ / ‘--use-database-names’"),
        Item.stable("Fluent API is used by default to change to annotations ‘-DataAnnotations / ‘--data-annotations’"),
        Item.stable("More configuration to be found in documentations"),
      ),
      <.br, <.b("BUT"), <.br,
      Enumeration(
        Item.stable("Does not work on"),
        Enumeration(
          Item.stable("Inheritance"),
          Item.stable("Columns types not supported with EF Core"),
          Item.stable("Tables without primary key"),
        ),
      ),
    ),

    headerSlideLeftAligned("Afterwards",
      Enumeration(
        Item.stable("The model can be changed afterwards (with migrations) -> So inheritance etc. can be created manually afterwards"),
        Item.stable("Default is to configure database via Fluent API"),
        Enumeration(
          Item.stable("-DataAnnotations - makes annotations"),
        ),
        Item.stable("Model is created as partial classes - so you can add extra validation"),
      ),

      cSharp("""[ModelMetadataType(typeof(UserValidation))]
                |public partial class User {
                |  public string Email { get; set; }
                |}
                |
                |private class UserValidation {
                |  [EmailAddress]
                |  public string Email { get; set; }       
                |}""".stripMargin),
    ),
    )

  val chapter3 = chapter(
    headerSlideLeftAligned("Transactions",
      Enumeration(
        Item.stable("All changes to be saved with SaveChanges() are applied in a single transaction"),
      ),
      // TODO to much code
      cSharp("""public class AClass {
                | public void AMethod() {
                |  using (var context = new MyDbContext()) {
                |   using (var transaction = context.Database.BeginTransaction()) {
                |     try {
                |       context.Books.Add(new Book { Title = "First Book" });
                |       context.SaveChanges();
                |       // .. Network call which Depends on First Book in DB and Second Book not
                |       context.Books.Add(new Book { Title = "Second Book" });
                |       context.SaveChanges();
                | 
                |       var books = context.Books.OrderBy(b => b.Title).ToList();
                |       // Commit transaction if all commands succeed, transaction will auto-rollback
                |       // when disposed if either commands fails
                |       transaction.Commit();
                |     }
                |     catch (Exception) { // TODO: Handle failure
                |} } } } }""".stripMargin),
    ),

    headerSlide("When to use Transactions",
      Enumeration(
        Item.stable(
          <.span("Business logic gets complex"), <.br,
          <.span("Solution:"),
        ),
        Enumeration(
          Item.stable(
            <.span("One big methods with all the logic"), <.br,
            <.span("Problem: Obvious")
          ),
          Item.stable(
            <.span("Smaller methods which are call from overreaching method"), <.br,
            <.span("Problem: If later parts relies on earlier parts being written, forget to call SaveChanges"),
          ),
          Item.stable("Smaller methods and use transaction to run them as one"),
        ),
        Item.stable("Be aware that transactions locks tables for writes (or reads in some cases) - so use with care"),
      ),
    ),
  )

  val chapter4 = chapter(
    // TODO what are disconnected entities
    headerSlide("Disconnected Entities",
      Enumeration(
        Item.stable("Scenario: Changes are made in a different database context instance"), // Maybe move to slides that describes problem
        Item.stable("Determine if the entity exists in DB or not."),
        Enumeration(
          Item.stable(<.b("Auto-keys"), <.span(": use context.Update(entity) // In Core 2.0+")),
          Item.stable(<.b("else"), <.span(": use context.Find(entity.Id) == null")),
          Enumeration(
            Item.stable("Use context.Update(entity)"),
            Item.stable("Or context.Add(newEntity)"),
          ),
        ),
        Item.stable("Same for Graphs"),
        Item.stable("Handling deletes"),
        Enumeration(
          Item.stable("Harder since objects don’t exists, so need check which don’t exists in incoming"),
          Item.stable("Can be handled with ‘soft-deletes’"), // TODO Soft deletes?
        ),
      ),
    ),
  )

  val chapter5 = chapter(
    headerSlide("Development",
      // tODO content"
    ),

    headerSlide("Data seeding",
      Enumeration(
        Item.stable("From EF Core 2.1 the HasData method is added - part of OnModelCreating"),
        Item.stable("Migrations is created without connection to DB - have to specify ID manually."),
        Item.stable("Data is removed if Primary key is changed"),
      ),
      // TODO to much code
      // TODO correct method / class
      cSharp("""public class AClass {
                | public void AMethod() {
                |   modelBuilder.Entity<Book>().HasData(new Book() { Title = "A title", Isbn = 1 });
                |   modelBuilder.Entity<PriceOffer>().HasData(new PriceOffer{ NewPrice = 1.1f, Isbn = 2 });
                |   // Not working !
                |   // modelBuilder.Entity<Book>().OwnsMany(b => b.Reviews).HasData(
                |   //     new Review() { Id = 1, BookIsbn = 1, Votername = "V1", NumStars = 1},
                |   //     new Review() { Id = 2, BookIsbn = 2, Votername = "V2", NumStars = 2}
                |   // );
                |   modelBuilder.Entity<Review>().HasData(
                |   new Review() { Id = 2, BookIsbn = 2, Votername = "V2", NumStars = 2});
                |   modelBuilder.Entity<Review>().HasData(
                |   new { Id = 1, BookIsbn = 1, Votername = "V1", NumStars = 1});
                |} }""".stripMargin),
      notes(
        "Has limitations",
        "Temporary data, custom transformation (hashing), Access to external API etc"
      ),
            

    ),

    headerSlide("Other solutions",
      Enumeration(
        Item.stable(
          <.span("InsertData(), UpdateData(), DeleteData() on MigraionBuilder"), <.br,
          <.span("See"), <.a(^.href := "https://docs.microsoft.com/en-us/ef/core/managing-schemas/migrations/operations", "Custom Migrations operations")
        ),
        Item.stable("aveChanges()"),
      ),
    ),

    headerSlide("Logging",
      Enumeration(
        Item.stable("LogLevel.Information gives a list of all SQL commands generated by EF Core"),
        Item.stable("Setup log factory:"),
      ),
      cSharp("""public class AClass {
                |  public void Setup() {
                |    var logs = new List<String>();
                |    var loggerFactory = context.GetService<ILoggerFactory>();
                |    loggerFactory.AddProvider(new MyLoggerProvider(logs, LogLevel.Information));""".stripMargin),
    ),

    headerSlide("Performance", 
      Enumeration(
        Item.stable(
          <.span("EF Core alerts to possible suboptimal LINQ commands by logging a warning of type QueryClientEvaluationWarning."), <.br,
          <.span("EF Core can’t translate LINQ expression to SQL") // TODO example
        ),
        Item.stable("Configure EF Core to throw an exception"),
        Item.stable("Analyse SQL queries"),
        Enumeration(
          Item.stable("Use SSML’s execution plan"),
          Item.stable(
            <.span("SQL Console SQL Server/MySQL use explain"), <.br,
            sql("EXPLAIN <sql_statement>"),
          ),
        ),
      ),
    ),

    headerSlide("Keep your application performing",
      Enumeration(
        Item.stable("Use SELECT loading to load only needed data"),
        Item.stable("Use paging/filtering to reduce rows loaded into application from SQL Server"),
        Item.stable("Lazy loading will affect performance"),
        Item.stable("Use AsNoTracking when you load read-only data"),
        Item.stable("Using async versions when possible"),
        Item.stable("Structure code, so database access code is isolated"),
      ),
    ),

    headerSlide("Testing",
      Enumeration(
        Item.stable(
          <.span("Create AddDbContext constructor for testing"), <.br,
          <.span("Allows for all options to come from test methods"),
        ),
        Item.stable("Steps in test method"),
        OrderedList(
          Item.stable("Create connection to in-memory database"),
          Item.stable("Create DbContextOptions"),
          Item.stable("Initialize AppDbContext"),
          Item.stable("Insert test data"),
          Item.stable("Test business logic"),
          Item.stable("Close connections"),
          Item.stable("Cleanup"),
        ),
        Item.stable("SQLite in-memory database or"),
        Item.stable("InMemory for General purpose database"),
      ),
    ),

    headerSlide("Testing example",
      // TODO Show code
      // public BloggingContext(DbContextOptions<BloggingContext> options : base(options) {}

    ),
  )
    
  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/make-homework-fun.jpg", VdomStyle("height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("maxHeight") := "600px"),
    ),

    headerSlide(
      "References",
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