(ns donkeyblend.reader-test
  (:require [clojure.test :refer [deftest testing is]]
            [donkeyblend.reader :as reader]))

(deftest test-read-string
  (testing "empty string"
    (is (= nil (reader/read-string ""))))
  (testing "string with single object"
    (is (= '(ns my-namespace (:import bpy) (:require [utils :as u]))
           (reader/read-string
             (str "(ns my-namespace\n"
                  ";; some comment \n"
                  "  (:import bpy)\n"
                  "  (:require [utils :as u]))"))))))
