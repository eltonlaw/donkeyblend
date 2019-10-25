(ns rugged-donut
  ;; https://github.com/njanakiev/blender-scripting/blob/master/scripts/rugged_donut.py 
  ;; An attempt at forcing the above python script into idiomatic clojure
  (:import os
           (math pi sin cos)
           bpy)
  (:require [utils :as u]))

(def num-frames 100)

(defn -main []
  (utils/remove-all)
  (let [[target camera lamp] (u/simple-scene [0 0 -3]
                                             [4.2 4.2 5]
                                             [-5 5 10])]
    (println "...")))
