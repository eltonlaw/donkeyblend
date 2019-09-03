(ns donkeyblend.python-test
  (:require [clojure.test :refer [deftest is testing]]
            [donkeyblend.python :as py]))

;; TODO: Some test util for string comparison errors
(deftest import-test
  (is (= (py/import "bpy")
         "import bpy")))

(deftest clj->py-literal-test
  (is (= (py/clj->py-literal "asd")
         "'asd'"))
  (is (= (py/clj->py-literal 1729) 1729))
  (is (= (py/clj->py-literal :key)
         "key"))
  (is (= (py/clj->py-literal '(:get "Cube"))
         '("get" "'Cube'")))
  (is (= (py/clj->py-literal [:get "Cube"])
         ["get" "'Cube'"])))

(deftest double-quote-test
  (is (= (py/surround "s" "'") "'s'")))

(deftest fn-invoke
  (is (= (py/fn-invoke "foo" 1) "foo(1)"))
  (is (= (py/fn-invoke "foo" 1 "'string'" "var") "foo(1, 'string', var)"))
  (is (= (apply py/fn-invoke "foo" [1 "'string'" "var"])
         "foo(1, 'string', var)")))

(deftest assign-test
  (is (= (py/assign "foo" "s") "foo = s"))
  #_(is (= (py/assign :foo 1) "foo = 1"))
  #_(is (= (py/assign :foo :bar) "foo = bar")))

(deftest dict-test
  (is (= (py/dict {:a 1 :b "s" :c :var})
         "{'a': 1, 'b': 's', 'c': var}")))

(deftest print-test
  (testing "Single arg string"
    (is (= (py/print "Hello world!")
           "print('Hello world!')")))
  (testing "Multi arg strings"
    (is (= (py/print "Hello" "world!")
           "print('Hello', 'world!')")))
  (testing "Multi arg"
    (is (= (py/print "Hello" "world!" :x 1)
           "print('Hello', 'world!', x, 1)"))))

(deftest set-bl-info-test
  (is (= (py/set-bl-info {:name "Move X Axis" :category "Object"})
         "bl_info = {'name': 'Move X Axis', 'category': 'Object'}")))

(deftest attr-test
  (is (= (py/attr :bpy)
         "bpy"))
  (is (= (py/attr :bpy :data)
         "bpy.data"))
  (is (= (py/attr :bpy :data :objects)
         "bpy.data.objects"))
  (is (= (py/attr :bpy :data :objects [:get "Cube"])
         "bpy.data.objects.get('Cube')"))
  (is (= (py/attr :bpy :data :objects [:get "Cube"] :data :vertices [:index 0] :co :x)
         "bpy.data.objects.get('Cube').data.vertices.index(0).co.x")))
