(ns donkeyblend.compile-test
  (:require [clojure.test :refer [deftest is]]
            [donkeyblend.compile :as c]
            [donkeyblend.python.lang :as lang]
            [donkeyblend.test-utils :refer [is-str=]]))

(c/defscript my-script
  (lang/import "bpy")
  (lang/print "h"))

(deftest defscript-test
  (is-str= "import bpy\nprint('h')"
           my-script))
