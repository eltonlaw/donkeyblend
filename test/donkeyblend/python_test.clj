(ns donkeyblend.python-test
  (:require [clojure.test :refer [deftest is testing]]
            [donkeyblend.python :as py]))

(deftest import-test
  (is (= (py/import "bpy")
         "import bpy")))

(deftest clj->py-literal-test
  (is (= (py/clj->py-literal "asd")
         "\"asd\""))
  (is (= (py/clj->py-literal 1729)
         1729))
  (is (= (py/clj->py-literal :key)
         "key")))

(deftest double-quote-test
  (is (= (py/surround "s" "\"") "\"s\"")))

(deftest fn-invoke
  (is (= (py/fn-invoke :foo 1) "foo(1)"))
  (is (= (py/fn-invoke :foo 1 "string" :var) "foo(1, \"string\", var)"))
  (is (= (apply py/fn-invoke :foo [1 "string" :var])
         "foo(1, \"string\", var)")))

(deftest assign-test
  (is (= (py/assign :foo "s") "foo = \"s\""))
  (is (= (py/assign :foo 1) "foo = 1"))
  (is (= (py/assign :foo :bar) "foo = bar")))

(deftest print-test
  (testing "Single arg string"
    (is (= (py/print "Hello world!")
           "print(\"Hello world!\")")))
  (testing "Multi arg strings"
    (is (= (py/print "Hello" "world!")
           "print(\"Hello\", \"world!\")")))
  (testing "Multi arg"
    (is (= (py/print "Hello" "world!" :x 1)
           "print(\"Hello\", \"world!\", x, 1)"))))
