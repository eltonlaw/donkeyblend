(ns basic.core
    (:require [donkeyblend.compile :refer [defscript]]
              [donkeyblend.python :as py]
              [donkeyblend.io :as db.io]))

(defscript create_square
  (py/import "bpy")
  (py/set-bl-info {:name "some script"
                   :description "does something"})
  (py/print "hello"))

(db.io/write! create_square {:filename "create_square.py"
                             :filepath "scripts/startup"})
