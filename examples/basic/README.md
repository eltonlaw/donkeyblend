# Basic Example

In a shell run:

    $ clj -A:build

Which will transpile something like this Clojure code

    (defscript create_square
        (py/import "bpy")
        (py/set-bl-info {:name "some script"
                         :description "does something"})
        (py/print "hello"))

...to this:

    import bpy
    bl_info = {"name": "some script", "description": "does something"}
    print("hello")
