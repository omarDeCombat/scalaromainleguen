package scala

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.util.Duration
import scalafx.animation.{KeyFrame, Timeline}
import javafx.scene.input.{KeyEvent, KeyCode}
import scala.util.Random
import scalafx.scene.paint.Color
import Direction._

object Main extends JFXApp3 {

  private val windowSize: Int = 1000
  val cellSize: Int = 10
  val numberTuna: Int = 100
  val numberShark: Int = 200
  val sharkEnergie: Int = 3
  val sharkBreed: Int = 2
  val tunaBreed: Int = 20

  override def start() = {
    val random = new Random

    val tunas: Array[Tuna] = generateTunas(numberTuna)
    val sharks: Array[Shark] = generateSharks(numberShark)

    val watorState: ObjectProperty[WatorState] = ObjectProperty(WatorState(tunas, sharks))

    stage = new PrimaryStage {
      title = "PreyAndPredator"
      width = windowSize
      height = windowSize
      scene = new Scene {
        fill = White
        content = watorState.value.draw(cellSize)
        watorState.onChange { (_, _, newValue) =>
          content = newValue.draw(cellSize)
        }
      }
    }

    new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(100),
          onFinished = _ => watorState.update(watorState.value.move(windowSize, cellSize,sharkBreed,tunaBreed,sharkEnergie))
        )
      )
      cycleCount = Timeline.Indefinite
    }.play()
  }

  def generateTunas(n: Int): Array[Tuna] = {
    Array.tabulate(n) { id =>
      Tuna(
        id = id,
        x = Random.nextInt(windowSize / cellSize),
        y = Random.nextInt(windowSize / cellSize),
        direction = Direction.random,
        breed = Random.nextInt(tunaBreed),
        dead = false,
        color = Color.Gray
      )
    }
  }

  def generateSharks(n: Int): Array[Shark] = {
    Array.tabulate(n) { id =>
      Shark(
        id = id,
        x = Random.nextInt(windowSize / cellSize),
        y = Random.nextInt(windowSize / cellSize),
        direction = Direction.random,
        breed = Random.nextInt(sharkBreed),
        energy = Random.nextInt(sharkEnergie)
      )
    }
  }
}
