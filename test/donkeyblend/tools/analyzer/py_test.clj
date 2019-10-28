(ns donkeyblend.tools.analyzer.py-test
  (:require [clojure.test :refer [deftest testing is]]
            [donkeyblend.tools.analyzer.py :as ana.py]))

(deftest empty-env
  (testing "verify no spec errors"
    (is (= (ana.py/empty-env)
           {:context :ctx/statement
            :locals  {}
            :ns      ana.py/*ns*}))))
