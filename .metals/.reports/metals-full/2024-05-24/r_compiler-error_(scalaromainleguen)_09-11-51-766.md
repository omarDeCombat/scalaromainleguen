file:///C:/Users/warsr/Documents/GitHub/scalaromainleguen/scalaromainleguen/src/main/scala/WatorState.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1670
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
final case class WatorState(tunas: Array[Tuna], sharks: Array[Shark]) {

    def draw(cellSize: Int): Seq[Rectangle] = {
    val tunaRectangles = tunas.map { tuna =>
      new Rectangle {
        x = tuna.x * cellSize
        y = tuna.y * cellSize
        width = cellSize
        height = cellSize
        fill =  Color.RED
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

    def moveEntity[T <: { def x: Int; def y: Int; def direction: Direction }](entity: T): (Int, Int) = {
      entity.direction match {
        case UP        => (entity.x, (entity.y - 1 + maxCells) % maxCells)
        case UPRIGHT   => ((entity.x + 1) % maxCells, (entity.y - 1 + maxCells) % maxCells)
        case RIGHT     => ((entity.x + 1) % maxCells, entity.y)
        case DOWNRIGHT => ((entity.x + 1) % maxCells, (entity.y + 1) % maxCells)
        case DOWN      => (entity.x, (entity.y + 1) % maxCells)
        case DOWNLEFT  => ((entity.x - 1 + maxCells) % maxCells, (entity.y + 1) % maxCells)
        case LEFT      => ((entity.x - 1 + maxCells) % maxCells, entity.y)
        case @@UPLEFT    => ((entity.x - 1 + maxCells) % maxCells, (entity.y - 1 + maxCells) % maxCells)
      }
    }

    val movedTunas = tunas.map { tuna =>
      val (newX, newY) = moveEntity(tuna)
      tuna.copy(x = newX, y = newY)
    }

    val movedSharks = sharks.map { shark =>
      val (newX, newY) = moveEntity(shark)
      shark.copy(x = newX, y = newY)
    }

    copy(tunas = movedTunas, sharks = movedSharks)
  }
}


```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2615)
	dotty.tools.dotc.typer.Nullables$.usedOutOfOrder(Nullables.scala:286)
	dotty.tools.dotc.typer.Nullables$.isTracked(Nullables.scala:177)
	dotty.tools.dotc.typer.Nullables$TrackedRef$.unapply(Nullables.scala:142)
	dotty.tools.dotc.typer.Nullables$.caseContext(Nullables.scala:195)
	dotty.tools.dotc.typer.Typer.typedCase(Typer.scala:1987)
	dotty.tools.dotc.typer.Typer.typedCases$$anonfun$1(Typer.scala:1912)
	dotty.tools.dotc.core.Decorators$.loop$1(Decorators.scala:99)
	dotty.tools.dotc.core.Decorators$.mapconserve(Decorators.scala:115)
	dotty.tools.dotc.typer.Typer.typedCases(Typer.scala:1917)
	dotty.tools.dotc.typer.Typer.$anonfun$37(Typer.scala:1902)
	dotty.tools.dotc.typer.Applications.harmonic(Applications.scala:2392)
	dotty.tools.dotc.typer.Applications.harmonic$(Applications.scala:351)
	dotty.tools.dotc.typer.Typer.harmonic(Typer.scala:120)
	dotty.tools.dotc.typer.Typer.typedMatchFinish(Typer.scala:1902)
	dotty.tools.dotc.typer.Typer.typedMatch(Typer.scala:1831)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3128)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3197)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3389)
	dotty.tools.dotc.typer.Typer.typedBlock(Typer.scala:1200)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3121)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3197)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3389)
	dotty.tools.dotc.typer.Typer.$anonfun$62(Typer.scala:2603)
	dotty.tools.dotc.inlines.PrepareInlineable$.dropInlineIfError(PrepareInlineable.scala:256)
	dotty.tools.dotc.typer.Typer.typedDefDef(Typer.scala:2603)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3095)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3196)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3300)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3346)
	dotty.tools.dotc.typer.Typer.typedBlockStats(Typer.scala:1193)
	dotty.tools.dotc.typer.Typer.typedBlock(Typer.scala:1197)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3121)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3197)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3389)
	dotty.tools.dotc.typer.Typer.$anonfun$62(Typer.scala:2603)
	dotty.tools.dotc.inlines.PrepareInlineable$.dropInlineIfError(PrepareInlineable.scala:256)
	dotty.tools.dotc.typer.Typer.typedDefDef(Typer.scala:2603)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3095)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3196)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3300)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3346)
	dotty.tools.dotc.typer.Typer.typedClassDef(Typer.scala:2790)
	dotty.tools.dotc.typer.Typer.typedTypeOrClassDef$1(Typer.scala:3101)
	dotty.tools.dotc.typer.Typer.typedNamed$1(Typer.scala:3105)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3196)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3300)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3346)
	dotty.tools.dotc.typer.Typer.typedPackageDef(Typer.scala:2923)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3147)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3197)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3274)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3278)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3389)
	dotty.tools.dotc.typer.TyperPhase.typeCheck$$anonfun$1(TyperPhase.scala:47)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:477)
	dotty.tools.dotc.typer.TyperPhase.typeCheck(TyperPhase.scala:53)
	dotty.tools.dotc.typer.TyperPhase.$anonfun$4(TyperPhase.scala:99)
	scala.collection.Iterator$$anon$6.hasNext(Iterator.scala:479)
	scala.collection.Iterator$$anon$9.hasNext(Iterator.scala:583)
	scala.collection.immutable.List.prependedAll(List.scala:152)
	scala.collection.immutable.List$.from(List.scala:684)
	scala.collection.immutable.List$.from(List.scala:681)
	scala.collection.IterableOps$WithFilter.map(Iterable.scala:898)
	dotty.tools.dotc.typer.TyperPhase.runOn(TyperPhase.scala:100)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:315)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:337)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:350)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:360)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:69)
	dotty.tools.dotc.Run.compileUnits(Run.scala:360)
	dotty.tools.dotc.Run.compileSources(Run.scala:261)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:161)
	dotty.tools.pc.MetalsDriver.run(MetalsDriver.scala:47)
	dotty.tools.pc.HoverProvider$.hover(HoverProvider.scala:38)
	dotty.tools.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:345)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner