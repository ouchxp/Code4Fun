package me.ouchxp.tool

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object PrintExpr {
  def printExpr(expr: Any): Unit =
  macro printExprMacro

  def printExprMacro(c: blackbox.Context)(expr: c.Tree): c.universe.Tree = {
    import c.universe._
    val code: String = showCode(expr)
    q"""println($code  + " -> " + $expr)"""
  }
}

