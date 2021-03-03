
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

object EfCoreIntro {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("Entity Framework Core"),
      <.br,
      <.h4("Models and Relationships"),
      <.img(VdomAttr("data-src") := "./img/syntatic_sugar.jpeg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Constraints"),
        Item.stable("Keys / Unique"),
        Item.stable("Excludingh"),
        Item.stable("Shadow properties"),
        Item.stable("Relationships"),
        Item.stable("Inheritance"),
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
    headerSlideLeftAligned("Different tactics",
      Enumeration(
        Item.stable(
          <.span("Conventions"), <.br,
          <.span(VdomStyle("fontSize") := "30px", "Primary key → Class property with name <class-name>Id or Id")
        ),
        Item.stable(
          <.span("Data annotations (Data classes)"), <.br,
          <.span(VdomStyle("fontSize") := "30px", "Primary key → Annotate property with [Key]")
        ),
        Item.stable(
          <.span("Fluent API (always in DbContext)"), <.br,
          <.span(VdomStyle("fontSize") := "30px", "Primary key → In method OnModelCreating in DbContext")
        ),
      ),
      cSharp("""public class Context : DbContext {
               |  public void protected override void 
               |          OnModelCreating(ModelBuilder mb) {
               |    mb.Entity<Car>().HasKey(c => c.LicensePlate);
               |} }""".stripMargin),
    ),

    headerSlide("Constraints - Fluent API",
      cSharp("""class MyContext : DbContext {
               |    ...
               |    DbSet<Client> clients {get; set;}
               |    protected override void 
               |          OnModelCreating(ModelBuilder mb) {
               |        mb.Entity<Client>()
               |            .Property(b => b.Email)
               |            .IsRequired(); // Not null
               |          //.HasMaxLength(500)
               |    }}""".stripMargin),
    ),


