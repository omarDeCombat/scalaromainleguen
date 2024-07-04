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

    val prey = Prey(1,1,' ') ;
    val predator = Predator(39,39,' ') ;
    val map = generateGrid();


    val preyAndPredatorState : ObjectProperty[PreyAndPredatorState] = ObjectProperty(PreyAndPredatorState(prey,predator,map))

    stage = new PrimaryStage {
      title = "PreyAndPredator"
      width = windowSize
      height = windowSize
      scene = new Scene {
        fill = White
        content = preyAndPredatorState.value.draw(cellSize)
        preyAndPredatorState.onChange { (_, _, newValue) =>
          content = newValue.draw(cellSize)
        }

        onKeyPressed  = (event: KeyEvent) => {
          updateDirection(preyAndPredatorState ,event)
        }
        
      }
    }

    new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(250),
          onFinished = _ => preyAndPredatorState.update(preyAndPredatorState.value.move()) //println("onFinished")// watorState.update(watorState.value.move(windowSize, cellSize))
        )
      )
      cycleCount = Timeline.Indefinite 
    }.play()
  }


  private def updateDirection(preyAndPredatorState: ObjectProperty[PreyAndPredatorState], key: KeyEvent): Unit = {
    val newState = key.getCode match {
      case KeyCode.Z => preyAndPredatorState.value.setNextMove('z')
      case KeyCode.Q => preyAndPredatorState.value.setNextMove('q')
      case KeyCode.S => preyAndPredatorState.value.setNextMove('s')
      case KeyCode.D => preyAndPredatorState.value.setNextMove('d')
      case _         => preyAndPredatorState.value
    }
    preyAndPredatorState.value = newState
  }

  def generateGrid(): Array[Array[Int]] = {
    val gridSize = windowSize/cellSize
    val bushDensity = 0.1 // 20% of the grid will be bushes
    val grid = Array.ofDim[Int](gridSize, gridSize)
    val rand = new Random()

    for (i <- 0 until gridSize) {
      for (j <- 0 until gridSize) {
        if (rand.nextDouble() < bushDensity) {
          grid(i)(j) = 1 // 1 represents a bush
        } else {
          grid(i)(j) = 0 // 0 represents a free space
        }
      }
    }
    printGrid(grid)
    grid
  }

  def printGrid(grid: Array[Array[Int]]): Unit = {
    for (row <- grid) {
      println(row.mkString(" "))
    }
  }
  
}



// code du jeux de la vie
// package scala

// import scalafx.application.JFXApp3
// import scalafx.application.JFXApp3.PrimaryStage
// import scalafx.beans.property.ObjectProperty
// import scalafx.scene.Scene
// import scalafx.scene.paint.Color._
// import scalafx.util.Duration
// import scalafx.animation.{KeyFrame, Timeline}
// import javafx.scene.input.{KeyEvent, KeyCode}
// import scala.util.Random
// import scalafx.scene.paint.Color

// object Main extends JFXApp3 {

//   private val windowSize: Int = 600
//   val cellSize: Int = 1 

//   override def start() = {
//     val random = new Random
//   val particules = Array.fill(1000) {
//     Particule(
//       id = random.nextInt(500),
//       x = random.nextInt(600), 
//       y = random.nextInt(600), 
//       direction = Direction.random, 
//       color = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)) // faut faire le random
//     )
//   }

//     val gameState: ObjectProperty[GameState] = ObjectProperty(GameState(particules))

//     stage = new PrimaryStage {
//       title = "Particules"
//       width = windowSize
//       height = windowSize
//       scene = new Scene {
//         fill = White
//         content = gameState.value.draw(cellSize)
//         gameState.onChange { (_, _, newValue) =>
//           content = newValue.draw(cellSize)
//         }
//       }
//     }

//     new Timeline {
//       keyFrames = List(
//         KeyFrame(
//           time = Duration(25),
//           onFinished = _ => gameState.update(gameState.value.move(windowSize, cellSize))
//         )
//       )
//       cycleCount = Timeline.Indefinite 
//     }.play()
//   }

//   
// }
