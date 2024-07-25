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

// object Main extends JFXApp3{
// 	//Exo 1
//   def contact[T](l1: List[T], l2: List[T]): List[T] = l1 match {
//     case Nil => l2
//     case head :: tail => head :: contact(tail, l2)
//   }

//   val l1 = List(1, 2, 3)
//   val l2 = List(4, 5, 6)
//   val contact = contact(l1, l2)
//   println(contact)

//   //Exo2
//   // def max(l:List[Int]):Option[Int]{

//   // }

//   //Exo3
//   def adition(l:List[Int]):Int{
//     return 10 ;
//   }//propose moi une implementation recurcive la plus simple possible en scala fonctionnel pour pour calculer la somme des elements  d'une list 


// }

object Main extends JFXApp3 {

  private val windowSize: Int = 500
  val cellSize: Int = 10
  val numberTuna: Int = 300
  val numberShark: Int = 50
  val sharkEnergie: Int = 6
  val sharkBreed: Int = 18
  val tunaBreed: Int = 4
  val maxTunas: Int = 1000
  val maxShark: Int = 1000

  override def start() = {
    val tunas: Vector[Tuna] = generateTunas(numberTuna)
    val sharks: Vector[Shark] = generateSharks(numberShark)

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
          onFinished = _ => watorState.update(watorState.value.move(windowSize, cellSize, sharkBreed, tunaBreed, sharkEnergie, maxTunas, maxShark))
        )
      )
      cycleCount = Timeline.Indefinite
    }.play()
  }

  def generateTunas(n: Int): Vector[Tuna] = {
    Vector.tabulate(n) { id =>
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

  def generateSharks(n: Int): Vector[Shark] = {
    Vector.tabulate(n) { id =>
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
