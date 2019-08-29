(ns donkeyblend.compile
  (:require [donkeyblend.python :as py]))

(defn ->py []
  (py/import "bpy"))

(defmacro defscript [script-name & body])
