
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Modeling {
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
      <.h2("Modeling"),
      <.br,
      <.h4("Entity Relationship Diagram"),
      <.img(VdomAttr("data-src") := "./img/modeling.png", ^.cls := "headerMeme"),
    ),

    // TODO agenda

    headerSlide(
      "Why",
      Enumeration(
        Item.stable("Create model before system to avoid expensive changes"),
        Item.stable("Simplify real world problems to create a simple relational model"),
        Item.stable("Communicate design between developers and / or customer(s)"),
      ),
    ),

    headerSlide(
      "What",
      Enumeration(
        Item.stable("Domain rules and business rules"),
        Item.stable("Data structures and object structures"),
        Item.stable("!Processes"),
      ),
    ),

    headerSlide(
      "How",
      Enumeration(
        Item.stable("Design interaction between application and database"),
        Item.stable("Optional: Use an Object Relational Mapper"),
      ),
    ),

  )

  val chapter2 = chapter(
    // TODO start with an example
    headerSlideWithColumns("Process")
    (
      Enumeration(
        Item.stable("Requirements"),
        Item.stable("Logical design"),
        Item.stable("Physical design"),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/databaseLifeCycle.png", VdomStyle("max-height") := "700px"),
    ),

    headerSlide(
      "Requirements",
      Enumeration(
        Item.stable("From producers and consumers of data"),
        Item.stable("Must include"),
        Enumeration(
          Item.stable("Data"),
          Item.stable("Relationship between data"),
        ),
      ),
       <.br,
      <.img(VdomAttr("data-src") := "./img/logicalDesign.png", VdomStyle("height") := "300px"),

    ),

    headerSlide(
      "Logical design (1/2)",
      Enumeration(
        Item.stable("Schema showing all data and relationships between entities"),
        Item.stable("Can be done in Entity-Relationship (E/R) or Unified Modeling Language (UML)"),
        Item.stable(<.b("Goal"), <.span(": Model that can be transferred into database tables")),
      ),
    ),

    headerSlide(
      "Logical design (2/2)",
      Enumeration(
        Item.stable("Conceptual data modeling"),
        Enumeration(
          Item.stable("Typically modeled by E/R and UML"),
          Item.stable("Relationships, super-types etc."),
        ),
        Item.stable("View integration"),
        Enumeration(
          Item.stable("Normally necessary in large projects"),
          Item.stable("Eliminate inconsistencies and redundancies"),
          Item.stable("E.g. find synonyms, aggregate and generalize"),
        ),
        Item.stable("Transform to SQL (covered in week 2-3)"),
        Item.stable("Normalize tables (covered in week 5)"),
      ),
    ),

  )

  val chapter3 = chapter(
    auHeadlineSlide(
      <.h2("Entity relationship diagrams"),
      <.br,
      <.h4("Chen notation"),
    ),

    headerSlideWithColumns("Components")
    (
      Enumeration(
        Item.stable("Entities: principle data objects"),
        Enumeration(
          Item.stable("Key uniquely identifies occurrence"),
        ),
        Item.stable("Weak entities"),
        Enumeration(
          Item.stable("Connected to parent – cannot exists without"),
        ),
        Item.stable("Relationship"),
        Enumeration(
          Item.stable("Associations between entities"),
        ),
        Item.stable("Attributes"),
        Enumeration(
          Item.stable("Multivalued: Multiple values"),
          Item.stable("Complex: Attribute contains more information"),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erComponents.png", VdomStyle("max-height") := "600px"),
    ),
  

  headerSlideWithColumns("Relationship (1/3)")
    (
      Enumeration(
        Item.stable("No standard for connectivity"),
        Item.stable("One-to-one"),
        Enumeration(
          Item.stable("Mapped with attribute"),
        ),
        Item.stable("One-to-many"),
        Enumeration(
          Item.stable("Mapped with attribute"),
        ),
        Item.stable("Many-to-many"),
        Enumeration(
          Item.stable("relationship table/connection entity – can have attributes associated"),
        ),
        Item.stable("Constraints on numbers (cardinality) are possible"),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erRelationshipConnectivity.png", VdomStyle("max-height") := "800px"),
    ),

  headerSlideWithColumns("Relationship (2/3)")
    (
      Enumeration(
        Item.stable("Binary"),
        Enumeration(
          Item.stable("Most common"),
        ),
        Item.stable("Binary recursive"),
        Enumeration(
          Item.stable("Relationship between entities of same type"),
        ),
        Item.stable("Ternary - in a moment"),
        Item.stable("Higher degree"),
        Enumeration(
          Item.stable("Not used"),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erRelationshipDegree.png", VdomStyle("max-height") := "800px"),
    ),

    headerSlide(
      "Ternary (1/3)",
      Enumeration(
        Item.stable("Should only be used when binary relationships are not sufficient"),
        Item.stable("A ‘one’ relationship iff. one instance is associated with one of each of the two other"),     
        Item.stable("else ‘many’"),
      )
    ),

    headerSlideWithColumns("Ternary relationship (2/3)")
    (
      Enumeration(
        Item.stable(<.b("Assertion 1"), <.span(":")),
        Enumeration(
          Item.stable("Relationship is one to one to one"),
          Item.stable("Technician can work on multiple project – having different notebooks for each"),
        ),
        Item.stable(<.b("Assertion 2"), <.span(":")),
        Enumeration(
          Item.stable("Employee works on one project in one location. Can be in different locations depending on project"),
          Item.stable("At a location there can be multiple employee on a given project."),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erTenaryOneOneOne.png", VdomStyle("max-height") := "300px"),
      <.br,
      <.img(VdomAttr("data-src") := "./img/erTenaryOneOneMany.png", VdomStyle("max-height") := "300px"),
    ),

    headerSlideWithColumns("Ternary relationship (3/3)")
    (
      Enumeration(
        Item.stable(<.b("Assertion 3"), <.span(":")),
        Enumeration(
          Item.stable("A manager of a project manages multiple engineers"),
          Item.stable("Manager of an engineer can manage multiple projects"),
          Item.stable("Engineer working under a manager can work on multiple projects"),
        ),
        Item.stable(<.b("Assertion 4"), <.span(":")),
        Enumeration(
          Item.stable("..."),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erTenaryOneManyMany.png", VdomStyle("max-height") := "300px"),
      <.br,
      <.img(VdomAttr("data-src") := "./img/erTenaryManyManyMany.png", VdomStyle("max-height") := "300px"),
    ),

    headerSlideWithColumns("Relationship (3/3)")
    (
      Enumeration(
        Item.stable("Optional"),
        Enumeration(
          Item.stable("Minimum of zero"),
        ),
        Item.stable("Mandatory"),
        Enumeration(
          Item.stable("Minimum of one"),
        ),
        Item.stable("Existence is often inferred"),
        Enumeration(
          Item.stable("E.g. weak entities can’t have optional relationship to parent."),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erRelationshipExistence.png", VdomStyle("max-height") := "400px"),
    ),

    headerSlideWithColumns("Generalization / Inheritence")
    (
      Enumeration(
        Item.stable("Disjoint"),
        Enumeration(
          Item.stable("Requires mutually exclusiveness in sup-types"),
        ),
        Item.stable("Overlapping"),
        Enumeration(
          Item.stable("Total or partial covered"),
          Item.stable("Total → double line"),
        ),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erGeneralization.png", VdomStyle("max-height") := "400px"),
    ),

    headerSlideWithColumns("Aggregation")
    (
      Enumeration(
        Item.stable("Part-of relationship"),
        Item.stable("Seldom used"),
        Item.stable("No inherited attributes"),
      ),
    )(
      <.img(VdomAttr("data-src") := "./img/erAggregation.png", VdomStyle("max-height") := "400px"),
    ),

    fullscreenImageSlide("./img/erExampel.jpg"),

  )



  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/exercises.jpg", VdomStyle("max-height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage image: http://www.etltechblog.com/2012/09/part-1-data-modeling-overview.html"),
      <.br,
      <.span("Example ER: https://jameseckertgis590ncstate.weebly.com/e-r-diagram.html"),
      <.br,
      <.span("Figures from Database modeling and Design, Fifth edition Logical design")
    )
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
          // chapter4,
          // chapter5,
          // chapter6,
          // chapter7,

          chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}