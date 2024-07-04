file:///C:/Users/warsr/Documents/GitHub/scalaromainleguen/scalaromainleguen/src/main/scala/WatorState.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 4035
uri: file:///C:/Users/warsr/Documents/GitHub/scalaromainleguen/scalaromainleguen/src/main/scala/WatorState.scala
text:
```scala
package scala

import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import Direction.*
import scala.annotation.switch
import scala.compiletime.ops.boolean
import scala.concurrent.ExecutionContext.parasitic
import scala.util.Random
final case class WatorState(tunas: Array[Tuna], sharks: Array[Shark]) {

    def draw(cellSize: Int): Seq[Rectangle] = {
    val tunaRectangles = tunas.map { tuna =>
      new Rectangle {
        x = tuna.x * cellSize
        y = tuna.y * cellSize
        width = cellSize
        height = cellSize
        fill =  tuna.color
      }
    }
    val sharkRectangles = sharks.map{shark =>
      new Rectangle{
        x = shark.x * cellSize
        y = shark.y * cellSize
        width = cellSize
        height = cellSize
        fill =  Color.BLUE
      }
      }
      tunaRectangles ++ sharkRectangles
  }

   def move(windowSize: Int, cellSize: Int): WatorState = {
    val maxCells = windowSize / cellSize
    var idTuna = -1;
    var tunax = 0 ;
    var tunay = 0 ;

    // shark map
    val movedSharks = sharks.map { shark =>
      tunax = 0 ;
      tunay = 0 ;

      val voisonXY = Array( // chat gpt coordonnée des case autour du requin
        ( shark.x - 1, shark.y - 1), // top-left
        (shark.x - 1, shark.y),     // top
        (shark.x - 1, shark.y + 1), // top-right
        (shark.x, shark.y - 1),     // left
        (shark.x, shark.y + 1),     // right
        (shark.x + 1, shark.y - 1), // bottom-left
        (shark.x + 1, shark.y),     // bottom
        (shark.x + 1, shark.y + 1)  // bottom-right
      )

      voisonXY.foreach { // for eache pour regarder si il y as un thon autour
        case (x, y) => println(s"($x, $y)")
        tunas.map { tuna =>
          if (x == tuna.x && y == tuna.y && tuna.dead != true){
            tunax = x 
            tunay = y 
            idTuna = tuna.id
          }
        }
      }

      if(tunax == 0 && tunay == 0){ // si pas de thon autour on avance
        
        val (newX, newY) = shark.direction match {
          case UP        => (shark.x, (shark.y - 1 + maxCells) % maxCells)
          case UPRIGHT   => ((shark.x + 1) % maxCells, (shark.y - 1 + maxCells) % maxCells)
          case RIGHT     => ((shark.x + 1) % maxCells, shark.y)
          case DOWNRIGHT => ((shark.x + 1) % maxCells, (shark.y + 1) % maxCells)
          case DOWN      => (shark.x, (shark.y + 1) % maxCells)
          case DOWNLEFT  => ((shark.x - 1 + maxCells) % maxCells, (shark.y + 1) % maxCells)
          case LEFT      => ((shark.x - 1 + maxCells) % maxCells, shark.y)
          case UPLEFT    => ((shark.x - 1 + maxCells) % maxCells, (shark.y - 1 + maxCells) % maxCells)
        }
        shark.copy(x = newX, y = newY)
      }
      else{ // sinon on se deplace sur le thon
        shark.copy(x = tunax, y = tunay)
      }  
    }

    // map thon 
    val movedTunas = tunas.flatMap { tuna =>

      //si l'id du tuna correspond on le fais disparaitre 
      if(idTuna == tuna.id){// le thon est manger faire un flatmap
        tuna.copy(color = Color.Red) // tmp on le passe en blanc pour faire genre il disparait
      }
      else{
        tuna.copy(color = Color.White)
        val (newX, newY) = tuna.direction match {
          case UP        => (tuna.x, (tuna.y - 1 + maxCells) % maxCells)
          case UPRIGHT   => ((tuna.x + 1) % maxCells, (tuna.y - 1 + maxCells) % maxCells)
          case RIGHT     => ((tuna.x + 1) % maxCells, tuna.y)
          case DOWNRIGHT => ((tuna.x + 1) % maxCells, (tuna.y + 1) % maxCells)
          case DOWN      => (tuna.x, (tuna.y + 1) % maxCells)
          case DOWNLEFT  => ((tuna.x - 1 + maxCells) % maxCells, (tuna.y + 1) % maxCells)
          case LEFT      => ((tuna.x - 1 + maxCells) % maxCells, tuna.y)
          case UPLEFT    => ((tuna.x - 1 + maxCells) % maxCells, (tuna.y - 1 + maxCells) % maxCells)
        }
        tuna.copy(x = newX, y = newY)
      }
        [@@]

      
    }

    copy(tunas = movedTunas, sharks = movedSharks).changeDirection()
  }

  //change de direction (devant droite ou gauche )
  def changeDirection(): WatorState = {
    
    val movedTunas = tunas.map { tuna =>
      Random.nextInt(3) match {
        case 0 => tuna.copy(direction =tuna.direction)
        case 1 => tuna.copy(direction = Direction.rightOf(tuna.direction))
        case 2 => tuna.copy(direction = Direction.leftOf(tuna.direction))
      }
    }

    val movedSharks = sharks.map { shark =>

      Random.nextInt(3) match {
        case 0 => shark.copy(direction =shark.direction)
        case 1 => shark.copy(direction = Direction.rightOf(shark.direction))
        case 2 => shark.copy(direction = Direction.leftOf(shark.direction))
      }
    }

    copy(tunas = movedTunas,sharks = movedSharks)
  }

  // def sharkLunchTime(): WatorState = {

  // }
}

```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2615)
	dotty.tools.dotc.core.SymUtils.isLocalToBlock(SymUtils.scala:332)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:222)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:104)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:53)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:391)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner