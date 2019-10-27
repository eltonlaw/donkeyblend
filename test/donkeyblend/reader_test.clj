(ns donkeyblend.reader-test
  (:refer-clojure :exclude [read-string])
  (:require [clojure.test :refer [deftest testing is]]
            [donkeyblend.reader :as reader]))

(deftest test-read-string
  (testing "empty string"
    (is (= '() (reader/read-string ""))))
  (testing "string with single object"
    (is (= '((ns my-namespace (:import bpy) (:require [utils :as u])))
           (reader/read-string
             (str "(ns my-namespace\n"
                  ";; some comment \n"
                  "  (:import bpy)\n"
                  "  (:require [utils :as u]))")))))
  (testing "string with multiple objects"
    (is (= '({} [])
           (reader/read-string
             (str "{} []"))))))
