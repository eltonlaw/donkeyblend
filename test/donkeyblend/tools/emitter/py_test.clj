(ns donkeyblend.tools.emitter.py-test
  (:require [clojure.test :refer [deftest testing is]]
            [donkeyblend.tools.analyzer.py :as analyzer]
            [donkeyblend.tools.emitter.py :as emitter]
            [donkeyblend.reader :as reader]))

(defn- to-py [s]
  (-> (reader/read-string s)
      analyzer/analyze
      emitter/emit))

(deftest emit-const
  (is (= "True" (to-py "true")))
  (is (= "1" (to-py "1")))
  (is (= "1.5" (to-py "1.5")))
  (is (= "\"a\"" (to-py ":a"))))

(deftest emit-invoke-import
  (is (= "import bpy"
         (to-py "(import bpy)")))
  (is (= "import bpy\nimport os"
         (to-py "(import bpy os)")))
  (is (= "import bpy\nimport os\nfrom math import pi,sin,cos"
         (to-py "(import bpy os (math pi sin cos))"))))
