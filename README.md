# donkeyblend

Clojure to Python transpiler for scripting Blender (WIP)

This project leans heavily on the interface exposed through [`tools.analyzer`](https://github.com/clojure/tools.analyzer) and [`tools.reader`](https://github.com/clojure/tools.reader)... and more indirectly on large swaths of code/ideas/architectural decisions/variable names etc. from the analyzer in [`clojurescript`](https://github.com/clojure/clojurescript), [`tools.analayzer.jvm`](https://github.com/clojure/tools.analyzer.jvm) and [`tools.analyzer.js`](https://github.com/clojure/tools.analyzer.js/).

## Motivations

Even for something as visually-oriented as Blender, there are reasons to use a text based interface. Re-usability and precision are the main ones that come to mind for me. Blender's Python interface is powerful, super powerful but I've had trouble adjusting to it. Perhaps it's more useful for people entrenched in design or professionals users but as a simple earthworm, I found myself fighting with the software throughout the development and debugging of a relatively simple asset.

I started procrastinating and well...one thing led to another and here we are.

Also compilers are neat.

## Design

After some initial thinking, there are quite a few interesting things to consider in tackling this problem.

It's almost pointless to do something that ends up as just imperative, mutation-heavy Clojure

    (do
        (import bpy)
        (-> bpy (.data) (.objects) (.new "Sphere" {:location [0 0 0]})))
        ...

Basically Python with some syntax sugar...kind of. At this point macros are available and everything is in S-expressions so that's pretty convenient.

The design goals are to:

1. Cut down on boilerplate, minimize incidental complexity, expose an API that has multiple layers (depending on the amount of fine-tuning required), use a more declarative style
2. Make interactive development with source code more tightly integrated to the entire workflow. Cut down on feedback loop. Better debugging tools.

Conceptually, Blender seems to fit well in a functional framework, a few unique data types and multiple functions that can act on them. Making things small and composable is a pretty straightforward translation.

## Implementation

## Future Work

There are [undo & redo operations](https://docs.blender.org/api/2.79/bpy.ops.ed.html?highlight=undo#bpy.ops.ed.undo_redo), [methods to get the pointer](https://docs.blender.org/api/current/bpy.types.bpy_struct.html#bpy.types.bpy_struct.as_pointer) as well as the concept of hiding and showing layers, copying objects. At the very least it's quite possible to have some memory-intensive, poor imitation of persistent data structures. Some sort of system will need to be made to track operations on objects, but once that's done we'll be able to treat all operations on objects as immutable. Needs more research. Some more digging into what's available as well as how things are implemented under the hood may yield better options.

Having a workflow with an external editor that's still connected to the GUI is optimal, a custom Blender plugin containing a server to hot reload code somehow will need to be made if something can't be hacked together.

