(ns donkeyblend.core
  (:require [clojure.string :as str]
            [donkeyblend.reader :as reader]
            [donkeyblend.tools.analyzer.py :as analyzer]
            [donkeyblend.tools.emitter.py :as emitter]))

(defn -main
  "Reads in clojure file, runs reader, analyzer and emitter saving output to 
  input file with a `.py` extension"
  [& args]
  (let [[path & _] args
        path-out (str/replace path #".clj" ".py")
        raw-text (slurp path)
        forms (reader/read-string raw-text)
        ast (analyzer/analyze forms)
        py (emitter/emit ast)]
    (spit path-out py)))
