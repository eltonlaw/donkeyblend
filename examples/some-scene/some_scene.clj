(require '[donkeyblend.compile :refer [defscript]]
         '[donkeyblend.python :as py])

(defscript create_square [bpy]
  (py/import "bpy")
  (py/set-bl-info {:name "some script"
                   :description "does something"})
  (py/print "hello"))
