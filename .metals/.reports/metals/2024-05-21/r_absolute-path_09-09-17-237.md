### java.lang.IllegalArgumentException: Illegal character in opaque part at index 2: <WORKSPACE>\scalaromainleguen\src\main\scala\GameState.scala

Uri: <WORKSPACE>\scalaromainleguen\src\main\scala\GameState.scala


#### Error stacktrace:

```
java.base/java.net.URI.create(URI.java:906)
	scala.meta.internal.mtags.MtagsEnrichments$XtensionStringMtags.toAbsolutePath(MtagsEnrichments.scala:187)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePath(MetalsEnrichments.scala:741)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePath(MetalsEnrichments.scala:738)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePathSafe(MetalsEnrichments.scala:724)
	scala.meta.internal.metals.MetalsLspService.$anonfun$reports$2(MetalsLspService.scala:187)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.MetalsLspService.$anonfun$reports$1(MetalsLspService.scala:185)
	scala.meta.internal.metals.StdReporter.reportPath(ReportContext.scala:163)
	scala.meta.internal.metals.StdReporter.create(ReportContext.scala:131)
	scala.meta.internal.metals.ReferenceProvider.$anonfun$references$2(ReferenceProvider.scala:176)
	scala.collection.immutable.List.map(List.scala:247)
	scala.meta.internal.metals.ReferenceProvider.references(ReferenceProvider.scala:151)
	scala.meta.internal.metals.MetalsLspService.$anonfun$compileAndLookForNewReferences$1(MetalsLspService.scala:1613)
	scala.meta.internal.metals.MetalsLspService.$anonfun$compileAndLookForNewReferences$1$adapted(MetalsLspService.scala:1588)
	scala.util.Success.foreach(Try.scala:268)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:481)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:833)
```
#### Short summary: 

java.lang.IllegalArgumentException: Illegal character in opaque part at index 2: <WORKSPACE>\scalaromainleguen\src\main\scala\GameState.scala