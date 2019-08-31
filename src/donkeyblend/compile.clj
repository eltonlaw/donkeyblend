(ns donkeyblend.compile
  (:require [clojure.string :as str]
            [donkeyblend.python :as py]))

(defmacro defscript
  "All top level arguments are joined"
  [script-name & body]
  `(def ~script-name (str/join "\n" [~@body])))
