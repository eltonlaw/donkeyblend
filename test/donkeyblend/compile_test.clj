(ns donkeyblend.compile-test
  (:require [clojure.test :refer [deftest is]]
            [donkeyblend.compile :refer :all]))

(deftest py-import-test
  (is (= (py-import "bpy")
         "import bpy")))

(deftest ->py-test
  (let [bpy nil]
    (is (= (-> bpy .data .objects (.get "Cube") .vertices (.get 0) .co .x inc)
           "bpy.data.objects[\"Cube\"].data.vertices[0].co.x += 1.0"))))
