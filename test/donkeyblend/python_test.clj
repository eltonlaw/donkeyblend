(ns donkeyblend.python-test
  (:require [clojure.test :refer [deftest is testing]]
            [donkeyblend.python :as py]))

(deftest import-test
  (is (= (py/import "bpy")
         "import bpy")))

(deftest double-quote
  (is (= (py/double-quote "s")
         "\"s\"")))

(deftest print-test
  (testing "Single arg"
    (is (= (py/print "Hello world!")
           "print(\"Hello world!\")")))
  (testing "Multi arg"
    (is (= (py/print "Hello" "world!")
           "print(\"Hello\", \"world!\")")))
  (testing "Multi arg"
    (is (= (py/print "Hello" "world!" :x)
           "print(\"Hello\", \"world!\", x)"))))
