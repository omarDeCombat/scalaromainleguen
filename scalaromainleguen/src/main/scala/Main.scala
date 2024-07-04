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
import Direction.*

object Main extends JFXApp3 {

  private val windowSize: Int = 500
  val cellSize: Int = 10

  override def start() = {
    val random = new Random

    // val prey = Prey(1,1,' ') ;
    // val predator = Predator(39,39,' ') ;
    // val map = generateGrid();
    val tunas: Array[Tuna] = generateTunas(10)
    val sharks: Array[Shark] = generateSharks(20)

    val watorState : ObjectProperty[WatorState] = ObjectProperty(WatorState(tunas,sharks));
    // val preyAndPredatorState : ObjectProperty[PreyAndPredatorState] = ObjectProperty(PreyAndPredatorState(prey,predator,map))

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
          time = Duration(250),
          onFinished = _ => watorState.update(watorState.value.move(windowSize, cellSize)) //println("onFinished")// watorState.update(watorState.value.move(windowSize, cellSize))
        )
      )
      cycleCount = Timeline.Indefinite 
    }.play()
  }




  def generateTunas(n: Int): Array[Tuna] = {
    Array.tabulate(n) { id =>
      Tuna(
        id = id,
        x = Random.nextInt(100), // Example range for x
        y = Random.nextInt(100), // Example range for y
        direction = Direction.random,
        breed = Random.nextInt(5),
        dead = Random.nextBoolean(),
        color = Color.Gray
      )
    }
  }

  def generateSharks(n: Int): Array[Shark] = {
    Array.tabulate(n) { id =>
      Shark(
        id = id,
        x = Random.nextInt(100), // Example range for x
        y = Random.nextInt(100), // Example range for y
        direction = Direction.random,
        breed = Random.nextInt(5),
        energy = Random.nextInt(100) // Example range for energy
      )
    }
  }



  
}

