;; clj -A:build
(ns basic
  (:require [donkeyblend.compile :refer [defscript]]
            [donkeyblend.python :as py]
            [donkeyblend.io :as db.io]))

(defscript init
  (py/import "bpy")
  (py/import "mathutils")
  "\n"
  (py/assign "C" "bpy.context")
  (py/assign "D" "bpy.data"))

(defscript create_square
  init
  (py/set-bl-info {:name "some script"
                   :description "does something"})
  "\n"
  (py/print "hello")
  (py/assign "context_name" (py/attr :bpy :context :object :name))
  (py/assign "context" (py/attr :bpy :data :objects [:get :context_name])))

(defn -main []
  (db.io/write! create_square {:filename "create_square.py"
                               :filepath "scripts/startup"}))
