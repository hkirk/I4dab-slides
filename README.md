### Use DAB slides

Download SBT: https://www.scala-sbt.org/download.html

In terminal/console navigate to this repository and type

> sbt

A 'project' corresponds to a folder in /dab/ and is defined in build.sbt

So eg. to work on intro slides in the SBT terminal opened above type

sbt:root> project dabIntro

sbt:dabIntro> fastOptJs

'fastOptJs' compiles the html that can be used to show slides in browser and/or to export PDF.

To use/show these slides open file://..../dab/1.1-intro/index.html in a browser

To export to PDF use the same url but append ?print-pdf so to export intro file://..../dab/1.1-intro/index.html?print-pdf

Keyboard shortcuts to know:
'ESC' or 'O' to toogle the overview feature
'S' to show display Speaker view (if enabled)


# ScalaJS + reveal.js = &#10084;
This is a basic project setup to create beautiful [reveal.js](https://github.com/hakimel/reveal.js/) presentations with [ScalaJS](https://www.scala-js.org/). To use it just clone or fork this repository and simply start to write down your own slide-deck.

### How to use it
 1. Create a new SBT sub-project for your presentation (see [my-talk](https://github.com/pheymann/scala-reveal-js/blob/master/build.sbt#L30) as an example).
 2. Write down your slide-deck. For more information take a look at the [example](https://github.com/pheymann/scala-reveal-js/blob/master/my-talk/src/main/scala/MyTalk.scala) and [reveal.js](https://github.com/hakimel/reveal.js/).
 3. Compile your presentations with `sbt "project myTalk" "fastOptJS"` or if it is the final state `sbt "project myTalk" "fullOptJS"`. Just make sure you reference the right JS files in the [index.html](https://github.com/pheymann/scala-reveal-js/blob/master/my-talk/index.html) - the root of your presentation.
 
