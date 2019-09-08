(ns donkeyblend.python.str-utils-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [donkeyblend.python.str-utils :as str-utils]
            [donkeyblend.test-utils :refer [is-str=]]))

(deftest clj->py-literal-test
  (is-str= (str-utils/clj->py-literal "asd")
           "'asd'")
  (is (= (str-utils/clj->py-literal 1729)
         1729))
  (is (= (str-utils/clj->py-literal :key)
         "key"))
  (is (= (str-utils/clj->py-literal '(:get "Cube"))
         '("get" "'Cube'")))
  (is (= (str-utils/clj->py-literal [:get "Cube"])
         ["get" "'Cube'"])))

(deftest double-quote-test
  (is-str= (str-utils/surround "s" "'") "'s'"))

(deftest fn-invoke
  (is-str= (str-utils/fn-invoke "foo" 1) "foo(1)")
  (is-str= (str-utils/fn-invoke "foo" 1 "'string'" "var") "foo(1, 'string', var)")
  (is-str= (apply str-utils/fn-invoke "foo" [1 "'string'" "var"])
           "foo(1, 'string', var)"))
