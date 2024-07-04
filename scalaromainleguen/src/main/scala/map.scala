import scala.util.Random

class map {
  val gridSize = 40
  val bushDensity = 0.2 // 20% of the grid will be bushes

  def generateGrid(): Array[Array[Int]] = {
    val gridSize = 40
    val bushDensity = 0.2 // 20% of the grid will be bushes
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