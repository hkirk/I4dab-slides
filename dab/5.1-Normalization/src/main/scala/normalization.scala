
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Modeling {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("Normalization"),
      <.br,
      <.h4("1st-, 2nd-, 3rd-, 4th-, & BC-NF"),
      <.img(VdomAttr("data-src") := "./img/jedi.jpeg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Why Normalize"),
        Item.stable("Functional Dependency"),
        Item.stable("Boyce and Codd Normal Form"),
        Item.stable("3rd Normal Form"),
        Item.stable("4th Normal Form"),
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
    headerSlide("Anomalies",
      Enumeration(
        Item.stable(<.b("Redundancy"), <.br,
            <.span("Information may be repeated unnecessarily in several tubles"),
        ),
        Item.stable(<.b("Update anomalies"), <.br,
          <.span("We change information in one tuble, but the same information is left in another tuble"),
        ),
        Item.stable(<.b("Deletion anomalies"), <.br,
          <.span("If we delete data we may lose information as a side effect"),
        ),
      ),
    ),

    headerSlide("Movies1 table",
      firstMovieTable,
    ),
  )

  val chapter3 = chapter(
    headerSlide("Functional dependency",
      <.p(^.textAlign := "left",
        <.span("If two tuples of R agree on all attributes "), getOneThroughN("a"), <.span(", then they must also agree on another list of attributes "), getOneThroughN("b"), <.span("."), <.br,
        <.span("Formally"), 
        <.div(^.textAlign := "center",
          getOneThroughN("a"), <.span(" → "), getOneThroughN("b"), <.br,
        ),
        <.span("Reads: "),
        <.div(^.textAlign := "center",
          getOneThroughN("a"), <.span(" functional determines "), getOneThroughN("b"),
        )
      )
    ),

    headerSlide("FD examples", 
      <.p(^.textAlign := "left",
        <.span("Example:"),
        <.div(^.textAlign := "center",
          <.span("Movies1(title, year, length, genre, studioName, starName)"), <.br, <.br,
        ),
        fadeInFragment(
          <.b("Claim 1: "), 
          <.div(^.textAlign := "center",
            <.span("title, year → length, genre, studioName")
          ),
        ),
        fadeInFragment(
          <.span("Expect that no movies with same title are made in the same year"), <.br, // TODO more precise
        ),
        fadeInFragment(
          <.b("Claim 2: "),
          <.div(^.textAlign := "center",
            <.span("title, year → starName")
          ),
        ),
        fadeInFragment(
          <.span("False - more than one star for a particular movie"), <.br,  // TODO more precise
        ),
      )
    ),

    headerSlideLeftAligned("Keys", // TODO include notes
      <.span("We can say than 1...n of attributes { "), getOneThroughN("a"), <.span(" } is a key for a relation R if"), <.br,
      OrderedList(
        Item.stable("These attributes functional determine all other attributes in R"),
        Item.stable(<.span("No proper subset of { "), getOneThroughN("a"), <.span(" } functional determines all other attributes in R")),
      ),
      <.span("Example:"),
      <.div(^.textAlign := "center",
        <.span("Attributes {title, year, starName} forms a key for Movies1")
      ),
    ),

    headerSlideLeftAligned("Multiple keys",// TODO include notes
      <.span("A set of attributes that contains a key is called a superkey (superset of a key)"), <.br,
      <.span("A relation can hold multiple superkeys"), <.br,
      <.span("Example"),
      <.div(^.textAlign := "center",
        <.span("{title, year, starName} and {title, year, length, studioName, starName}")
      ),
    ),

    headerSlideLeftAligned("Decomposing Relations",
      <.span("To eliminate anomalies in relations, we can decompose them."), <.br,
      <.span("Given a relation R("), getOneThroughN("a"), <.span(") we may decompose into two relations S("), getOneThroughX("b", "m"), <.span(") and T("), getOneThroughX("c", "k"), <.span(") such that"), <.br,
      OrderedList(
        Item.stable(<.span("{"), getOneThroughN("a"), <.span("} = {"), getOneThroughX("b", "m"), <.span("} ∪ {"), getOneThroughX("c", "k"), <.span("}")),
        Item.stable("Attributes in S and T may overlap")
      ),
      fadeInFragment(
        <.span("Movies1(title, year, length, genre, studioName, starName) can be decomposed into"),
        <.div(^.textAlign := "center",
          <.span("Movies2(title, year, length, genre, studioName) and"), <.br,
          <.span("Stars(title, year, starName)"),
        ),
      )
    ),
  )

  val chapter4 = chapter(
    headerSlide("Normal forms",
      <.img(VdomAttr("data-src") := "./img/normalforms.png", VdomStyle("height") := "600px"),
    ),

    headerSlideLeftAligned("Boyce-Codd Normal Form (BCNF)",
      // TODO
    )
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