    headerSlide("Constraints - Annotations",

      cSharp("""namespace MyApp.Models {
               |  public class Client {
               |      [Required]
               |      public int ID {get ; set;}
               |      [Required]
               |      [MaxLength(64)]
               |      public string FirstName {get; set;}
               |      [Required]
               |      [MaxLength(64)]
               |      public string LastName {get; set;}
               |      public string Email {get; set;}
               |      ...
               |      public Membership Membership {get; set;}
               |}}""".stripMargin),
    ),
  )

  val chapter3 = chapter(
    headerSlideWithColumns("Primary key")
    (
      Enumeration(
        Item.stable("Convention")
      ),
      cSharp("""public int Id {get; set;}
               |public int <ClassName>Id { get; set;}""".stripMargin),

      fadeInFragment(
        Enumeration(
          Item.stable("Annotation")
        ),
        cSharp("""[Key]
                |public int Identifier {get; set;}""".stripMargin),
      ),
    )(
      fadeInFragment(
        Enumeration(
          Item.stable("Fluent API")
        ),
        cSharp("""modelBuilder.Entity<Book>()
                 |  .HasKey(b => b.ID);""".stripMargin),
      ),
      fadeInFragment(
        Enumeration(
          Item.stable("Composite")
        ),
        cSharp("""modelBuilder.Entity<Author>()
                |  .HasKey(a => new { a.FirstName, a.LastName});""".stripMargin),
      ),
    ),

    headerSlide("Index & Uniqueness",
      cSharp("""public class MyDbContext: DbContext {
                |  protected override void OnModelCreating(ModelBuilder mb) {
                |    // Alternative key - unique
                |    mb.Entity<Book>().HasAlternateKey(b => b.Isbn).HasName("UniqueIsbn");
                |    // Index - not nessesaryly unique
                |    mb.Entity<Book>().HasIndex(b => b.Isbn)
                |        .HasName("Isbn index").IsUnique(); // Remember isUnique with Index
                |    // Composite key - also available with HasIndex
                |    mb.Entity<Author>().HasKey(a => new { a.FirstName, a.LastName});
                |}}""".stripMargin),
    ),
  )

  val chapter4 = chapter(
    headerSlideLeftAligned("Excluding properties",
      cSharp("""public class Person {
               |  ...
               |  [NotMapped]
               |  public string FullName {
               |    get => $"{FirstName} {MiddleName} {LastName}";
               |}}""".stripMargin),

      Enumeration(
        Item.stable("[NotMapped] not needed when no public setter"),
        Item.stable("Alternatively in FluentApi"),
      ),
      cSharp("mb.Entity<Book>().Ignore(b => b.FullTitle);"),

      Enumeration(
        Item.stable("Types"),
      ),
      cSharp("mb.Ignore<BookMetadata>();"),
  
    ),

    headerSlideLeftAligned("Database generated values", // TODO where to place?
      <.span("In Books.cs add"), <.br,
      cSharp("""[DatabaseGenerated(DatabaseGeneratedOption.Identity)]
               |public DateTime Created {get;set;}""".stripMargin),

      <.span("In DbContext add"), <.br,
      cSharp("mb.Entity<Book>().Property(b => b.Created).HasDefaultValue(DateTime.Now)"),

      <.span("Above is migrations time decided. If value should dynamicly from SQL Server"), <.br,
      cSharp("""mb.Entity<Book>().Property(b => b.Created)
              |   //SQLite: .HasDefaultValueSql("CURRENT_TIME")
              |   .HasDefaultValueSql("getdate()")""".stripMargin),

      <.b("Note"), <.span(": DatabaseGeneratedOption.{Identity, Computed, None}"),
    ),
  )

  val chapter5 = chapter(
    headerSlideLeftAligned("Shadow properties",
      Enumeration(
        Item.stable("Hidden from model"),
        Item.stable("To make OO model clean"),
      ),

      <.br,
      <.span("Steps:"), <.br,

      OrderedList(
        Item.stable("Remove CreatedAt in Books.cs"),
        Item.stable("In DbContext insert in onModelCreating"),
      ),
      cSharp("""modelBuilder.Entity<Book>().Property<DateTime>("Created")
              |   .HasDefaultValueSql("getdate()");"""".stripMargin),
    ),
  )
  
  val chapter6 = chapter(
    headerSlideWithColumns("1-1 relationship (1/2)")
    ( // TODO arrows above code?
      cSharp("""public class Membership
                |{
                |  [Required]
                |  public int ID {get; set;}
                |  [Required]
                |  public DateTime Initiated {get; set;}
                |  [Required]
                |  public Genre Genre {get; set;}
                |  public int ClientId {get;set;}
                |  public Client Client {get;set;}
                |}""".stripMargin),
      fadeInFragment(
        <.b("Note"), <.span(": Client is a navigational property"),
      )
    )
    (
      cSharp("""public class Client {
                |  [Required]
                |  public int ID {get ; set;}
                |  [Required]
                |  [MaxLength(64)]
                |  public string FirstName {get; set;}
                |  [Required]
                |  [MaxLength(64)]
                |  public string LastName {get; set;}
                |  public string Email {get; set;}
                |  ...
                |  public Membership Membership {get; set;}
                |}""".stripMargin),
      fadeInFragment(
        <.b("Note"), <.span(": Membership is a navigational property"),
      )

    ),

    headerSlide("1-1 relationship (2/2)",
      <.span("Or with Fluent API"),
      cSharp("""public class MyDbContext: DbContext {
                |  protected override void OnModelCreating(ModelBuilder modelBuilder) {
                |    modelBuilder.Entity<Client>()
                |         .HasOne(s => s.Membership)
                |         .WithOne(l => l.Client)
                |         .HasForeignKey<Membership>();
                |}}""".stripMargin),
    ),
    
    headerSlideWithColumns("1-N relationship (1/2)")
    (
      cSharp("""public class Book
                |{    
                |  public int ID { get; set;}
                |  [MaxLength(32)]
                |  public string Title {get; set;}
                |  public Author Author {get; set;}
                |  public int AuthorId {get; set;}
                |}""".stripMargin),
    )(
      cSharp("""public class Author
              |{
              |    [Required]
              |    [Key]
              |    public int ID {get; set;}
              |    public string FirstName {get; set;}
              |    public DateTime DoB {get; set;}
              |    public string Nationality {get;set; }
              | 
              |    public List<Book> Books {get; set;}
              |}""".stripMargin),
    ),

    headerSlide("1-N relationship (2/2)",
      <.span("Or with Fluent API"),
      cSharp("""public class MyDbContext: DbContext {
              |  protected override void OnModelCreating(ModelBuilder modelBuilder) { 
              |    modelBuilder.Entity<Book>()
              |      .HasOne(b => b.Author)
              |      .WithMany(a => a.Books)
              |      .HasForeignKey(b => b.AuthorId);
              |}}""".stripMargin),
    ),

    headerSlideLeftAligned("N-N relationship (1/3)",
      Enumeration(
        Item.stable("No annotations / conventions"),
        Item.stable("Done in Fluent API"),
      ),
      <.br,
      <.span("Navigational property in Books.cs"), <.br,
      cSharp("public List<PersonalLibraryBook> PersonalLibraryBooks {get; set;}"),

      <.span("Navigational property in PersonalLibrary.cs"), <.br,
      cSharp("public List<PersonalLibraryBook> PersonalLibraryBooks {get; set;}"),
    ),

    headerSlideLeftAligned("N-N relationship (2/3)",
      <.span("Navigational properties in PersonalLibraryBook.cs"), <.br,
      cSharp("""public class PersonalLibraryBook {
                |  public int BookId {get; set;}
                |  public Book Book {get; set;}
                |  public int PersonalLibraryId {get; set;}
                |  public PersonalLibrary PersonalLibrary {get; set;}
                |}""".stripMargin),
    ),

    headerSlide("N-N relationship (3/3)",
      <.b("In Fluent API"), <.br,
      <.span("In OnModelCreating add the following"),
      cSharp("""public class MyDbContext: DbContext {
              |  protected override void OnModelCreating(ModelBuilder modelBuilder) { 
              |    // Book - PersonalLibrary (many to many relationship)
              |    modelBuilder.Entity<PersonalLibraryBook>()
              |        .HasKey(p => new {p.BookId, p.PersonalLibraryId});
              |    modelBuilder.Entity<PersonalLibraryBook>()
              |        .HasOne(       plb => plb.Book)
              |        .WithMany(     b   => b.PersonalLibraryBooks)
              |        .HasForeignKey(plb => plb.BookId);
              |    modelBuilder.Entity<PersonalLibraryBook>()
              |        .HasOne(       plb => plb.PersonalLibrary)
              |        .WithMany(     pl  => pl.PersonalLibraryBooks)
              |        .HasForeignKey(plb => plb.PersonalLibraryId);
              |}}""".stripMargin),
    ),
  )

  val chapter7 = chapter(
    headerSlideLeftAligned("Inheritance (1/2)",
      Enumeration(
        Item.stable("By convention derived class are managed in a TPH (table-per-hierarchy) pattern"),
        Item.stable("A discriminator column to identify type."),
        Item.stable(<.span("Types should be "), <.b("explicitly"), <.span(" added as DbSet to DbContext")),
        Enumeration(
          Item.stable(<.span("Or in Fluent API"), <.br,
            cSharp("""modelBuilder.Entity<RssBlog>()
                     |    .HasBaseType<Blog>();""".stripMargin),
          ),
        ),
      ),
    ), 

    headerSlideLeftAligned("Inheritance - discriminator (2/2)",
      Enumeration(
        Item.stable("Discriminator is a database attribute and can be manipulated"),
        Enumeration(
          Item.stable("Used to tell about type"),
          Item.stable(<.span("Use Fluent API to change name values"), <.br,
            cSharp("""modelBuilder.Entity<Blog>()
                    |  .HasDiscriminator<string>("blog_type")
                    |  .HasValue<Blog>("blog_base")
                    |  .HasValue<RssBlog>("blog_rss");""".stripMargin),
          ),
        ),
      ),
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
          chapter7,
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