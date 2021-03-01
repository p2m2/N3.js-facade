/**
 * olivier.filangi@inrae.fr - P2M2 Platform - https://github.com/p2m2
 */
package com.github.p2m2.facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.language.implicitConversions

//http://rdf.js.org/data-model-spec/

@js.native
@JSImport("n3", JSImport.Default)
object N3 extends js.Object {
  type Parser = N3Parser
  type Writer = N3Writer
  type Store = N3Store
}

@js.native
@JSImport("n3", "DataFactory")
object DataFactory extends js.Object {
  def quad( s: Term, p: Term, o: Term, g: Term = null): Quad = js.native
  def namedNode( s:String ) : NamedNode = js.native
  def literal( v:String, p: String="") : Literal = js.native
  def defaultGraph() : DefaultGraph = js.native
}

@js.native
@JSImport("n3", "Parser")
class N3Parser( options : js.Object = null) extends js.Object {
  def parse( input : String , callback : js.Function3[String  , js.UndefOr[Quad], js.UndefOr[js.Object],Unit] = null) : js.Object = js.native
}

@js.native
@JSImport("n3", "Writer")
class N3Writer( options : js.Object = null) extends js.Object {
  def addQuad(quad : Quad) : Unit = js.native

  def blank() : Term = js.native
  def blank(predicate : Term, `object` : Term) : Term = js.native
  def blank(obj : js.Object  ) : Term = js.native
  def blank(listObj : js.Array[js.Object] , `object` : Term = null  ) : Term = js.native

  def list[T<:Term](listObj : js.Array[T] ) : Term = js.native

  def end (done : js.Function2[String,String,Unit]) : Unit = js.native
}

@js.native
@JSImport("n3", "Store")
class N3Store( options : js.Object = null) extends js.Object {

  def addQuad(quad : Quad) : Unit = js.native
  def addQuads(quads : Quad*) : Unit = js.native
  def removeQuad(quad : Quad) : Unit = js.native
  def removeQuads(quads : Quad*) : Unit = js.native
  def removeMatches(quad : Quad) : Unit = js.native
  def deleteGraph(graph : NamedNode) : Unit = js.native
  def createBlankNode(suggestedName:String) : Unit = js.native


  def getQuads(s: Term, p: Term, o: Term, g: Term=null) : js.Array[Quad] = js.native
  //todo : return stream
  //def `match`(s: Term, p: Term, o: Term, g: Term=null) : js.Array[Quad] = js.native

  def countQuads(s: Term, p: Term, o: Term, g: Term=null) : Int = js.native

  def forEach(callback : js.Function1[Quad,Unit],s: Term, p: Term, o: Term, g: Term=null) : Unit = js.native
  def every(callback : js.Function1[Quad,Boolean],s: Term, p: Term, o: Term, g: Term=null) : Boolean = js.native
  def some(callback : js.Function1[Quad,Boolean],s: Term, p: Term, o: Term, g: Term=null) : Boolean = js.native

  def getSubjects(predicate : Term, `object` : Term, graph : Term) : Quad = js.native
  def forSubjects(callback : js.Function1[Quad,Unit],predicate : Term, `object` : Term, graph : Term) : Quad = js.native
  def getPredicates(subject : Term, `object` : Term, graph : Term) : Quad = js.native
  def getObjects(subject : Term, predicate : Term, graph : Term) : Quad = js.native
  def forObjects(callback : js.Function1[Quad,Unit],subject : Term, predicate : Term, graph : Term) : Quad = js.native
  def getGraphs(subject : Term, predicate : Term, `object` : Term) : Quad = js.native
  def forGraphs(callback : js.Function1[Quad,Unit],subject : Term, predicate : Term, `object` : Term) : Quad = js.native

}

@js.native
@JSImport("n3", "Term")
abstract class Term extends js.Object {
  val termType : String = js.native
  val value : String = js.native
}

@js.native
@JSImport("n3", "Quad")
class Quad(val subject : Term, val predicate : Term, val `object` : Term, val graph : Term = null) extends Term

object Quad {
  implicit def quadrupletNameNode2Quad(quad : (Term,Term,Term,Term)) : Quad = DataFactory.quad(quad._1,quad._2,quad._3,quad._4)
  implicit def quadrupletNameNode2Quad(quad : (Term,Term,Term)) : Quad = DataFactory.quad(quad._1,quad._2,quad._3,null)
  implicit def quad2namedNodeQuadruplet(quad : Quad ) : (Term,Term,Term,Term) = (quad.subject,quad.predicate,quad.`object`,quad.graph)

  implicit def quad2String( quad : Quad ) : String =
    "("+ quad.subject + "," + quad.predicate + quad.`object` + "," + quad.graph + ")"
  implicit def term2String( term : Term ) : String = term.value +"#"+term.termType
}

@js.native
@JSImport("n3", "Triple")
class Triple(val subject : Term, val predicate : Term, val `object` : Term, val graph : Term = null) extends Term

@js.native
@JSImport("n3", "BlankNode")
class BlankNode(val name: String = "") extends Term

@js.native
@JSImport("n3", "NamedNode")
class NamedNode(val name: String = "") extends Term

@js.native
@JSImport("n3", "Literal")
class Literal(val name: String = "",val datatype : NamedNode, val language : String = "") extends Term

@js.native
@JSImport("n3", "DefaultGraph")
class DefaultGraph extends Term

@js.native
@JSImport("n3", "Variable")
class Variable extends Term