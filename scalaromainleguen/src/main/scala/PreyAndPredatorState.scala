package scala

import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import Direction.*
import scala.annotation.switch
import scala.compiletime.ops.boolean
import scala.concurrent.ExecutionContext.parasitic
import scala.util.Random

case class Position(x: Int, y: Int)

final case class PreyAndPredatorState(prey:Prey,predator:Predator,map:Array[Array[Int]] ){
    
    def draw(cellSize:Int): Seq[Rectangle] = {
        println("draw")
        var rectangles: Seq[Rectangle] = Seq()

        for (i <- map.indices) {
            for (j <- map(i).indices) {
                val rect = new Rectangle {
                x = j * cellSize
                y = i * cellSize
                width = cellSize
                height = cellSize
                fill = if (map(i)(j) == 1) Color.Green else Color.LightGray
                }
                rectangles :+= rect
            }
        }

        val preyRectangle = new Rectangle{
            x = prey.x * cellSize
            y = prey.y * cellSize
            width = cellSize
            height = cellSize
            fill =  Color.Blue
        }

        val predatorRectangle = new Rectangle{
            x = predator.x * cellSize
            y = predator.y * cellSize
            width = cellSize
            height = cellSize
            fill =  Color.Red
        }

        rectangles ++= Seq(preyRectangle, predatorRectangle);
         return rectangles ;
    }

    def update():PreyAndPredatorState = {
        copy()
    }


    def move(): PreyAndPredatorState = {
        val preyPosition = Position(prey.x, prey.y)
        val predatorPosition = Position(predator.x, predator.y)

        var updatedPrey =prey.copy();
        var updatedPredator =predator.copy();

        if(preyPosition != predatorPosition){
            val nextPredatorPosition = setNextMovePredator(predatorPosition, preyPosition)

            updatedPredator = predator.copy(x = nextPredatorPosition.x, y = nextPredatorPosition.y)

            updatedPrey = prey.direction match {
                case 'z' => prey.copy(y = prey.y - 1) // Haut
                case 'q' => prey.copy(x = prey.x - 1) // Gauche
                case 's' => prey.copy(y = prey.y + 1) // Bas
                case 'd' => prey.copy(x = prey.x + 1) // Droite
                case _ => prey // Autres touches
            }

            updatedPrey = updatedPrey.copy(direction = ' ')
        }
        else{
           updatedPredator = predator.copy(x = 40, y = 40)
           updatedPrey = prey.copy(x = 10, y = 10)
        }

        


        copy(prey = updatedPrey, predator = updatedPredator)
    }

    def setNextMovePredator(start: Position, goal: Position ): Position = {
        val directions = List((0, 1), (1, 0), (0, -1), (-1, 0)) // Directions for moving up, right, down, and left
        val width = 40
        // Priority queue to keep track of the next position to visit with the shortest distance so far
        val pq = collection.mutable.PriorityQueue[(Int, Position)]()(Ordering.by(-_._1))
        pq.enqueue((0, start)) // Initialize with the start position and distance 0

        // Map to store the shortest distance to each position
        val distances = collection.mutable.Map[Position, Int]().withDefaultValue(Int.MaxValue)
        distances(start) = 0

        // Map to store the previous position to trace the shortest path
        val previous = collection.mutable.Map[Position, Position]()

        while (pq.nonEmpty) {
            val (currentDist, currentPosition) = pq.dequeue()
            if (currentPosition == goal) {
            // If we reached the goal, backtrack to find the next move from the start
            var pathPosition = goal
            while (previous(pathPosition) != start) {
                pathPosition = previous(pathPosition)
            }
            return pathPosition
            }

            // Explore the neighbors of the current position
            for ((dx, dy) <- directions) {
                val neighbor = Position(currentPosition.x + dx, currentPosition.y + dy)
                if (neighbor.x >= 0 && neighbor.x < width && neighbor.y >= 0 && neighbor.y < width  && map(neighbor.y)(neighbor.x) == 0){
                   val newDist = currentDist + 1
                    if (newDist < distances(neighbor)) {
                        distances(neighbor) = newDist
                        previous(neighbor) = currentPosition
                        pq.enqueue((newDist, neighbor))
                    } 
                }

            }
        }
        start
    }

    def setNextMove(direction : Char): PreyAndPredatorState = {
        
        println(direction)
        var updatedPrey = prey.copy();
        
        if (prey.direction == ' '){
            updatedPrey = direction match {
                case 'z' => prey.copy(direction = 'z')  // Haut
                case 'q' => prey.copy(direction = 'q')  // Gauche
                case 's' => prey.copy(direction = 's')  // Bas
                case 'd' => prey.copy(direction = 'd')  // Droite
                case _   => prey  // Autres touches
            } 
        }
        copy(prey = updatedPrey)
    }    
}