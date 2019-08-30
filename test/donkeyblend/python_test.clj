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

(deftest attr-test
  (is (= (py/attr :bpy)
         "bpy"))
  (is (= (py/attr :bpy :data)
         "bpy.data"))
  (is (= (py/attr :bpy :data :objects)
         "bpy.data.objects"))
  #_(is (= (py/attr :bpy :data :objects (:get "Cube"))
           "bpy.data.objects[\"Cube\"]"))
  #_(is (= (:+= 1 (py/attr :bpy :data :objects (:get "Cube") :data :vertices (:get 0) :co :x))
           "bpy.data.objects[\"Cube\"].data.vertices[0].co.x += 1.0")))
