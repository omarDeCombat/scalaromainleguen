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
        case (x, y) => 
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
        shark.copy(x = tunax, y = tunay,energy = shark.energy + 35)
      }  
    }

    // map thon 
    val movedTunas = tunas.flatMap { tuna =>

      //si l'id du tuna correspond on le fais disparaitre 
      if(idTuna == tuna.id){// le thon est manger faire un flatmap
        tuna.copy(color = Color.Red) // tmp on le passe en blanc pour faire genre il disparait
        List()
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
        List(tuna.copy(x = newX, y = newY))//tuna.copy(x = newX, y = newY)
      }
        

      
    }

    copy(tunas = movedTunas, sharks = movedSharks).changeDirection().sharkEnergie().tunaBaby().sharksBaby()
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

  def sharkEnergie(): WatorState = {
    val sharksNRJ = sharks.flatMap { shark =>
      if(shark.energy == 0 ){
        List()
      }
      else{
        List(shark.copy(energy = shark.energy- 1))
      }
      
    }
    copy(sharks = sharksNRJ)
  }

  def tunaBaby(): WatorState = {
    val random = new Random
    val tunasBaby = tunas.flatMap { tuna =>
      if (tuna.breed == 0){

        List(tuna.copy(breed = 15),Tuna(
        id = random.nextInt(1000),
        x = tuna.x, 
        y = tuna.y,
        direction = Direction.random,
        breed = 10,
        dead = false,
        color = Color.Gray
      ))
      }
      else{
        List(tuna.copy(breed = tuna.breed- 1))
      }

    }
    copy(tunas = tunasBaby)
  }

  def sharksBaby(): WatorState = {
    val random = new Random
    val sharksBaby = sharks.flatMap { shark =>
      if (shark.breed == 0){

        List(shark.copy(breed = 20),Shark(
        id = random.nextInt(1000),
        x = shark.x, 
        y = shark.y,
        direction = Direction.random,
        breed = 20,
        energy = 19
      ))
      }
      else{
        List(shark.copy(breed = shark.breed- 1))
      }

    }
    copy(sharks = sharksBaby)
  }
  
}
