(ns donkeyblend.tools.analyzer.py-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.tools.analyzer.ast :as ast]
            [donkeyblend.tools.analyzer.py :as analyzer]
            [donkeyblend.tools.emitter.py :as emitter]
            [donkeyblend.reader :as reader]))

(defn- dissoc-in [m ks]
  (update-in m (butlast ks) dissoc (last ks)))

(defn- analyze [s]
  (analyzer/analyze (reader/read-string s)))

(deftest analyze-number-literals
  (let [result (analyze "1")]
    (is (= :const (:op result)))
    (is (= :number (:type result)))
    (is (= 1 (:val result)))))

(deftest analyze-arithmetic
  (let [result (analyze "(+ 1 2)")]
    (is (= :invoke (:op result)))
    (is (= #'clojure.core/+ (-> result :fn :var)))))

(deftest analyze-if
  (let [result (analyze "(if true :then :else)")
        children (ast/children result)]
    (is (= :if (:op result)))
    (is (= true (:val (first children))))
    (is (= :then (:val (second children))))
    (is (= :else (:val (nth children 2))))))
