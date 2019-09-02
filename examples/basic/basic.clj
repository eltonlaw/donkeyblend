;; clj -A:build
(ns basic
  (:require [donkeyblend.compile :refer [defscript]]
            [donkeyblend.python :as py]
            [donkeyblend.io :as db.io]))

(defscript create_square
  (py/import "bpy")
  (py/set-bl-info {:name "some script"
                   :description "does something"})
  (py/print "hello")
  (py/assign "context_name" (py/attr :bpy :context :object :name))
  (py/assign "context" (py/attr :bpy :data :objects [:get :context_name])))

(defn -main []
  (db.io/write! create_square {:filename "create_square.py"
                               :filepath "scripts/startup"}))
