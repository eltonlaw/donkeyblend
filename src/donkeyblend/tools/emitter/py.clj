(ns donkeyblend.tools.emitter.py
  (:require [clojure.string :as str]))

(defmulti -emit (fn [{:keys [op]}] op))

(defn emit
  "AST -> Python"
  [ast]
  (-emit ast))

(defmethod -emit :const
  [{:keys [type val]}]
  (case type
    :bool (str/capitalize val)
    :keyword (str "\"" (name val) "\"")
    :number (str val)
    (throw (Exception. (format "Unknown type %s with value %s" type val)))))
