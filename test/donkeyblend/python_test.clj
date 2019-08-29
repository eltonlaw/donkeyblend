(ns donkeyblend.python-test
  (:require [clojure.test :refer [deftest is testing]]
            [donkeyblend.python :as py]))

(deftest import-test
  (is (= (py/import "bpy")
         "import bpy")))

(deftest clj-value->py-value-test
  (is (= (py/clj-value->py-value "asd")
         "\"asd\""))
  (is (= (py/clj-value->py-value 1729)
         1729))
  (is (= (py/clj-value->py-value :key)
         "key")))

(deftest double-quote-test
  (is (= (py/double-quote "s")
         "\"s\"")))

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

(deftest property-test
  (is (= true true)))
  
