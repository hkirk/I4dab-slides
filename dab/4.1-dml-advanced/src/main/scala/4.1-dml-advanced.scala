
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
      <.h4("Domain Manipulation language - Advanced"),
      <.img(VdomAttr("data-src") := "./img/nested.jpg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("")
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
    headerSlide("ORDER BY/GROUP BY",
      sql("""
            |SELECT * FROM movies 
            |  ORDER BY title [DESC|ASC]""".stripMargin),
      sql("""
            |SELECT count(title) FROM movies
            |  GROUP BY category""".stripMargin),
      Enumeration(
        Item.stable(<.b("HAVING"), <.span(" can be used for search condition when using "), <.b("GROUP BY")),
      ),
    ),

    headerSlide("TOP",
      sql("""
            |SELECT TOP 10 * FROM movies
            |  ORDER BY rating""".stripMargin),
      sql("""
            |SELECT TOP 10 PERCENT FROM movies""".stripMargin),

      Enumeration(
        Item.stable("Pagination in SQL Server is a bit complicated – will try later"),
        Item.stable("MySQL:"),
      ),

      sql("""SELECT * FROM movies ORDER BY rating LIMIT 10""".stripMargin),
      sql("""SELECT * FROM movies ORDER BY rating
            |  LIMIT 10 OFFSET 11""".stripMargin),
      
    ),

    headerSlide("Search condition",
      Enumeration(
        Item.stable("Equal"), // TODO in last slide set?
      ),
      sql("""SELECT * FROM movies WHERE title = (‘Star Wars’)""".stripMargin),

      Enumeration(
        Item.stable("LIKE"),
      ),
      sql("""SELECT * FROM movies WHERE title LIKE (‘Star%’)""".stripMargin),
      // TODO '%'' Zero, one or multiple chars
      // TODO '_' char

      Enumeration(
        Item.stable("IN"),
      ),
      sql("""SELECT * FROM movies WHERE category
            |  IN (‘action’, ‘drama’, scifi’)""".stripMargin),

      Enumeration(
        Item.stable("BETWEEN"),
      ),
      sql("""SELECT * FROM movies WHERE year
            |  BETWEEN 1920 AND 1940""".stripMargin),

    ),


    headerSlide("CASE (1/2)",
      caseMoviesTable,
      <.span("How to calculate category?"),
      // TODO animate
      sql("""UPDATE movies SET category = 'drama' WHERE drama = 'T'""".stripMargin),
      sql("""UPDATE movies SET category = 'scifi' WHERE scifi = 'T'""".stripMargin),
      sql("""UPDATE movies SET category = 'comedy' WHERE comedy = 'T'""".stripMargin),
      sql("""UPDATE movies SET category = 'cartoon' WHERE cartoon = 'T'""".stripMargin),
    ),

    headerSlide("CASE (2/2)",
      sql("""
            |UPDATE movies SET category =
            |  CASE
            |    WHEN drama = ‘T’    THEN ‘drama’
            |    WHEN scifi = ‘T’    THEN ‘scifi’
            |    WHEN comedy = ‘T’   THEN ‘comedy’
            |    WHEN cartoon = ‘T’  THEN ‘cartoon’
            |    ELSE ‘unknown’
            |  END""".stripMargin)
    ),

    headerSlide("Aggregates",
      sql("SELECT MIN(year) FROM movies"),
      sql("SELECT MAX(year) FROM movies"),
      sql("SELECT COUNT(*) FROM movies"),
      sql("SELECT AVG(rating) FROM movies"),
      sql("SELECT SUM(rating) FROM movies"),
      sql("SELECT DISTINCT(title) FROM movies"),
    ),


  )

  val chapter3 = chapter(
    headerSlide("Sub queries",
      sql("""
            |SELECT m.title, m.avgRating,
            |    (SELECT COUNT(r.id) FROM ratings AS r
            |       WHERE m.id = r.MovieId) AS numRatings
            |  FROM movies AS m
            |""".stripMargin),
      <.span("or"), <.br,
      sql("""
          |SELECT m.title FROM movies AS m
          |   WHERE m.id IN 
          |   (SELECT r.movieID FROM rating AS r)
          |""".stripMargin),
    ),

    headerSlide("Pagination in SQL Server",
      sql("""
            |SELECT * FROM (
            |  SELECT 
            |     ROW_NUMBER() OVER (ORDER BY id) AS rowNum, *
            |  FROM movies) AS rowConstrainedResult
            |WHERE rowNum >= 11 AND rowNum <= 20
            |ORDER BY rowNum
            |""".stripMargin),
    ),

    headerSlide("Transactions",
      Enumeration(
        Item.stable("Treat multiple statements as single unit of work"),
        Item.stable("If transaction is successful"),
        Enumeration(
          Item.stable("all changes are committed,"),
          Item.stable("else all changes are canceled")
        ),
        Item.stable("Autocommit – each statement is a transaction"),
        Item.stable("Explicit – started with BEGIN, ended explicitly"),
        Item.stable("Implicit – started when previous transaction completes and explicitly ended."), // TODO ?????
      ),
    ),

    headerSlide("Transaction example",
      sql("""
            |BEGIN TRY
            |BEGIN TRANSACTION
            |    UPDATE Account SET balance = balance + 100
            |       WHERE accNo = 123
            |    UPDATE Account SET balance = balance - 100
            |       WHERE accNo = 321
            |    -- RAISERROR('Some Random Error',16,1)
            |    COMMIT
            |END TRY
            |BEGIN CATCH
            |    ROLLBACK
            |END CATCH""".stripMargin),
    ),

    headerSlide("When to use transactions", 
      sql("""
          |BEGIN TRANSACTION
          |  UPDATE Account SET balance = balance + 100
          |    WHERE accNo = 123
          |  UPDATE Account SET balance = balance - 100
          |    WHERE accNo = 321
          |COMMIT""".stripMargin),
    ),

    // TODO roolback example

    headerSlide("Explicit rollback",
       sql("ROLLBACK TRANSACTION"),
       Enumeration(
         Item.stable("Can have names and descriptions"),
         Item.stable("Can be nested"),
       ),
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
      <.span("Frontpage meme: https://rtask.thinkr.fr/the-ten-commandments-for-a-well-formatted-database/"),
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