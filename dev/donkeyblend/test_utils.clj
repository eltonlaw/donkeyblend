(ns donkeyblend.test-utils
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]))

(defmacro is-str=
  "Provides slightly better error messages by showing where the string mismatch occurs
   Lays out each arg character by character by character along side each other making
  the first column whether all characters at that index are equal or not argument. 
  
    user=> (is-str= \"abc\" \"abc\" \"abd\")
    true a a a
    true b b b
    false c c d
   "
  [& args]
  `(is (= ~@args)
       (let [char-equal?# (map = ~@args)
             table# (map vector char-equal?# ~@args)]
         (reduce
           (fn [s# row#] (str s# (str/join " " row#) "\n"))
           "" table#))))
