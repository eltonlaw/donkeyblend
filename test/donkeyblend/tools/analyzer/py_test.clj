(ns donkeyblend.tools.analyzer.py-test
  (:require [clojure.test :refer [is deftest testing]]
            [donkeyblend.reader :as reader]
            [donkeyblend.tools.analyzer.py :as ana.py]))

(defn- dissoc-in [m ks]
  (update-in m (butlast ks) dissoc (last ks)))

(deftest analyze-test
  (testing "Number literal"
    (is (= {:op :const
            :env {:context :ctx/expr :locals {}}
            :type :number
            :literal? true
            :val 1
            :form 1
            :top-level true}
           (dissoc-in (ana.py/analyze '1) [:env :ns])))))
