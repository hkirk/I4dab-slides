
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
      <.h2("SQL"),
      <.br,
      <.h4("Joins & Views"),
      <.img(VdomAttr("data-src") := "./img/sql-timing-out.jpg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Joining"),
        Item.stable("Views")
      ),      
    ),
  )

  val caseMoviesTable = <.table(
    <.thead(
      <.tr(
        <.th("title"),
        <.th("..."),
        <.th("drama"),
        <.th("scifi"),
        <.th("comedy"),
        <.th("cartoon"),
        <.th("..."),
        <.th("category"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Star Wars"),
        <.td(""),
        <.td("T"),
        <.td("T"),
        <.td("F"),
        <.td("F"),
        <.td(""),
        <.td("?"),
      ),
      <.tr(
        <.td("Monsters Inc"),
        <.td(""),
        <.td("F"),
        <.td("F"),
        <.td("T"),
        <.td("T"),
        <.td(""),
        <.td("?"),
      ),
      <.tr(
        <.td("Casablanca"),
        <.td(""),
        <.td("T"),
        <.td("F"),
        <.td("F"),
        <.td("F"),
        <.td(""),
        <.td("?"),
      ),

    ),
  )

  val chapter2 = chapter(
    fullscreenImageSlide("./img/joins.jpg"),

    headerSlide("Documentation",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap", ^.cls := "text-block",
        <.span("""
        |<joined_table> ::=   
        |  {  
        |      <table_source> <join_type> <table_source> ON <search_condition>   
        |      | <table_source> CROSS JOIN <table_source>   
        |      | left_table_source { CROSS | OUTER } APPLY right_table_source   
        |      | [ ( ] <joined_table> [ ) ]   
        |  }  
        |  <join_type> ::=   
        |      [ { INNER | { { LEFT | RIGHT | FULL } [ OUTER ] } } [ <join_hint> ] ]  JOIN
        |""".stripMargin),
      )
    ),

    headerSlide("Example tables",
      sql("""CREATE TABLE customer(
            |  id           INT PRIMARY KEY,
            |  name         NVARCHAR(255),
            |  country      NVARCHAR(255));""".stripMargin),
      sql("""CREATE TABLE order_table(
            |  id           INT PRIMARY KEY,
            |  customer_id  INT,
            |  value        INT,
            |  order_date   DATETIME DEFAULT getdate());""".stripMargin),
      sql("""INSERT INTO customer VALUES
            | (1, 'Hans Hansen', 'Denmark'), (2, 'Boris Johnson', 'Great Britan'),
            | (3, 'Gunter von Halden', 'Germany'), (5, 'Will Smidt', 'USA');""".stripMargin),
      sql("""INSERT INTO order_table (id, value, customer_id) VALUES
            | (1, 10, 1), (2, 20, 2), (3, 30, 2), (4, 24, 2),
            | (5, 100, 3), (6, 14, 3), (7,12, 12);""".stripMargin),
    ),
  )

  val chapter3 = chapter(
    headerSlide("Inner join",
      Enumeration(
        Item.stable(<.b("JOIN"), <.span(" or "), <.b("INNER JOIN")),
      ),
      sql("""SELECT o.id, c.name, o.order_date 
            |FROM order_table AS o
            |INNER JOIN customer AS c ON o.customer_id = c.id""".stripMargin),
      <.div(^.cls := "container",
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/inner_join_template.png", VdomStyle("height") := "200px"),
        ),
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/inner_join.png", VdomStyle("maxHeight") := "400px"),
        ),
      ),
    ),

    headerSlide("Left join",
      Enumeration(
        Item.stable("Fetches data present in left table in combination with data from right table"),
      ),
      sql("""SELECT o.id, c.name, o.order_date 
            |FROM order_table AS o
            |LEFT JOIN customer AS c ON o.customer_id = c.id""".stripMargin),
      <.div(^.cls := "container",
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/left_join_template.png", VdomStyle("height") := "200px"),
        ),
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/left_join.png", VdomStyle("maxHeight") := "400px"),
        ),
      ),
    ),

    headerSlide("Right join",
      Enumeration(
        Item.stable("Fetches data present in right table in combination with data from left table"),
      ),
      sql("""SELECT o.id, c.name, o.order_date 
            |FROM order_table AS o
            |RIGHT JOIN customer AS c ON o.customer_id = c.id""".stripMargin),
      <.div(^.cls := "container",
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/right_join_template.png", VdomStyle("height") := "200px"),
        ),
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/right_join.png", VdomStyle("maxHeight") := "400px"),
        ),
      ),
    ),

    headerSlide("Full join",
      Enumeration(
        Item.stable("Fetches data from both tables"),
      ),
      sql("""SELECT o.id, c.name, o.order_date 
            |FROM order_table AS o
            |FULL JOIN customer AS c ON o.customer_id = c.id""".stripMargin),
      <.div(^.cls := "container",
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/full_join_template.png", VdomStyle("height") := "200px"),
        ),
        <.div(^.cls := "col",
          <.img(VdomAttr("data-src") := "./img/full_join.png", VdomStyle("maxHeight") := "400px"),
        ),
      ),
    ),

    headerSlide("Cross Join",
      Enumeration(
        Item.stable("Cartesian Product*"),
        Enumeration(
          Item.stable("A × B = { (a, b) | a ∈ A, b ∈ B }"),
          Item.stable("{A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2}"),
          Item.stable("{♠, ♥, ♦, ♣} ")
        ),
      ),
      <.span("*"), <.a(^.href:="https://en.wikipedia.org/wiki/Cartesian_product", "https://en.wikipedia.org/wiki/Cartesian_product"),
    ),

    headerSlide("Cross join", 
      <.img(VdomAttr("data-src") := "./img/Piatnikcards.jpg", VdomStyle("maxHeight") := "500px"),
    ),
  )

  val chapter4 = chapter(
    headerSlide("Combining relation",
      Enumeration(
        Item.stable("INTERSECT"),
        Item.stable("UNION"),
        Item.stable("EXCEPT"),
      ),

      sql("""(SELECT … FROM .. WHERE)
            |  INTERSECT | EXCEPT | UNION
            |(SELECT … FROM .. WHERE)""".stripMargin),
    ),
  )

  val chapter5 = chapter(
    headerSlide("Views",
      <.span("Create a View"), <.br,
      sql("""CREATE VIEW RecordSongs AS
            |  SELECT r.name, r.year, s.text, s.length
            |  FROM Records AS r LEFT JOIN song AS s 
            |  ON r.id = s.albumId""".stripMargin),
      <.br,
      <.span("Using a view - like a table"), <.br,
      sql("SELECT * FROM RecordSongs"),
    ),
  )

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/make-homework-fun.jpg", VdomStyle("maxHeight") := "600px"),
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