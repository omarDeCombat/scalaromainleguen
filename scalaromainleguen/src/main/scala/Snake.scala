package scala

final case class Snake(body: List[(Double, Double)]) {
  
  def hasBitOwnTail(x: Double, y: Double): Boolean = body.contains((x, y))
  
}
