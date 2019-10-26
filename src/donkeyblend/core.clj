(ns donkeyblend.core
  (:require [donkeyblend.reader :as reader]))

(defn -main [& args]
  "Passing in a file"
  (let [[file & _] args]
    (-> (slurp file)
        reader/read-string)))
