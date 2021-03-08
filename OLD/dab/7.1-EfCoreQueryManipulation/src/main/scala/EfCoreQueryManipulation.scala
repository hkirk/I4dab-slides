
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

object EfCoreQueryManipulation {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("EF Core"),
      <.br,
      <.h4("Query and Manipulations"),
      <.img(VdomAttr("data-src") := "./img/queries.png", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Query"),
        Item.stable("Loading strategies"),
        Enumeration(
          Item.stable("Explicit"),
          Item.stable("Eager"),
          Item.stable("Multiple leves (join)"),
          Item.stable("Lazy"),
          Item.stable("Select Loading"),
        ),
        Item.stable("Tracing"),
        Item.stable("Create / Update"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlide("Querying",
      Enumeration(
        Item.stable("Access via DbContext"),
      ),

      // TODO arrows to 
      // 1 DbContext property accces
      // 2 A series of LINQ and/or EF core commands
      // 3 An execute command
      cSharp("_context.Books.Where(b => b.Title.StartsWith(\"Database\").ToList();"),
      <.span("vs"),
      cSharp("""from b in _context.Books
               |  where b.Title == "Database"
               |  select b;""".stripMargin),
      <.b("Note"), <.span(": Requires Linq and EntityFrameworkCore imports in C#"),
      notes(
        "1 DbContext property accces",
        "2 A series of LINQ and/or EF core commands",
        "3 An execute command",
      )
    ),

    headerSlide("Execute commands",
      Enumeration(
        Item.stable(".ToList()"),
        Item.stable(".ToArray()"),
        Item.stable(".Count()"),
        Item.stable("..."),
      ),
    ),

    headerSlide("Async execution",
      Enumeration(
        Item.stable("Ends with Async()"),
        Enumeration(
          Item.stable("E.g. .ToListAsync()"),
        ),
        Item.stable("Exists in EntityFrameworkCore namespace - remember use import"),
        Item.stable("Returns a Task<??>"),
        Item.stable("Use async and await"),
        Item.stable("Can not execute queries in parallel")
      )    
    )
  )

  val chapter3 = chapter(
    headerSlide("Loading Strategies - Explicit",
      cSharp("""public class AClass {
                |  public async Task<IEnumerable<Book>> LoadExplicit() {
                |    var books = await _context.Books.ToListAsync();
                |    foreach(var book in books) {
                |      await _context.Entry(book).Reference(b => b.Author).LoadAsync();
                |    }
                | 
                |    return books;
                |  }
                |}""".stripMargin),
      Enumeration(
        Item.stable("+ Load relationship when needed"),
        Item.stable("% More database round-trips"),
        Item.stable("Can be used when library only returns primary entity"),
        Item.stable("Data only used in some circumstances, so we only load needed data"),
      ),
    ),

    headerSlide("Loading Strategies - Eager",
      cSharp("""public class AClass {
                |  public IEnumerable<Book> LoadEager() {
                |    var books = _context.Books
                |      .Include(b => b.Author)
                |      .Include(b => b.Review)
                |      .ToList();
                |     
                |    return books;
                |  }
                |}""".stripMargin),
      Enumeration(
        Item.stable("+ Loaded by EF Core efficiently with a minimum of round-trips"),
        Item.stable("% Load all data, even when not needed"),
        Item.stable("If relationship does not exists, EF does not fail"),
        Item.stable("Since 3.0 this uses JOIN extensively - Be AWARE"),
      ),
    ),

    headerSlide("Loading Strategies - multiple levels",
      cSharp("""public class AClass {
                |  public IEnumerable<Book> LoadMultipleLevels() {
                |    var books = _context.Books
                |      .Include(b => b.Author)
                |      .Include(b => b.Review)
                |        .ThenInclude(r => r.Voter)
                |      .ToList(); 
                |    return books;
                |  }
                |}""".stripMargin),
      Enumeration(
        Item.stable("ThenInclude can be chained"),
      ),
    ),

    headerSlideLeftAligned("Loading Strategies - Select",
      cSharp("""public class AClass {
                |  public object LoadSelect() {
                |    return _context.Books
                |      .Select(b => new {
                |        b.Title,
                |        b.Isbn,
                |        NumReview = b.Reviews.Count
                |      });
                |  }
                |}""".stripMargin),
      <.span("Use LINQ to create anonymous objects with specific data"),
      Enumeration(
        Item.stable("+Load specifically the data needed, including database calculations"),
        Item.stable("-Have to write each query by hand"),
      ), <.br,
      <.b("Note"), <.span(": Includes are ignored when returning instances which are not an entity type")
    ),


    headerSlideLeftAligned("Loading Strategies - Lazy",
      <.span("Install NugetPackage 'Microsoft.EntityFrameworkCore.Proxies'"), <.br,
      <.span("Enable proxies in DbContext"),
      cSharp("""public class Context : DbContext {
                |  protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
                |    => optionsBuilder
                |        .UseLazyLoadingProxies()
                |        .UseSqlServer(myConnectionString); 
                |}""".stripMargin),
      <.span("This enables lazy loading of navigational properties that can be overridden. "), <.b("Note"), <.span(": requires that all navigational properties are declared virtual"),
      cSharp("""public class Author {
                |    ...
                |    public virtual List<Book> Books {get; set;}
                |}""".stripMargin),
      <.span("Or by injecting LazyLoader into service"),
      cSharp("public BookServices(ILazyLoader layzyLoader)"),
    ),

  )

  val chapter4 = chapter(
    headerSlide("Tracking",
      <.span("To track"),
      cSharp("_context.Books.ToList()"),
      <.span("or to NoTrack"),
      cSharp("_context.Books.AsNoTracking().ToList()"),

      Enumeration(
        Item.stable("Better performance in readonly scenarios"),
      ),
    ),

    headerSlide("Changing",
      <.span("Without AsNoTracking - data can be changed:"),
      cSharp("""var book = _context.Books.Single(p => p.Title == "Database Systems");
              |book.Isbn = "12341234";
              |_context.SaveChanges();""".stripMargin),
      <.span("When SaveChanges is run, EF Core method DetectChanges which compares snapshot with application copy"),
      // TODO example / graphic
    )
  )

  val chapter5 = chapter(
    headerSlide("Create",
      cSharp("""public class AClass {
        |  public void Create() {
        |    var book = new Book {
        |      Isbn = "1234",
        |      Title = "Functional Programming in Scala",
        |      Author = paulChiusano
        |   };
        |  _context.Add(book); // or _context.Books.Add(book);
        |  _context.SaveChanges();
        |  }
        |}""".stripMargin),
      Enumeration(
        Item.stable("EF Core expects primary key with SQL IDENTITY."),
        Item.stable("Primary keys which are eg. GUID should be created with ValueGenerator"),
      ),
    ),

    headerSlide("Update",
      cSharp("""public class AClass {
        |  public void Update() {
        |    var book = _context.Books.Single(p => p.Title == "Database Systems");
        |    book.Isbn = "12341234";
        |    _context.SaveChanges();
        |  }
        |}""".stripMargin),
    ),

    headerSlide("Delete",
      cSharp("""public class AClass {
        |  public void Delete() {
        |    var book = _context.Books.First();
        |    _context.Remove(book); // or _context.Books.Remove(book);
        |    _context.SaveChanges();
        |  }
        |}""".stripMargin),
    ),

    headerSlide("Manipulating content",
      Enumeration(
        Item.stable("Multiple save/delete/update statements can be made in a single SaveChanges()"),
        Item.stable("SaveChanges vs SaveChangesAsync"),
      ),
    )
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

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/make-homework-fun.jpg", VdomStyle("height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage meme: https://www.thereformedprogrammer.net/building-efficient-database-queries-using-entity-framework-core-and-automapper/"),
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