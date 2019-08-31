(ns donkeyblend.compile-test
  (:require [clojure.test :refer [deftest is]]
            [donkeyblend.compile :as c]
            [donkeyblend.python :as py]))

(c/defscript my-script
  (py/import "bpy")
  (py/print "h"))

(deftest defscript-test
  (is (= "import bpy\nprint(\"h\")"
         my-script)))
