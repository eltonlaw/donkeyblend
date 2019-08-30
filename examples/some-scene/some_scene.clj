(require '[donkeyblend.compile :refer [defscript]]
         '[donkeyblend.python :as py])

(defscript create_square [bpy]
  (py/import "bpy")
  (py/print "hello"))
