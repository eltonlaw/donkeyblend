(ns donkeyblend.reader
  (:refer-clojure :exclude [read-string])
  (:require [clojure.tools.reader.edn :as edn]))

(defn read-string [s]
  (edn/read-string {:eof nil} s))
