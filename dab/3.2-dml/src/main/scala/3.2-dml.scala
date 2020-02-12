
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
      <.h4("Domain Manipulation language"),
      <.img(VdomAttr("data-src") := "./img/all-your-data.png", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Insert"),
        Item.stable("Select"),
        Item.stable("Delete"),
        Item.stable("Update"),
      ),      
    ),
  )

  val chapter2 = chapter(
    headerSlide("Relational Algebra",
      <.img(VdomAttr("data-src") := "./img/relational_algebra.png", VdomStyle("height") := "400px"),
    ),

    headerSlideWithColumns("Math is fun")
    (
      <.img(VdomAttr("data-src") := "./img/math.jpg", VdomStyle("height") := "400px"),
    )
    (
      <.img(VdomAttr("data-src") := "./img/database-systems.jpeg", VdomStyle("height") := "400px"),
    ),

    headerSlide("INSERT documentation",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap", ^.cls := "text-block",
        <.b("INSERT"), <.span(" {"), <.br,
        <.span("  [ "), <.b("TOP"), <.span(" ( expression ) [ PERCENT ] ]"), <.br, 
        <.span("  [ "), <.b("INTO"), <.span(" ]"), <.br, 
        <.span("  { <object> | rowset_function_limited"), <.br,
        <.span("    [ WITH ( <Table_Hint_Limited> [ ...n ] ) ]"), <.br,
        <.span("  }"), <.br,
        <.span("{   [ ( column_list ) ]"), <.br, 
        <.span("  [ <OUTPUT Clause> ]"), <.br,
        <.span("  { "), <.b("VALUES"), <.span(" ( { DEFAULT | NULL | expression } [ ,...n ] ) [ ,...n     ]"), <.br,
        <.span("  | derived_table"), <.br, 
        <.span("  | execute_statement"), <.br,
        <.span("  | <dml_table_source>"), <.br,
        <.span("  | DEFAULT VALUES "), <.br,
        <.span("} } }"), <.br,
      )
    ),

    headerSlide("SQL syntax", 
      Enumeration(
        Item.stable("UPPERCASE – keyword"),
        Item.stable("Italic – arguments"),
        Item.stable("Bold – names (table, database ..)"),
        Enumeration(
          Item.stable("On slides bold is focus"),
        ),
        Item.stable("[,…n], […n] – Item can be repeated (with comma or without)"),
        Item.stable("[] – optional"),
        Item.stable("{} – required"),
        Item.stable("<block> – block syntax"),
      ),
    ),

    headerSlide("Insert",
      Enumeration(
        Item.stable(<.b("TOP")),
        Enumeration(
          Item.stable(
            <.span("Limits the rows returned in a query result set to a specified number of rows or percentage of rows in SQL Server 2017. When you use TOP with the ORDER BY clause, the result set is limited to the first N number of ordered rows. Otherwise, "),
            <.b("TOP returns the first N number of rows in an undefined order"),
            <.span(". Use this clause to specify the number of rows returned from a SELECT statement."),
            <.b("Or, use TOP to specify the rows affected by an INSERT, UPDATE, MERGE, or DELETE statement (random rows)"),
            <.span(".")
          ),
        ),
      ),
    ),

    headerSlide("INSERT documentation",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap",
        <.span("<SELECT statement> ::="), <.br,
        <.span("  [WITH <common_table_expression> [,...n]]"), <.br,
        <.b("  <query_expression> "), <.br,
        <.span("  [ ORDER BY { order_by_expression | column_position [ ASC | DESC ] } "), <.br,
        <.span("[ ,...n ] ]"), <.br,
        <.span("  [ <FOR Clause>]"), <.br,
        <.span("  [ OPTION ( <query_hint> [ ,...n ] ) ]"), <.br,
        <.span("<query_expression> ::="), <.br,
        <.span("  { <query_specification> | ( <query_expression> ) }"), <.br,
        <.span("  [  { UNION [ ALL ] | EXCEPT | INTERSECT }"), <.br,
        <.span("      <query_specification> | ( <query_expression> ) [...n ] ]"), <.br,
      ),
    ),

    headerSlide("<query_specification>",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap",
        <.span("<query_specification> ::= "), <.b("SELECT"), <.span(" [ ALL | DISTINCT ]"), <.br,
        <.span("  [TOP ( expression ) [PERCENT] [ WITH TIES ] ]"), <.br,
        <.span("  < select_list >"), <.br,
        <.span("  [ INTO new_table ]"), <.br,
        <.span("  [ "), <.b("FROM"), <.span(" { <table_source> } [ ,...n ] ]"), <.br,
        <.span("  [ "), <.b("WHERE"), <.span(" <search_condition> ]"), <.br,
        <.span("  [ <GROUP BY> ]"), <.br,
        <.span("  [ HAVING < search_condition > ]"), <.br,
      ),
    ),

    headerSlide("Deep breath",
      <.img(VdomAttr("data-src") := "./img/breathe.webp", VdomStyle("height") := "600px"),
    ),

  )



  val chapter3 = chapter(

    headerSlide("Delete", 
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap",
        <.span("[ WITH <common_table_expression> [ ,...n ] ]"), <.br,
        <.b("DELETE"), 
        <.span("  [ TOP ( expression ) [ PERCENT ] ]"), <.br, 
        <.span("  [ FROM ]"), <.br,
        <.span("  { { table_alias | <object>  | rowset_function_limited [ WITH ( table_hint_limited [ ...n ] ) ] }  | @table_variable }"), <.br,
        <.span("  [ <OUTPUT Clause> ]"), <.br,
        <.span("  [ "), <.b("FROM"), <.span(" table_source [ ,...n ] ] "), <.br,
        <.span("  [ "), <.b("WHERE"), <.span(" { <search_condition>"), <.br,
        <.span("      | { [ CURRENT OF"), <.br,
        <.span("             { { [ GLOBAL ] cursor_name }"), <.br,
        <.span("                 | cursor_variable_name"), <.br,
        <.span("             } ] } }  ]"), <.br,
        <.span("  [ OPTION ( <Query Hint> [ ,...n ] ) ]"), <.br,
        <.span("[; ]"), <.br,
      ),
    ),

    headerSlide("<search_condition>",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap",
        <.span("<search_condition> ::="), <.br,
        <.span("  { [ NOT ] <predicate> | ( <search_condition> ) }"), <.br,
        <.span("  [ { AND | OR } [ NOT ] { <predicate> | ( <search_condition> ) } ]"), <.br,
        <.span("[ ,...n ]"), <.br,
      ),
    ),

    headerSlide("<predicate>",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap",
      <.span("<predicate> ::="), <.br,
        <.span("    { expression { = | < > | ! = | > | > = | ! > | < | < = | ! < } expression "), <.br,
        <.span("    | string_expression [ NOT ] LIKE string_expression"), <.br,
        <.span("  [ ESCAPE 'escape_character' ]"), <.br,
        <.span("    | expression [ NOT ] BETWEEN expression AND expression"), <.br,
        <.span("    | expression IS [ NOT ] NULL"), <.br,
        <.span("    | CONTAINS"), <.br,
        <.span("  ( { column | * } , '<contains_search_condition>' )"), <.br,
        <.span("    | FREETEXT ( { column | * } , 'freetext_string' )"), <.br,
        <.span("    | expression [ NOT ] IN ( subquery | expression [ ,...n ] )"), <.br,
        <.span("    | expression { = | < > | ! = | > | > = | ! > | < | < = | ! < }"), <.br,
        <.span("  { ALL | SOME | ANY} ( subquery )"), <.br,
        <.span("    | EXISTS ( subquery )     }"), <.br,
      ),
    ),

    headerSlide("Update",
      <.p(^.textAlign := "left", ^.whiteSpace := "pre-wrap", ^.fontSize := "38px",
        <.b("UPDATE"), <.br,
        <.span("  [ TOP ( expression ) [ PERCENT ] ]"), <.br,
        <.span("  { { table_alias | <object> | rowset_function_limited"), <.br,
        <.span("       [ WITH ( <Table_Hint_Limited> [ ...n ] ) ]"), <.br,
        <.span("    } | @table_variable }"), <.br,
        <.b("  SET"), <.br,
        <.span("      { column_name = { expression | DEFAULT | NULL }"), <.br,
        <.span("       "), <.b(<.i(<.u("CUT_OUT"))), <.br,
        <.span("  [ <OUTPUT Clause> ]"), <.br,
        <.span("  [ FROM{ <table_source> } [ ,...n ] ]"), <.br,
        <.span("  [ "), <.b("WHERE"), <.span(" { <search_condition>"), <.br,
        <.span("          | { [ CURRENT OF"), <.br,
        <.span("                { { [ GLOBAL ] cursor_name }"), <.br,
        <.span("                    | cursor_variable_name  } ]  }  }  ]"), <.br,
        <.span("  [ OPTION ( <query_hint> [ ,...n ] ) ]"), <.br,
      )
    ),
  )

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/exercise.jpg", VdomStyle("height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage meme: https://rtask.thinkr.fr/the-ten-commandments-for-a-well-formatted-database/"),
      <.br,
      <.span("Exercise gif: https://giphy.com/gifs/13HgwGsXF0aiGY/media"),
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