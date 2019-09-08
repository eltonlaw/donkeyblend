(ns donkeyblend.compile
  (:require [clojure.string :as str]))

(defmacro defscript
  "All top level arguments are joined"
  [script-name & body]
  `(def ~script-name (str/join "\n" [~@body])))
