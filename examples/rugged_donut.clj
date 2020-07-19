(ns rugged-donut
  ;; https://github.com/njanakiev/blender-scripting/blob/master/scripts/rugged_donut.py 
  ;; An attempt at forcing the above python script into idiomatic clojure
  (:import os
           (math pi sin cos)
           bpy)
  (:require [utils]))

;; Sets number of frame
(def num-frames 100)

(defn empty-obj
  (.. bpy (data) (objects) (new "Empty" nil)))

(defn -main []
  ;; Remove all elements
  (utils/remove-all!)

  (let [[target camera lamp] (utils/simple-scene [0 0 -3]
                                                 [4.2 4.2 5]
                                                 [-5 5 10])
        eobj (empty-obj)
        transition-fn #(vec (+ (* 0.7 (cos (* 2 pi %))) 1
                               (* 0.7 (sin (* 2 pi %)))
                               (* 0.4 (sin (* 2 pi %)))))]
    (doseq [frame (range 100)]
      (.keyframe_insert {^Attribute :location [x y z]
                         :data_path "locations"
                         :index -1
                         :frame frame}
                        (transition-fn (/ frame num-frames))))))
