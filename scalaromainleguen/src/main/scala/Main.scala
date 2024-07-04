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

object Main extends JFXApp3 {

  private val windowSize: Int = 600
  val cellSize: Int = 1 

  override def start() = {
    val random = new Random
  val particules = Array.fill(1000) {
    Particule(
      id = random.nextInt(500),
      x = random.nextInt(600), 
      y = random.nextInt(600), 
      direction = Direction.random, 
      color = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)) // faut faire le random
    )
  }

    val gameState: ObjectProperty[GameState] = ObjectProperty(GameState(particules))

    stage = new PrimaryStage {
      title = "Particules"
      width = windowSize
      height = windowSize
      scene = new Scene {
        fill = White
        content = gameState.value.draw(cellSize)
        gameState.onChange { (_, _, newValue) =>
          content = newValue.draw(cellSize)
        }
      }
    }

    new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(25),
          onFinished = _ => gameState.update(gameState.value.move(windowSize, cellSize))
        )
      )
      cycleCount = Timeline.Indefinite 
    }.play()
  }

  
}
