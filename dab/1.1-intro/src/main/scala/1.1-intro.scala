
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Intro {
  import Enumeration._
  
  val exampleMovieTable = <.table(
    <.thead(
      <.tr(
        <.th("title"),
        <.th("year"),
        <.th("length"),
        <.th("genre"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("SciFi"),
      ),
      <.tr(
        <.td("Ghost"),
        <.td("1990"),
        <.td("127"),
        <.td("Drama"),
      ),
      <.tr(
        <.td("Skyfall"),
        <.td("2012"),
        <.td("143"),
        <.td("Action"),
      ),
    ),
  )

  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("I4DAB"),
      <.br,
      <.h4("Introduction to databases")
    ),

  )

  val chapter2 = chapter(
    headerSlide(
      "Qualifications",
      Enumeration(
        Item.stable(colorRedSpan("Summarize", Some(1)), <.span(" methods for selection and modeling of a concrete database technology.")),
        Item.stable(colorRedSpan("Compare",   Some(1)), <.span(" data structures in databases with data structures in an application.")),
        Item.stable(colorRedSpan("Applying",  Some(1)), <.span(" standard network technologies to provide network access to a given database.")),
        Item.stable(colorRedSpan("Analyze",   Some(1)), <.span(" techniques to ensure cstableorrect data in a database and for sharing the responsibility for correct data between databases and applications.")),
        Item.stable(colorRedSpan("Applying",  Some(1)), <.span(" different types of databases related to application development with help from development tools and programming methods.")),
        Item.stable(colorRedSpan("Describe",  Some(1)), <.span(" the most common database subject expressions.")),
      )
    ),

    headerSlide("Examination",
      Enumeration(
        Item.stable("Assessment: Passed/Failed"),
        Item.stable("3 assignments + 3 review should be approved"),
        Item.stable("Assignment are graded on Blackboard:"),
        Enumeration(
          Item.stable("Passed (Blackboard score 2)"),
          Item.stable("Passed with comments(Blackboard score 1)"),
          Item.stable("Failed - resubmit (Blackboard score 0)"),
        ),
        Item.stable("Review, BB score 1 approved, 0 not approved."),
        Item.stable("Assignment should be handed in before deadline to be graded"),
      )
    ),

    headerSlide(
      "Plagiarism",
      <.img(VdomAttr("data-src") := "./img/plagiarism.png", VdomStyle("max-height") := "600px"),
      <.a(
        ^.href := "https://library.au.dk/studerende/plagiering/",
        "https://library.au.dk/studerende/plagiering/"
      )
    ),
    
    headerSlide(
      "Schedule",
      <.img(VdomAttr("data-src") := "./img/schedule.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "Form",
      Enumeration(
        Item.stable("Lectures (Theory, code examples, live coding, exercises)"),
        Item.stable("3 mandatory exercises (hand-in)"),
        Enumeration(
          Item.stable("Have to be approved!"),
        ),
        Item.stable("3 Peer review"),
        Enumeration(
          Item.stable("Have to be approved!"),
        ),
        Item.stable("Curriculum"),
        Enumeration(
          Item.stable("Database Modeling and Design - 5th edition"),
          Item.stable("MongoDB Applied Design"),
          Item.stable("Online articles / documentation"),
        ),
      )
    ),

  )

  val chapter3 = chapter(
    headerSlide(
      "Your expectations",
      <.b(VdomStyle("font-size") := "252px", "?"),
    ),

    headerSlide(
      "My expectations",
      Enumeration(
        Item.stable("Read material before class"),
        Item.stable("You solve exercises - and there will be exercises to do out of class"),
        Item.stable("Help each other"),
        Item.stable("Ask if we should talk about previous exercises"),
      ),
    ),
  )

  val chapter4 = chapter(
    auHeadlineSlide(
      <.h2("Intro"),
      <.br,
      <.h4("Relational Model"),
      <.img(VdomAttr("data-src") := "./img/theMoment.jpg", ^.cls := "headerMeme"),
    ),

    headerSlide(
      "Agenda",
      Enumeration(
        Item.stable("Database"),
        Item.stable("Relational model"),
        Item.stable("SQL (more in week 2, 3, and 4)"),
      ),
    ),

  )

  val chapter5 = chapter(
    headerSlide(
      "Database",
      Enumeration(
        Item.stable(<.span("Database is an abstractions on a filesystem, which allow"),
                    <.br,
                    <.span("common operations on data")),
        Item.stable("Database server"),
        Enumeration(
          Item.stable("Allow multiple simultaneous clients"),
          Item.stable("Access over network"),
          Item.stable("Can be spread on multiple hosts"),
        ),
      ),
      <.img(VdomAttr("data-src") := "./img/database.jpg", ^.cls := "side-image"),
    ),

    headerSlide(
      "Database server",
      Enumeration(
        Item.stable("Where is it running"),
        Enumeration(
          Item.stable("SQLite: Running when sqlite.exe is open"),
          Item.stable("SQL Server:"),
          Enumeration(
            Item.stable("When the docker container is running"),
            Item.stable("Or when native server is running"),
          ),
        ),
      ),
    ),

    headerSlide(
      "Data Model",
      Enumeration(
        Item.stable("Structure of the data"),
        Enumeration(
          Item.stable("Operations on the data"),
          Item.stable("Queries and modifications"),
        ),
        Item.stable("Constraints on the data"),
      ),
    ),

    headerSlideWithColumns("Data model")
      (
        Enumeration(
          Item.stable("Relational model vs semi-structure model"),
          Item.stable("Values"),
          Item.stable("Objects"),
        ),
        exampleMovieTable,
      )(
        xml(
        """<Movies>
          |  <Movie title=”Star Wars”>
          |    <Year>1977</Year>
          |    <Length>124</Length>
          |    <Genre>SciFi</Genre>
          |  </Movie>
          |  <Movie title=”Ghost”>
          |    <Year>1990</Year>
          |    <Length>127</Length>
          |    <Genre>Drama</Genre>
          |  </Movie>
          |  <Movie title=”Skyfall”>
          |  <Year>2012</Year>
          |  <Length>143</Length>
          |  <Genre>Action</Genre>
          |  </Movie>
          |</Movies>""".stripMargin)
      ),
  )

  val chapter6 = chapter(
    headerSlide(
      "Relational model",
      <.blockquote("""”Data dominates. If you’ve chosen the
                     |right data structures and organized
                     |things well, the algorithms will almost
                     |always be self-evident. Data structures,
                     |not algorithms, are central to
                     |programming.” – Rob Pike in 1989""".stripMargin)
    ),

    headerSlideWithColumns("Basic")
      (
        Enumeration(
          Item.stable("Relation (Two-dimensional table)"),
          Enumeration(
            Item.stable("Row represent an entity (object)"),
            Item.stable("Column represent an attribute (property)"),
          ),
          Item.stable("Attributes"),
          Enumeration(
            Item.stable("Must be atomic, eg. int, string, ..."),
          ),
          Item.stable("Schema"),
          Item.stable("Tuples"),
        ),
      )
      (
        exampleMovieTable 
      ),

    headerSlide(
      "Representation of Relations",
      Enumeration(
        Item.stable("Relations are Set<Tuple>"),
        Enumeration(
          Item.stable("Order of rows and columns are not significant"),
        ),
        Item.stable("Relations are not static"),
        Enumeration(
          Item.stable("Addition, modifications, deletion"),
          Item.stable("Less common, changes to schema"),
        ),
      ),
    ),

    headerSlide(
      "Keys",
      Enumeration(
        Item.stable("Fundamental constraint"),
        Item.stable("A set of attributes forms a key iff. we do not allow two tuples to have the same values in all key attributes"),
        Enumeration(
          Item.stable("Eg. Movies(title, year, length, genre)"),
        ),
        Item.stable("Keys can be generated"),
        Item.stable("Key constraints is a statement for all possible instances of the relation"),
        exampleMovieTable,
      ),
    ),

    headerSlide(
      "Schema",
      sql("""Movies (
        |  title: string, year: integer, length: integer, genre: string, studioName: string
        |)
        |MovieStar (
        |  name: string, address: string, gender: char
        |)
        |Studio (
        |  name: string, address: string, pressC: integer
        |)""".stripMargin)
    ),

    headerSlideWithColumns("SQL")
      (
        Enumeration(
          Item.stable("Data-Definition sub-language"),
          Enumeration(
            Item.stable("Declaring database structures"),
            Item.stable("Declaring database constraints"),
          ),
          Item.stable("Data-Manipulation sub-language"),
          Enumeration(
            Item.stable("Querying database for content"),
            Item.stable("Modifying database content"),
          ),
        ),
      )
      (
        sql("""CREATE TABLE
        |Movies (
        |  title CHAR(100),
        |  year INT,
        |  length INT,
        |  genre CHAR(20)
        |)""".stripMargin),

        sql("""SELECT title
        |FROM Movies
        |ORDER BY year""".stripMargin)
      ),

    headerSlide(
      "Relations in SQL",
      Enumeration(
        Item.stable("Stored relations"),
        Enumeration(
          Item.stable("Tables"),
        ),
        Item.stable("Views"),
        Enumeration(
          Item.stable("Relations defined by computation"),
          Item.stable("Not stored but constructed when needed"),
        ),
        Item.stable("Temporary tables"),
        Enumeration(
          Item.stable("Constructed by SQL processor"),
          Item.stable("Constructed as part of queries and data modification"),
          Item.stable("Thrown away after"),
        ),
      ),
    )
  )

  val chapter7 = chapter(
    headerSlide(
      "Simple table definition",
      <.blockquote("""”Fold knowledge into data, so program
      |logic can be stupid and robust.” – Eric Raymond""".stripMargin)
    ),

    headerSlide(
      "Data types",
      Enumeration(
        Item.stable("Strings: CHAR(n) and VARCHAR(n)"),
        Item.stable("Bit: BIT(n) and BIT VARYING(n)"),
        Item.stable("BOOLEAN"),
        Item.stable("Numbers: INT, SHORT INT"),
        Item.stable("Floating point numbers: FLOAT, REAL and DOUBLE"),
        Item.stable("Time: DATE and TIME"),
        Item.stable(<.b("Note"), <.span(" : Differs from SQL engine to engine.")),
        ),
    ),

    noHeaderSlide(
      sql("""CREATE TABLE
                    |Movies (
                    |  title CHAR(100),
                    |  year INT,
                    |  length INT,
                    |  genre CHAR(20)
                    |)""".stripMargin),

      sqlFragment("""SELECT title
                    |FROM Movies
                    |ORDER BY year
                    |)""".stripMargin),

      sqlFragment("""ALTER TABLE Movies
                    |ADD studioName CHAR(30))""".stripMargin),

      sqlFragment("DROP TABLE Movies"),

      sqlFragment("""CREATE TABLE Movies (
                    |  title CHAR(100), year INT, length INT, genre CHAR(20),
                    |  UNIQUE KEY (title, year)
                    |)""".stripMargin),

      sqlFragment("""CREATE TABLE Movies (
                     |  title CHAR(100), year INT, length INT,
                     |  genre CHAR(20) DEFAULT ‘UNKNOWN’,
                     |)""".stripMargin),

    ),

  )

  val chapterEnd = chapter(
    fullscreenImageSlide(
      "./img/end.jpg"
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
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

          chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}