package scala

import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import Direction._
import scala.util.Random
import constant._

final case class WatorState(tunas: Array[Tuna], sharks: Array[Shark]  ) {

  def draw(cellSize: Int): Seq[Rectangle] = {
    val tunaRectangles = tunas.map { tuna =>
      new Rectangle {
        x = tuna.x * cellSize
        y = tuna.y * cellSize
        width = cellSize
        height = cellSize
        fill = tuna.color
      }
    }
    val sharkRectangles = sharks.map { shark =>
      new Rectangle {
        x = shark.x * cellSize
        y = shark.y * cellSize
        width = cellSize
        height = cellSize
        fill = Color.BLUE
      }
    }
    tunaRectangles ++ sharkRectangles
  }

  def move(windowSize: Int, cellSize: Int,sharkBreed: Int,tunaBreed: Int,sharkEnergie:Int): WatorState = {
    val maxCells = windowSize / cellSize
    val maxEntities = maxCells * maxCells

    println("move")

    val tunaSet = tunas.filter(!_.dead).map(tuna => (tuna.x, tuna.y) -> tuna.id).toSet
    val sharkSet = sharks.map(shark => (shark.x, shark.y)).toSet

    val movedSharks = sharks.map { shark =>
      val voisonXY = Array(
        (shark.x - 1, shark.y - 1),
        (shark.x - 1, shark.y),
        (shark.x - 1, shark.y + 1),
        (shark.x, shark.y - 1),
        (shark.x, shark.y + 1),
        (shark.x + 1, shark.y - 1),
        (shark.x + 1, shark.y),
        (shark.x + 1, shark.y + 1)
      )

      val (tunax, tunay, idTuna) = voisonXY.flatMap {
        case (x, y) => tunaSet.collectFirst {
          case ((tx, ty), id) if tx == x && ty == y => (x, y, id)
        }
      }.headOption.getOrElse((0, 0, -1))

      if (idTuna == -1) {
        val (newX, newY) = shark.direction match {
          case UP        => (shark.x, math.max(0, (shark.y - 1)))
          case UPRIGHT   => (math.min(maxCells - 1, shark.x + 1), math.max(0, (shark.y - 1)))
          case RIGHT     => (math.min(maxCells - 1, shark.x + 1), shark.y)
          case DOWNRIGHT => (math.min(maxCells - 1, shark.x + 1), math.min(maxCells - 1, shark.y + 1))
          case DOWN      => (shark.x, math.min(maxCells - 1, shark.y + 1))
          case DOWNLEFT  => (math.max(0, shark.x - 1), math.min(maxCells - 1, shark.y + 1))
          case LEFT      => (math.max(0, shark.x - 1), shark.y)
          case UPLEFT    => (math.max(0, shark.x - 1), math.max(0, (shark.y - 1)))
        }
        shark.copy(x = newX, y = newY)
      } else {
        shark.copy(x = tunax, y = tunay, energy = shark.energy + 35)
      }
    }

    println("end movedSharks")

    val movedTunas = tunas.flatMap { tuna =>
      if (tunaSet.contains((tuna.x, tuna.y) -> tuna.id)) {
        List(tuna.copy(dead = true))
      } else {
        val (newX, newY) = tuna.direction match {
          case UP        => (tuna.x, math.max(0, (tuna.y - 1)))
          case UPRIGHT   => (math.min(maxCells - 1, tuna.x + 1), math.max(0, (tuna.y - 1)))
          case RIGHT     => (math.min(maxCells - 1, tuna.x + 1), tuna.y)
          case DOWNRIGHT => (math.min(maxCells - 1, tuna.x + 1), math.min(maxCells - 1, tuna.y + 1))
          case DOWN      => (tuna.x, math.min(maxCells - 1, tuna.y + 1))
          case DOWNLEFT  => (math.max(0, tuna.x - 1), math.min(maxCells - 1, tuna.y + 1))
          case LEFT      => (math.max(0, tuna.x - 1), tuna.y)
          case UPLEFT    => (math.max(0, tuna.x - 1), math.max(0, (tuna.y - 1)))
        }
        List(tuna.copy(x = newX, y = newY))
      }
    }

    val newState = copy(tunas = movedTunas, sharks = movedSharks)
      .changeDirection(maxCells)
      .sharkEnergie()
      .tunaBaby(maxCells, maxEntities,tunaBreed )
      .sharksBaby(maxCells, maxEntities,sharkBreed , sharkEnergie )

    println(s"Number of sharks: ${newState.sharks.length}, Number of tunas: ${newState.tunas.length}")

    newState
  }

  def changeDirection(maxCells: Int): WatorState = {
    val movedTunas = tunas.map { tuna =>
      Random.nextInt(3) match {
        case 0 => tuna.copy(direction = tuna.direction)
        case 1 => tuna.copy(direction = Direction.rightOf(tuna.direction))
        case 2 => tuna.copy(direction = Direction.leftOf(tuna.direction))
      }
    }

    val movedSharks = sharks.map { shark =>
      Random.nextInt(3) match {
        case 0 => shark.copy(direction = shark.direction)
        case 1 => shark.copy(direction = Direction.rightOf(shark.direction))
        case 2 => shark.copy(direction = Direction.leftOf(shark.direction))
      }
    }

    copy(tunas = movedTunas, sharks = movedSharks)
  }

  def sharkEnergie(): WatorState = {
    val sharksNRJ = sharks.flatMap { shark =>
      if (shark.energy == 0) {
        List()
      } else {
        List(shark.copy(energy = shark.energy - 1))
      }
    }
    copy(sharks = sharksNRJ)
  }

  def tunaBaby(maxCells: Int, maxEntities: Int,tunaBreed:Int): WatorState = {
    val random = new Random
    val allPositions = (tunas.map(tuna => (tuna.x, tuna.y)).toSet ++ sharks.map(shark => (shark.x, shark.y)).toSet)
    val tunasBaby = tunas.flatMap { tuna =>
      if (tuna.breed == 0 && (tunas.length + sharks.length) < maxEntities) {
        val (newX, newY) = generateNearbyPosition(allPositions, random, maxCells, tuna.x, tuna.y)
        List(
          tuna.copy(breed = tunaBreed),
          Tuna(
            id = random.nextInt(1000),
            x = newX,
            y = newY,
            direction = Direction.random,
            breed = tunaBreed,
            dead = false,
            color = Color.Gray
          )
        )
      } else {
        List(tuna.copy(breed = tuna.breed - 1))
      }
    }
    copy(tunas = tunasBaby)
  }

  def sharksBaby(maxCells: Int, maxEntities: Int,sharkBreed:Int,sharkEnergie:Int): WatorState = {
    val random = new Random
    val allPositions = (tunas.map(tuna => (tuna.x, tuna.y)).toSet ++ sharks.map(shark => (shark.x, shark.y)).toSet)
    val sharksBaby = sharks.flatMap { shark =>
      if (shark.breed == 0 && (tunas.length + sharks.length) < maxEntities) {
        val (newX, newY) = generateNearbyPosition(allPositions, random, maxCells, shark.x, shark.y)
        List(
          shark.copy(breed = sharkBreed),
          Shark(
            id = random.nextInt(1000),
            x = newX,
            y = newY,
            direction = Direction.random,
            breed = sharkBreed,
            energy = sharkEnergie
          )
        )
      } else {
        List(shark.copy(breed = shark.breed - 1))
      }
    }
    copy(sharks = sharksBaby)
  }

  def generateNearbyPosition(occupiedPositions: Set[(Int, Int)], random: Random, maxCells: Int, parentX: Int, parentY: Int): (Int, Int) = {
    val directions = Seq(
      (0, -1), // UP
      (1, -1), // UPRIGHT
      (1, 0), // RIGHT
      (1, 1), // DOWNRIGHT
      (0, 1), // DOWN
      (-1, 1), // DOWNLEFT
      (-1, 0), // LEFT
      (-1, -1) // UPLEFT
    )
    val adjacentPositions = directions.map { case (dx, dy) =>
      ((parentX + dx + maxCells) % maxCells, (parentY + dy + maxCells) % maxCells)
    }

    val availablePositions = adjacentPositions.filterNot(occupiedPositions.contains)

    if (availablePositions.nonEmpty) {
      availablePositions(random.nextInt(availablePositions.length))
    } else {
      generateUniquePosition(occupiedPositions, random, maxCells)
    }
  }

  def generateUniquePosition(occupiedPositions: Set[(Int, Int)], random: Random, maxCells: Int): (Int, Int) = {
    var newPosition = (random.nextInt(maxCells), random.nextInt(maxCells))
    while (occupiedPositions.contains(newPosition)) {
      newPosition = (random.nextInt(maxCells), random.nextInt(maxCells))
    }
    newPosition
  }
}
