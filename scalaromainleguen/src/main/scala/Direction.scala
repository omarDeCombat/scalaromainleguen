package scala

import scala.util.Random

enum Direction {
  case UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN, DOWNLEFT, LEFT, UPLEFT
}
object Direction:
  //val values: Array[Direction] = Direction.values
  def random: Direction = values(Random.nextInt(values.length))
  def opposite(direction: Direction): Direction = direction match {
    case Direction.UP        => Direction.DOWN
    case Direction.UPRIGHT   => Direction.DOWNLEFT
    case Direction.RIGHT     => Direction.LEFT
    case Direction.DOWNRIGHT => Direction.UPLEFT
    case Direction.DOWN      => Direction.UP
    case Direction.DOWNLEFT  => Direction.UPRIGHT
    case Direction.LEFT      => Direction.RIGHT
    case Direction.UPLEFT    => Direction.DOWNRIGHT
  }