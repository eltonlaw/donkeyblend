(ns donkeyblend.python.core
  (:require [clojure.string :as str]
            [donkeyblend.python.lang :as l]))

(defn boilerplate []
  (str (l/import "bpy") "\n"
       (l/import "mathutils")))

(defn set-bl-info [m]
  (l/= "bl_info" (l/dict m)))
