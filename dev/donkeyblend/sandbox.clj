(ns donkeyblend.sandbox
  (:require [donkeyblend.core :as db]
            [donkeyblend.tools.analyzer.py :refer :all]
            [clojure.tools.analyzer
             :as ana
             :refer [analyze analyze-in-env wrapping-meta analyze-fn-method]
             :rename {analyze -analyze}]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.tools.nrepl.server :as nrepl]
            [clojure.pprint :as pp]
            [clojure.repl :refer :all]
            [rebel-readline.clojure.line-reader :as clj-line-reader]
            [rebel-readline.jline-api :as api]
            [rebel-readline.clojure.main :as rr-clj-main]))


(defonce nrepl-server (nrepl/start-server))
(spit "./.nrepl-port" (:port nrepl-server))

;; https://github.com/bhauman/rebel-readline/issues/151
(defn pprint
  "Print a syntax highlighted clojure value.

  This printer respects the current color settings set in the
  service.

  The `rebel-readline.jline-api/*line-reader*` and
  `rebel-readline.jline-api/*service*` dynamic vars have to be set for
  this to work."
  [x]
  (binding [*out* (.. api/*line-reader* getTerminal writer)]
    (try
      (print (api/->ansi (clj-line-reader/highlight-clj-str (with-out-str (pp/pprint x)))))
      (catch java.lang.StackOverflowError e
        (pp/pprint x)))))

(comment
  ;; ANA.JVM
  (pprint (ana.jvm/analyze '5))
  (pprint (ana.jvm/analyze '(print 5)))
  (pprint (ana.jvm/analyze '(let [x 5] (print x))))
  (pprint (ana.jvm/analyze '(+ 1 1)))
  (pprint (ana.jvm/analyze '(+ 1 1)))
  (keys (ana.jvm/analyze '(+ 1 1)))
  ;; ANA.PY
  (def x 123)
  (ana.jvm/analyze '(+ 1 1))
  (pprint (analyze '(+ 1 1)))
  ;; MACRO
  (analyze '(let [x 1] (println x)))
  nil)
