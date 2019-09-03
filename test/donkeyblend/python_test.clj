(ns donkeyblend.python-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [donkeyblend.python :as py]))

(defmacro is-str=
  "Provides slightly better error messages by showing where the string mismatch occurs
   Lays out each arg character by character by character along side each other making
  the first column whether all characters at that index are equal or not argument. 
  
    user=> (is-str= \"abc\" \"abc\" \"abd\")
    true a a a
    true b b b
    false c c d
   "
  [& args]
  `(is (= ~@args)
       (let [char-equal?# (map = ~@args)
             table# (map vector char-equal?# ~@args)]
         (reduce
           (fn [s# row#] (str s# (str/join " " row#) "\n"))
           "" table#))))

(deftest import-test
  (is-str= (py/import "bpy")
           "import bpy"))

(deftest clj->py-literal-test
  (is-str= (py/clj->py-literal "asd")
           "'asd'")
  (is (= (py/clj->py-literal 1729)
         1729))
  (is (= (py/clj->py-literal :key)
         "key"))
  (is (=  (py/clj->py-literal '(:get "Cube"))
         '("get" "'Cube'")))
  (is (=  (py/clj->py-literal [:get "Cube"])
         ["get" "'Cube'"])))

(deftest double-quote-test
  (is-str= (py/surround "s" "'") "'s'"))

(deftest fn-invoke
  (is-str= (py/fn-invoke "foo" 1) "foo(1)")
  (is-str= (py/fn-invoke "foo" 1 "'string'" "var") "foo(1, 'string', var)")
  (is-str= (apply py/fn-invoke "foo" [1 "'string'" "var"])
           "foo(1, 'string', var)"))

(deftest assign-test
  (is-str= (py/assign "foo" "s") "foo = s"))

(deftest dict-test
  (is-str= (py/dict {:a 1 :b "s" :c :var})
           "{'a': 1, 'b': 's', 'c': var}"))

(deftest print-test
  (testing "Single arg string"
    (is-str= (py/print "Hello world!")
             "print('Hello world!')"))
  (testing "Multi arg strings"
    (is-str= (py/print "Hello" "world!")
             "print('Hello', 'world!')"))
  (testing "Multi arg"
    (is-str= (py/print "Hello" "world!" :x 1)
             "print('Hello', 'world!', x, 1)")))

(deftest set-bl-info-test
  (is-str= (py/set-bl-info {:name "Move X Axis" :category "Object"})
           "bl_info = {'name': 'Move X Axis', 'category': 'Object'}"))

(deftest attr-test
  (is-str= (py/attr :bpy)
           "bpy")
  (is-str= (py/attr :bpy :data)
           "bpy.data")
  (is-str= (py/attr :bpy :data :objects)
           "bpy.data.objects")
  (is-str= (py/attr :bpy :data :objects [:get "Cube"])
           "bpy.data.objects.get('Cube')")
  (is-str= (py/attr :bpy :data :objects [:get "Cube"] :data :vertices [:index 0] :co :x)
           "bpy.data.objects.get('Cube').data.vertices.index(0).co.x"))
