(ns donkeyblend.python.str-utils
  (:refer-clojure :exclude (print import))
  (:require [clojure.string :as str]))

(defn surround
  ([string sym]
   (str sym string sym))
  ([string sym1 sym2]
   (str sym1 string sym2)))

(defn surround-round-par [string]
   (surround string "(" ")"))

(defn surround-curly-par [string]
   (surround string "{" "}"))

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
    (string? v) (surround v "'")
    (keyword? v) (name v)
    (list? v) (map clj->py-literal v)
    (vector? v) (mapv clj->py-literal v)
    :default (throw (Exception. (str "Don't know how to handle type:" (type v))))))

(defn fn-invoke [& args]
  (let [[fn & args] args]
    (str fn (surround-round-par (str/join ", " args)))))
