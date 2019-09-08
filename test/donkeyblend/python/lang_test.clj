(ns donkeyblend.python.lang-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [donkeyblend.python.lang :as lang]
            [donkeyblend.test-utils :refer [is-str=]]))

(deftest import-test
  (is-str= (lang/import "bpy")
           "import bpy"))

(deftest assign-test
  (is-str= (lang/= "foo" "s") "foo = s"))

(deftest dict-test
  (is-str= (lang/dict {:a 1 :b "s" :c :var})
           "{'a': 1, 'b': 's', 'c': var}"))

(deftest print-test
  (testing "Single arg string"
    (is-str= (lang/print "Hello world!")
             "print('Hello world!')"))
  (testing "Multi arg strings"
    (is-str= (lang/print "Hello" "world!")
             "print('Hello', 'world!')"))
  (testing "Multi arg"
    (is-str= (lang/print "Hello" "world!" :x 1)
             "print('Hello', 'world!', x, 1)")))

(deftest attr-test
  (is-str= (lang/attr :bpy)
           "bpy")
  (is-str= (lang/attr :bpy :data)
           "bpy.data")
  (is-str= (lang/attr :bpy :data :objects)
           "bpy.data.objects")
  (is-str= (lang/attr :bpy :data :objects [:get "Cube"])
           "bpy.data.objects.get('Cube')")
  (is-str= (lang/attr :bpy :data :objects [:get "Cube"] :data :vertices [:index 0] :co :x)
           "bpy.data.objects.get('Cube').data.vertices.index(0).co.x"))
