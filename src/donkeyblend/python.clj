(ns donkeyblend.python
  "Naive implementation of clojure bindings for a subset of python"
  (:require [clojure.string :as str]))

(defn surround
  ([string sym]
   (str sym string sym))
  ([string sym1 sym2]
   (str sym1 string sym2)))

(defn surround-round-par [string]
   (surround string "(" ")"))

(defn clj-value->py-value
  " Handle different types of input types

  To differentiate between what should be quoted and what should be left as
  is when writing to a python script:
   - Numbers are left as is
   - Strings are surrounded with double quotes (python literals)
   - Keywords are treated as python variables"
  [v]
  (cond 
    (number? v) v
    (string? v) (surround v "\"")
    (keyword? v) (name v)
    :default (throw (Exception. (str "Don't know how to handle type:" (type v))))))

(defn import
  [module]
  (format "import %s" module)) 

(defn print
  "Strings are passed on as string literals and keywords are resolved"
  [& args]
  (let [;; Resolve variables and double quote literals
        args (->> (map clj-value->py-value args) (str/join ", "))]
    (str "print" "(" (apply str args) ")")))
