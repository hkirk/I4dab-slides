
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
      <.h2("Relational DB vs. NoSQL"),
      <.br,
      <.h4("How to choose"),
    ),

    headerSlide("Relational database",
      Enumeration(
        Item.stable("+ Mainstream"),
        Item.stable("+ Better support, tools, addons"),
        Item.stable("+ Atomicity"),
        Item.stable("- Atomicity - performance"),
        Item.stable("- Scalability vertically"),
      ),
    ),

    headerSlide("NoSQL",
      <.p("Types: Key-value store, Column store, Document database, Graph database"),
      Enumeration(
        Item.stable("+ Scalability - horizontal"),
        Item.stable("+ Flexibility"),
        Item.stable("+ Low resource devices"),
        Item.stable("+ Query optimization for large scala datasets"),

        Item.stable("- Atomicity"),
        Item.stable("- SQL compatibility"),
        Item.stable("- Standardizing"),
        Item.stable("- Cross platform support"),
        Item.stable("- Management tools etc."),
      ),
    ),

    headerSlide("So what to choose",
      Enumeration(
        Item.stable("Data consistency -> RDBMS"),
        Item.stable("Complext queries -> RDBMS"),
        Item.stable("Small devices -> NoSQL"),
        Item.stable("Data-structures can vary -> NoSQL"),
        Item.stable("Large scala data -> NoSQL"),
        Item.stable("Event capturing / processing -> NoSQL"),
      ),
    ),

    headerSlide("Sources",
      Enumeration(
        Item.stable("https://blog.pandorafms.org/nosql-vs-sql-key-differences/"),
        Item.stable("https://www.geeksforgeeks.org/difference-between-sql-and-nosql/"),
        Item.stable("https://www.newgenapps.com/blog/sql-vs-nosql-finding-the-right-dbms-for-your-project"),
        Item.stable("https://www.youtube.com/watch?v=rRoy6I4gKWU"),
      ),
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
          // chapter2,
          // chapter3,
          // chapter4,
          // chapter5,
          // chapter6,
          // chapter7,
          // chapter8,
          // chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}