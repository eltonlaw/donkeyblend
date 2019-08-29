(ns donkeyblend.python
  "Naive implementation of clojure bindings for a subset of python"
  (:require [clojure.string :as str]))

(defn double-quote [s]
  (str "\"" s "\""))

(defn import
  [module]
  (format "import %s" module)) 

(defn print
  "Strings are passed on as string literals and keywords are resolved"
  [& args]
  (let [;; Dispatch by argument passed in
        preprocess (fn [arg]
                     (cond 
                       (string? arg) (double-quote arg)
                       (keyword? arg) (name arg)
                       :default (throw (Exception. (str "Don't know how to print type:" (type arg))))))
        ;; Resolve variables and double quote literals
        args (->> (map preprocess args) (str/join ", "))]
    ;; Throw values into python interface
    (apply str (concat ["print("] args [")"]))))
