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
  def rightOf(direction: Direction): Direction = direction match {
    case Direction.UP        => Direction.UPRIGHT
    case Direction.UPRIGHT   => Direction.RIGHT
    case Direction.RIGHT     => Direction.DOWNRIGHT
    case Direction.DOWNRIGHT => Direction.DOWN
    case Direction.DOWN      => Direction.DOWNLEFT
    case Direction.DOWNLEFT  => Direction.LEFT
    case Direction.LEFT      => Direction.UPLEFT
    case Direction.UPLEFT    => Direction.UP
  }

  def leftOf(direction: Direction): Direction = direction match {
    case Direction.UP        => Direction.UPLEFT
    case Direction.UPRIGHT   => Direction.UP
    case Direction.RIGHT     => Direction.UPRIGHT
    case Direction.DOWNRIGHT => Direction.RIGHT
    case Direction.DOWN      => Direction.DOWNRIGHT
    case Direction.DOWNLEFT  => Direction.DOWN
    case Direction.LEFT      => Direction.DOWNLEFT
    case Direction.UPLEFT    => Direction.LEFT
  }