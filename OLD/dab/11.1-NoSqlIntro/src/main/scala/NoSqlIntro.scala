
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object NoSqlIntro {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("NoSQL - intro"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Modeling / Normalization"),
        Item.stable("Polymorphism"),
        Item.stable("Working with NoSQL"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlide("MongoDb / Document databases",
      Enumeration(
        Item.stable("Breaks 1st normal form:"),
        Enumeration(
          Item.stable("each row contains one value"),
        ),
        Item.stable("Specific for MongoDB - it uses"),
        Enumeration(
          Item.stable("Stores an array of values"),
          Item.stable("JSON types, BSON types, Arrays of values, object, null"),
          Item.stable("Allows documents to be up to 16 MB"),
        ),
        Item.stable("Designed to make it easier (and faster) to scala horizontally on different computers"),
        Enumeration(
          Item.stable("Sharding"),
          Item.stable("No JOIN operation"),
        ),
        Item.stable("No longer a given way of designing a database"),
      ),
    ),

    headerSlide("Document databases",
      Enumeration(
        Item.stable("Denormalizing data"),
        Enumeration(
          Item.stable("Embedding all related data into a single document (Json in MongoDB)"),
          Item.stable("Retrieving or writing data in single operation"),
          Item.stable("Fewer queries"),
        ),
      ),
      <.br,
      <.span("E.g."),
      javascript("""{   "id": "1",
                  |    "firstName": "Thomas",
                  |    "lastName": "Andersen",
                  |    "addresses": [
                  |        {   "line1": "100 Some Street",
                  |            "line2": "Unit 1",
                  |            "city": "Seattle",
                  |            "state": "WA",
                  |            "zip": 98012 } ],
                  |    "contactDetails": [
                  |        {"email": "thomas@andersen.com"},
                  |        {"phone": "+1 555 555-5555", "extension": 5555} ] }
                  |""".stripMargin),

    ),
  )

  val chapter3 = chapter(
    headerSlide("Embedding",
      Enumeration(
        Item.stable("So when to embed?"),
        Enumeration(
          Item.stable("There are a contained relationship"),
          Item.stable("There are a one-to-few relationship"),
          Item.stable("Embedded data changes infrequently"),
          Item.stable("Embedded data will not grow without bound"),
          Item.stable("Embedded data is often queried together"),
        ),
      ),
      // TODO insert image
    ),

    headerSlide("Referencing",
      Enumeration(
        Item.stable("No concept of foreign key"),
        Enumeration(
          Item.stable("Link between entities is considered weak links and must be maintained by application(s)"),
        ),
        Item.stable("Reference when"),
        Enumeration(
          Item.stable("One-to-many relationships"),
          Item.stable("Many-to-many relationships"),
          Item.stable("Related data changes frequently"),
          Item.stable("Data could be unbounded"),
        ),
      ),
    ),

    headerSlideWithColumns("1-N")
    (
      // TODO insert image
      <.b("Solution 1"), <.br,
      <.span("Publishers"), <.br,
      javascript("""{ "id": "1",
                |  "name": "O’Reilly",
                |  "books": [1, 2, 3, 12, 15, 19, 25, 26, 27, 49, 50 ...],
                |  ...}""".stripMargin), <.br,
      <.span("Books"), <.br,
      javascript("""{   "id": "1", "name": "Learning python" } 
                  |{   "id": "2", "name": "Jenkins 2 - up & running" } 
                  |{   "id": "3", "name": "Head First Kotlin" } 
                  |...
                  |{   "id": "50", "name": "Mastering Ethereum" } 
                  |...""".stripMargin), <.br,
    )
    (
      <.b("Solution 2"), <.br,
      <.span("Publishers"), <.br,
      javascript("""{ "id": "1",
                  |  "name": "O’Reilly", }""".stripMargin), <.br,
      <.span("Books"), <.br,
      javascript("""{  "id": "1", "name": "Learning python",
                  |     "publiser_id": 1 } 
                  |{ "id": "2", "name": "Jenkins 2 - up & running",
                  |     "publiser_id": 1}  
                  |{ "id": "3", "name": "Head First Kotlin",
                  |     "publiser_id": 1 } 
                  |...
                  |{ "id": "50", "name": "Mastering Ethereum",
                  |      "publiser_id": 1 } 
                  |... """.stripMargin), <.br,
    ),

    headerSlide("N-N",
      // TODO insert image
      <.span("Putting joining ids in both collections"), <.br,
      <.span("Authors"), <.br,
      javascript("""{ "id": "a1", "name": "J.K. Rowling", "books": ["b1", "b7", "b8", "b9" ]}
                  |{ "id": "a2", "name": "Amanda Berlin", "books": ["b10", "b14" ]}
                  |{ "id": "a3", "name": "Lee Brotherston", "books": ["b10", "b11" ]}""".stripMargin), <.br,
      <.span("Books"), <.span,
      javascript("""{ "id": "b1", "name": "Harry Potter and the Philosophers Stone", "authors": ["a1"] }
                    |{ "id": "b7", "name": "Harry Potter and the Goblet of Fire", "authors": ["a1"] }
                    |{ "id": "b10", "name": "Defensive Security Handbook", "authors": ["a3", "a2"] }""".stripMargin), <.br,
      Enumeration(
        Item.stable("Alternatively merging some data together based on application usage"),
        Enumeration(
          Item.stable("E.g. author name with book - since author name don’t change to often"),
        )
      ),
    ),
  )

  val chapter4 = chapter(
    headerSlide("Mixing different documents",
      Enumeration(
        Item.stable("Can be necessary to mix different documents type in same collection"),
        Enumeration(
          Item.stable(
            <.span("E.g. to keep them in same partition"), <.br,
            <.span("Problem: Which document is of which type"), <.br,
            <.span("Solution: Then put type on the entities in collection"), <.br,
          ),
        ),
      ),
      <.br,
      javascript("""{   "id": "1",
                  |    "name": "O’Reilly",
                  |    "type": "publisher"
                  |},
                  |{   "id": "2",
                  |    "name": "Mark Hamill",
                  |    "type": "actor"
                  |}""".stripMargin),
    ),

    headerSlide("Polymorphism",
      Enumeration(
        Item.stable("n document database you can store different types in same collection"),
        Enumeration(
          Item.stable("Performing queries on shared fields on different types"),
          Item.stable("Performing queries on specific type"),
        ),
        Item.stable("Avoid ALTER TABLE statements while preserving ability to evolve schema"),
        Enumeration(
          Item.stable("ALTER TABLE can be time consuming"),
        ),
        Item.stable("Handling schema evolution in NoSQL is often done in application"),
        Enumeration(
          Item.stable("Updating/altering entities when read"),
          Item.stable("Algorithm"),
          OrderedList(
            Item.stable("Read data"),
            Item.stable("If data is of old version -> update"),
            Item.stable("Save data"),
          ),
        ),
      )
    ),
  )

  val chapter5 = chapter(
    headerSlide("Drawbacks",
      Enumeration(
        Item.stable("Storage inefficiency"),
        Enumeration(
          Item.stable("No rules for which data is present"),
          Item.stable("Small values vs long property names"),
        ),
        Item.stable("Difficulties creating indexes"),
        Enumeration(
          Item.stable("Some of this can be handled by using array and index this instead of using object."),
        ),
      )
    ),

    // TODO add something about why one could end up in this example
    headerSlideWithColumns("Indexing example")
    (
      javascript("""{
                    |...
                    | properties : {
                    |  'Seek Time' : '5ms',
                    |  'Rotational Speed' : '15k RPM',
                    |  'Transfer Rate' : '...'
                    |  ... }
                    |}""".stripMargin)
    )
    (
      javascript("""{
                  | ...
                  | properties: [
                  |   ['Seek Time', '5ms' ],
                  |   ['Rotational Speed', '15k RPM'],
                  |   ['Transfer Rate', '...'],
                  |   ... ]
                  |
                  |}""".stripMargin)
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