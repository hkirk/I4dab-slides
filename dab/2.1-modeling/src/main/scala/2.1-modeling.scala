
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
      <.h4("Design principles"),
      <.img(VdomAttr("data-src") := "./img/database-meme.gif", ^.cls := "headerMeme"),
    ),

    // TODO agenda
  )

  val chapter2 = chapter(
    headerSlide(
      "Requirements",
      Enumeration(
        Item.stable("Time and labor consuming"),
        Item.stable("Knowledge from end users of database"),
        Enumeration(
          Item.stable("Outline requirements for basis elements"),
          Item.stable("Describe information about elements and relationship"),
          Item.stable("Determine transactions"),
          Item.stable("Define non-functional requirements"),
          Item.stable("Specify technical constraints"),
        ),
        Item.stable("Should result in system description"),
      ),
    ),

    headerSlide(
      "Project description",
      <.div(VdomStyle("font-size") := "28px", VdomStyle("text-align") := "left",
        <.p("A car rental company rents cars to customers. The company owns several cars. Each car has a brand, model name, production year, mileage, color, and so on. Cars are divided into different categories: small, mid-size, large, limousines."),
        <.p("The company has many locations where you can rent a car. The rental locations are located in different cities throughout the country. There can be more than one company location in a city."),
        <.p("Anyone over 21 who has a valid driver’s license can rent a car. Customers under 25 or over 75 years pay different (higher) charges then other customers."),
        <.p("Before renting a car, a customer usually makes a reservation for a car. A customer specifies the dates when the car will be rented, the pick-up location, the drop-off location, and the category of car he wants to rent. A customer may specify, that he wants some extra equipment in the car, for example a GPS, a car seat for a child, etc."),
        <.p("When a customer rents a car, he declares the pick-up and drop-off location, and the drop-off date. The customer can buy various types of insurance. He can also decide that he doesn’t need insurance because the insurance is covered otherwise, for example by his credit card company. The customer can choose additional options such as the possibility of an early drop-off, various refueling options, etc."),
        <.p("The customer pays the charges when he returns the car."),
      )
    ),
  )

  val chapter3 = chapter(
    headerSlide("Design Process",
      <.img(VdomAttr("data-src") := "./img/Steps-of-the-Engineering-Design-Process.png", VdomStyle("max-height") := "700px"),
    ),

    headerSlide("1. Entities and attributes",
      Enumeration(
        Item.stable("Find entities and attributes from project description"),
        Enumeration(
          Item.stable("Entity should contain descriptive information"),
          Item.stable("Multivalued attributes should be modeled as entities"),
          Item.stable("Attributes are properties associated with primary entity"),
          Item.stable("Attributes should be attached to ‘most relevant’ entity"),
        ),
        Item.stable("Not only real world objects"),
      ),
    ),

    headerSlide("Nouns",
      <.div(VdomStyle("font-size") := "28px", VdomStyle("text-align") := "left",
        <.span("A "), <.b("car"), <.span(" rental "), <.b("company"), <.span(" rents "), <.b("cars"), <.span(" to "), <.b("customers"), <.span(". The "), <.b("company"), <.span(" owns several "), <.b("cars"), <.span(". Each "), <.b("car"), <.span(" has a "), <.b("brand"), <.span(", "), <.b("model name"), <.span(", "), <.b("production year"), <.span(", "), <.b("mileage"), <.span(", "), <.b("color"), <.span(", and so on. "), <.b("Cars"), <.span(" are divided into different "), <.b("categories"), <.span(": small, mid-size, large, limousines."),
        <.br,
        <.span("The "), <.b("company"), <.span(" has many "), <.b("locations"), <.span(" where you can rent a "), <.b("car"), <.span(". The rental "), <.b("locations"), <.span(" are located in different "), <.b("cities"), <.span(" throughout the "), <.b("country"), <.span(". There can be more than one "), <.b("company location"), <.span(" in a "), <.b("city"), <.span("."),
        <.br,
        <.span("Anyone over 21 who has a valid "), <.b("driver’s license"), <.span(" can rent a "), <.b("car"), <.span(". "), <.b("Customers"), <.span(" under 25 or over 75 years pay different (higher) "), <.b("charges"), <.span(" then other "), <.b("customers"), <.b("."),
      )
    ),

    headerSlide("First take on entities",
      <.img(VdomAttr("data-src") := "./img/entities1.png", VdomStyle("max-height") := "700px"),
    ),

    headerSlide("2. Generalization Hierarchies",
      Enumeration(
        Item.stable("Put identifier and generic descriptors in supertype"),
        Item.stable("Subtype should have same identifier"),
      ),
    ),

    headerSlide("Second take on entities",
      <.img(VdomAttr("data-src") := "./img/entities2.png", VdomStyle("max-height") := "700px"),
    ),


    headerSlide("3. Relationship",
      Enumeration(
        Item.stable("Determine relationship from project description"),
        Item.stable("Determine"),
        Enumeration(
          Item.stable("relationship degree"), // TODO cardinality?
          Item.stable("connectivity"),
          Item.stable("existence (optional / mandatory)"),
          Item.stable("attributes associated with relationship"),
        ),
        Item.stable("Make sure not to introduces redundant relationship"),
        Item.stable("More relationship can exists between entities"),
      ),
    ),

    headerSlide("Relationship example",
      <.img(VdomAttr("data-src") := "./img/relations.png", VdomStyle("max-height") := "700px"),
    ),

    headerSlide("4. Exemplify",
      Enumeration(
        Item.stable("Given our entities, attributes and relationship"),
        Enumeration(
          Item.stable("Add example data"),
        ),
        Item.stable("Design is iterative"),
        Enumeration(
          Item.stable("So compare schema with description"),
          Item.stable("Do not make assumptions"),
          Item.stable("Ask questions"),
        ),
        Item.stable("There is not one correct design"),
      ),
    ),

    
    headerSlideWithColumns("5. Schema validation")
      (OrderedList(
        Item.stable("Identifying conflicts in schema"),
        OrderedList.withType("alphaList",
          Item.stable("Synonyms / homonyms"),
          Item.stable("Structural conflicts"),
          Item.stable("Keys"),
          Item.stable("Dependencies"),
        ),
        Item.stable("Conformation of schema"),
        Item.stable("Merging and restructuring"),
        OrderedList.withType("alphaList",
          Item.stable("Completeness"),
          Item.stable("Minimality"),
          Item.stable("Understandability"),
        ),
      ),
      )(
        <.img(VdomAttr("data-src") := "./img/bad_database.jpg", VdomStyle("max-height") := "600px"),
      ),

  )

  val chapter4 = chapter(
    // headerSlide("Design Principles"
    // ),

    headerSlide("Design Principles 1/2",
      Enumeration(
        Item.stable("Faithfulness"),
        Enumeration(
          Item.stable("Design should be faithful to specifications"),
          Item.stable("Attributes should reflect reality"),
        ),
        Item.stable("Avoid redundancies"),
        Enumeration(
          Item.stable("Try to model things only once"),
          Item.stable("Same truth in multiple places leads to"),
          Enumeration(
            Item.stable("Updates in multiple places"),
            Item.stable("Unnecessary space used"),
          ),
        ),
      ),
    ),

    headerSlide("Design Principles 2/2",
      Enumeration(
        Item.stable("Simplicity"),
        Enumeration(
          Item.stable("Don’t introduce more elements than needed"),
        ),
        Item.stable("Choosing right relationships"),
        Enumeration(
          Item.stable("Entity can be connected in various ways – only add needed relationships"),
        ),
        Item.stable("Element types"),
        Enumeration(
          Item.stable("Use attributes when this makes sense etc."),
          Item.stable("Attributes are simpler to implement than entities"),
        ),
      ),
    ),
  )


  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/database_design.jpg", VdomStyle("max-height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage meme: https://dilbert.com/strip/1996-02-28"),
      <.br,
      <.span("Design process: https://www.researchgate.net/figure/Steps-of-the-Engineering-Design-Process_fig1_333998494"),
      <.br, 
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
          chapter4,
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