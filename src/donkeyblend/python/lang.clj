(ns donkeyblend.python.lang
  "Naive implementation of clojure bindings for a subset of python"
  (:refer-clojure :exclude (print import =))
  (:require [clojure.string :as str]
            [donkeyblend.python.str-utils :as stru]))

(defn = [name value]
  (str name " = " value))

(defn dict [m]
  (->> (map #(vector (-> % first name) (second %)) m)
       (map #(map stru/clj->py-literal %))
       (map #(str/join ": " %))
       (str/join ", ")
       stru/surround-curly-par))

(defn import [module]
  (format "import %s" module)) 

(defn print [& args]
  (apply stru/fn-invoke "print" (map stru/clj->py-literal args)))

(defn attr [& args]
  (let [[obj & args] (map stru/clj->py-literal args)]
    (loop [obj obj
           args args]
      (if (seq args)
        (let [dot (if (sequential? (first args))
                    (apply stru/fn-invoke (first args))
                    (first args))]
          (recur (str obj "." dot)
                 (rest args)))
        obj))))
