(ns donkeyblend.reader
  (:refer-clojure :exclude [read-string read])
  (:require [clojure.tools.reader.edn :as edn]))

(defn read-string [s]
  (edn/read-string (str \( s \))))
