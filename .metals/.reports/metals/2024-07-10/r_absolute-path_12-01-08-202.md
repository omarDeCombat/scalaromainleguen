### java.lang.IllegalArgumentException: Illegal character in opaque part at index 2: <WORKSPACE>\scalaromainleguen\src\main\scala\WatorState.scala

Uri: <WORKSPACE>\scalaromainleguen\src\main\scala\WatorState.scala


#### Error stacktrace:

```
java.base/java.net.URI.create(URI.java:906)
	scala.meta.internal.mtags.MtagsEnrichments$XtensionStringMtags.toAbsolutePath(MtagsEnrichments.scala:187)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePath(MetalsEnrichments.scala:756)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePath(MetalsEnrichments.scala:753)
	scala.meta.internal.metals.MetalsEnrichments$XtensionString.toAbsolutePathSafe(MetalsEnrichments.scala:739)
	scala.meta.internal.metals.MetalsLspService.$anonfun$reports$2(MetalsLspService.scala:164)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.MetalsLspService.$anonfun$reports$1(MetalsLspService.scala:162)
	scala.meta.internal.metals.StdReporter.reportPath(ReportContext.scala:163)
	scala.meta.internal.metals.StdReporter.create(ReportContext.scala:131)
	scala.meta.internal.metals.ReferenceProvider.$anonfun$references$3(ReferenceProvider.scala:196)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:833)
```
#### Short summary: 

java.lang.IllegalArgumentException: Illegal character in opaque part at index 2: <WORKSPACE>\scalaromainleguen\src\main\scala\WatorState.scala