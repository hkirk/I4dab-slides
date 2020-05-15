
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
        Item.stable("UnitOfWork"),
        Item.stable("Tracing"),
        Item.stable("Create / Update"),
      ),
    ),
  )

  val chapter2 = chapter(
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
      <.span("Frontpage meme: https://xkcd.com/844/"),
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
          // chapter3,
          // chapter4,
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