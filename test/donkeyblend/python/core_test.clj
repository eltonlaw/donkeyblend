(ns donkeyblend.python.core-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [donkeyblend.python.core :as py]
            [donkeyblend.test-utils :refer [is-str=]]))

(deftest boilerplate-test
  (is-str= (py/boilerplate)
           "import bpy\nimport mathutils"))

(deftest set-bl-info-test
  (is-str= (py/set-bl-info {:name "Move X Axis" :category "Object"})
           "bl_info = {'name': 'Move X Axis', 'category': 'Object'}"))
