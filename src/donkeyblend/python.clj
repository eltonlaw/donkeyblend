(ns donkeyblend.python
  "Naive implementation of clojure bindings for a subset of python"
  (:require [clojure.string :as str]))

;;;;;;;; STRING UTILS ;;;;;;;;;;;

(defn surround
  ([string sym]
   (str sym string sym))
  ([string sym1 sym2]
   (str sym1 string sym2)))

(defn surround-round-par [string]
   (surround string "(" ")"))

(defn clj->py-literal
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

(defn fn-invoke [& args]
  (let [[fn & args] (map clj->py-literal args)]
    (str fn (surround-round-par (str/join ", " args)))))

;;;;;; PYTHON CORE ;;;;;;;;;;;;;;;;

(defn assign [name value]
  (str (clj->py-literal name) " = " (clj->py-literal value)))

(defn import [module]
  (format "import %s" module)) 

(defn print [& args]
  (apply fn-invoke :print args))
