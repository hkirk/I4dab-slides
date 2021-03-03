
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object EfCorePatternsDDD {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("Design Patterns"),
      <.br,
      <.h4("ORM system (EfCore)"),
      <.img(VdomAttr("data-src") := "./img/good_code.png", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Introduction"),
        Item.stable("Domain Driven Design"),
        Enumeration(
          Item.stable("Entity"),
          Item.stable("Repository"),
        ),
        Item.stable("UnitOfWork"),
        Item.stable("QueryObject"),
        Item.stable("Object Mappers"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlide("Vocabulary",
      Enumeration(
        Item.stable("CRUD"),
        Enumeration(
          Item.stable("Create, Read, Update, Delete")
        ),
        Item.stable("DTO (Data Transfers Object"),
        Enumeration(
          Item.stable("“An object that transfer data between processes” or “an object that encapsulate data and send it from one subsystem to another”")
        ),
        Item.stable("Extension methods in C#"),
        Enumeration(
          Item.stable(
            <.span("A method can become an extension method if a) is in a static class, b) method is static and c) first parameter has the keyword this in front."),
            <.br,
            <.span("E.g."),
            
          )
        ),
      ),
      cSharp("public static IQueryable<BookListDto> MapBookToDto(this IQueryable<Book> books)")
    ),

    headerSlide("Introduction",
      Enumeration(
        Item.stable("Build on top of others work"),
        Item.stable("Faster development"),
        Item.stable("Less repetitive work"),
        Enumeration(
          Item.stable("Leads to fewer bugs"),
        ),
        Item.stable("Making sure EF Core code isn’t shared all over the application"),
        Item.stable("Separation of Concerns"),
        Enumeration(
          Item.stable("Each layer is responsible for one thing - mental model is easier"),
          Item.stable("Having a isolated DAL it is easier to test"),
        ),
      ),
      // TODO insert image
    ),
  )

  val chapter3 = chapter(
    headerSlideLeftAligned("DDD",
      Enumeration(
        Item.stable("From Domain-Driven Design by Eric Evans"),
        Enumeration(
          Item.stable("About putting Business Domain in the center of Software"),
        ),
      ),
      // TODO make image floating
      <.img(VdomAttr("data-src") := "./img/ddd-book.jpeg", VdomStyle("maxHeight") := "200px"),
      <.br,
      <.span("DDD in short"),
      <.br,
      Enumeration(
        Item.stable("A project consists of one or more bounded context"),
        Item.stable("Within a Bounded context there exists an Ubiquitous Language around an Domain Model"),
        Item.stable("Building Blocks"),
        Enumeration(
          Item.stable(
            <.b("Entity"), <.span(", Value Object, "), <.b("Aggregate"), <.span(", "), <.b("Repository"),
            <.span(", Domain Event, Service, Factory"),
          ),
        ),
      ),
    ),

    headerSlide(
      "DDD continued",
      Enumeration(
        Item.stable("Entity is an object which is not defined by its attributes but by an ID"),
        Item.stable("Aggregates is a collection of entities"),
        Item.stable("Root entities is only way to access entities within an Aggregate"),
        Item.stable("Repository is exposing a set of methods for accessing domain objects (entities)"),
      ),
      <.img(VdomAttr("data-src") := "./img/ddd-diagram-example.png", VdomStyle("maxHeight") := "500px"),
    ),

    fullscreenImageSlide("./img/architecture.png"),

    headerSlideLeftAligned("Repository",
      Enumeration(
        Item.stable("Repository exposes a set of methods reflection UL"),
        Item.stable("Data is changed through methods and not entities - ensure data is updated correctly"),
        Item.stable("Repository hides away EF core code from application"),
        Item.stable("Example repository:"),
      ),
      <.br,
      cSharp("""public void AddBook(Book book)
              |public Book FindBook(int Id)
              |public void DeleteBook(Book book)
              |public void UpdateBook(Book book)
              |public List<book> Books(ICriteria criteria)""".stripMargin),
      Enumeration(
        Item.stable("Book (now has private setters):"),
      ),
      cSharp("""public void AddReview(Review review)
                |public void AddAuthor(Author authors)
                |...""".stripMargin),

      <.br,
      <.span("Source: https://docs.microsoft.com/en-us/dotnet/standard/microservices-architecture/microservice-ddd-cqrs-patterns/infrastructure-persistence-layer-implemenation-entity-framework-core#implement-custom-repositories-with-entity-framework-core"),
    ),

    headerSlide("Things to consider around Repository",
      Enumeration(
        Item.stable("Use Repository to hide DAL from application"),
        Enumeration(
          Item.stable("+ Interchangeable DAL"),
          Item.stable("- Can’t use O/RM as efficient"),
        ),
      ),
      <.br,
      <.img(VdomAttr("data-src") := "./img/pro-contra.jpeg", VdomStyle("maxHeight") := "500px"),
      
    ),
  )

  val chapter4 = chapter(
    headerSlide("Unit Of Work",
      <.blockquote("""“Maintains a list of objects affected by a business transaction and coordinates the writing out of changes and the resolution of concurrency problems.” - Martin Fowler""".stripMargin),
      Enumeration(
        Item.stable("The Unit of Work pattern is made to keep track of all changes made in database"),
        Enumeration(
          Item.stable("Avoid changes that are not written"),
        ),
      ),
      // TODO insert image
    ),

    headerSlide("Unit of Work in EF Core",
      Enumeration(
        Item.stable("Could see DbContext as Unit of Work"),
        Item.stable("Microsoft ‘recommends’ to build a UnitOfWork/Repository pattern around DbContext"),
        Enumeration(
          Item.stable("Create an abstraction between DAL and BLL"),
          Item.stable("Easier to maintain and test -> Changes from DAL don’t propagate to BLL"),
        ),
      ),
      cSharp("""public class UnitOfWork : IDisposable {
                |  private DbContext context = new DbContext();
                |  private GenericRepository<Book> bookRepository;
                |  public GenericRepository<Department> DepartmentRepository {
                |    // Return Singleton instance
                |  }
                |  public void Save() { context.SaveChanges(); }
                |  // TODO Implement Dispose()
                |}""".stripMargin),
      <.span("Source: https://docs.microsoft.com/en-us/aspnet/mvc/overview/older-versions/getting-started-with-ef-5-using-mvc-4/implementing-the-repository-and-unit-of-work-patterns-in-an-asp-net-mvc-application"),
    ),
  )

  val chapter5 = chapter(
    headerSlide("Query Object (1/2)",
      Enumeration(
        Item.stable("“A Query Object is an interpreter [Gang of Four], that is, a structure of objects that can form itself into a SQL query.”"),
        Item.stable("Makes it possible to create queries without knowing SQL and/or database schema."),
      ),

      cSharp("""public class BookQuery : IBookQuery {
              |  public bool LoadAuthor { get; set; } = false;
              |  public int? AuthorId { get; set; } = null;
              |
              |  public async Task<IEnumerable<Book>> Execute(AppDBContext context) {
              |    if (AuthorId == null) {
              |      if (LoadAuthor) return await context.Set<Book>().Include(b=>.Author).ToListAsync();
              |      else return await context.Set<Book>().ToListAsync();
              |    } else return await context.Set<Book>().Where(b =>b.AuthorId==(int)).ToListAsync();
              |    throw new NotImplementedException();
              | } }""".stripMargin),
    ),

    headerSlide("Query Object (2/2)",
      Enumeration(
        Item.stable(
          <.span("Another look on Query objects"), <.br,
          <.a(^.href := "https://www.rahulpnath.com/blog/query-object-pattern-and-entity-framework-making-readable-queries/", "Using Expression type")
        ),
        Item.stable(
          <.span("Library for implementing Query Objects and/or repository. E.g."), <.br,
          <.a(^.href := "https://github.com/urfnet/URF.NET", "URF.NET")
        ),
      ),
    ),
  )

  val chapter6 = chapter(
    headerSlide("Object Mappers",
      Enumeration(
        Item.stable("Transform between Entity classes and DTOs"),
        Enumeration(
          Item.stable("Typically one DTO per ‘view’"),
          Item.stable("Transformation is done in DI service"),
        ),
        Item.stable("Manually way to create LINQ transformation manually → Time consuming"),
        Item.stable("‘Automatic’ use a library that make use of ‘IQueryable’"),
        Enumeration(
          Item.stable(<.span("EF Core in action recommends "),<.a(^.href := "https://github.com/Automapper/Automapper", "AutoMapper")),
          Item.stable("AutoMapper work with convention eg. convert PromotionNewPrice to Promotion.NewPrice since there navigational property Promotion"),
        ),
      ),

      cSharp("""var config = new MapperConfiguration(cfg => {
              |   cfg.CreateMap<Book, BookDto>();   
              |   cfg.CreateMap<Review, ReviewDto>();
              |});
              |using (var context = new AppDbContext())
              |{
              |   var result = context.Books.  
              |       ProjectTo<BookDto>(config)
              |       .ToList();
              |}""".stripMargin),
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
      <.span("Frontpage meme: https://xkcd.com/844/"),
      <.span("Architecture: https://docs.microsoft.com/en-us/aspnet/mvc/overview/older-versions/getting-started-with-ef-5-using-mvc-4/implementing-the-repository-and-unit-of-work-patterns-in-an-asp-net-mvc-application")
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