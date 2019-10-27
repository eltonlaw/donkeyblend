(ns donkeyblend.reader
  (:refer-clojure :exclude [read-string read *default-data-reader-fn*])
  (:require [clojure.tools.reader.edn :as edn]))

;; default fn for handling tags without a reader
(def ^:dynamic *default-data-reader-fn*
  (atom (constantly :unknown-tag)))
;; map with symbol (... our custom tag) as key and fn as value
(def ^:dynamic *tag-table*
  (atom {}))

(defn read-string
  "Given a string return EDN formatted data.

  NOTE: This returns a list of ALL objects in the string"
  [s]
  (edn/read-string
     {:readers @*tag-table*
      :default @*default-data-reader-fn*
      :eof nil}
     (str \( s \))))
