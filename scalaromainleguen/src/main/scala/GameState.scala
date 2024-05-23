package scala

import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import Direction.*
import scala.annotation.switch
import scala.compiletime.ops.boolean
import scala.concurrent.ExecutionContext.parasitic

final case class GameState(particules: Array[Particule]) {

  def draw(cellSize: Int): Seq[Rectangle] = {
    particules.map { particule =>
      new Rectangle {
        x = particule.x * cellSize
        y = particule.y * cellSize
        width = 5
        height = 5
        fill = particule.color
      }
    }
  }

  

  def move(windowSize: Int, cellSize: Int):GameState  = {
    val movedParticules = particules.map { particule =>
      particule.direction match {
        case UP         => particule.copy(y = particule.y - cellSize)
        case UPRIGHT    => particule.copy(x = particule.x + cellSize, y = particule.y - cellSize)
        case RIGHT      => particule.copy(x = particule.x + cellSize)
        case DOWNRIGHT  => particule.copy(x = particule.x + cellSize, y = particule.y + cellSize)
        case DOWN       => particule.copy(y = particule.y + cellSize)
        case DOWNLEFT   => particule.copy(x = particule.x - cellSize, y = particule.y + cellSize)
        case LEFT       => particule.copy(x = particule.x - cellSize)
        case UPLEFT     => particule.copy(x = particule.x - cellSize, y = particule.y - cellSize)
      }

    }
    copy(particules = movedParticules).checkCollision()
    //checkCollision
    //return
  }






    //  x - - - -    si x
  def checkCollision(): GameState = {
    val updatedParticules = particules.map { particule =>
    val hasCollision = particules.exists { other =>
      other.id != particule.id &&
      intersects(particule.x, particule.y, 5, 5, other.x, other.y, 5, 5)
    }
    if (hasCollision) {
      particule.copy(
        //color = Color.Red,
        direction = Direction.random
      )
    } else if(particule.x<=0 ||particule.x>=600 || particule.y<=0 || particule.y>=600){
        particule.copy(direction = Direction.opposite(particule.direction))
    }else {
      particule
    }
  }
  copy(particules = updatedParticules)
  }

  //merci interneeeeet
  def intersects(x1: Int, y1: Int, w1: Int, h1: Int, x2: Int, y2: Int, w2: Int, h2: Int): Boolean = {
  x1 < x2 + w2 && x2 < x1 + w1 &&
  y1 < y2 + h2 && y2 < y1 + h1
}
}

