package scala

import scala.util.Random

final case class Food(x: Int, y: Int)

object Food {

  def random(numberOfCells: Int, cellSize: Int): Food =
    Food(Random.nextInt(numberOfCells) * cellSize, Random.nextInt(numberOfCells) * cellSize)

}
