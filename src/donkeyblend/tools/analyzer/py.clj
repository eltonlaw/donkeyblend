(ns donkeyblend.tools.analyzer.py
  "Analyzer for clojure code, extends tools.analyzer with Python specific passes/forms
  **focus is on blender's python interface**"
  (:refer-clojure :exclude [var? macroexpand-1 macroexpand])
  (:require [clojure.tools.analyzer
             :as ana
             :refer [analyze analyze-in-env wrapping-meta analyze-fn-method]
             :rename {analyze -analyze}]
            [clojure.tools.analyzer
             [env :as env :refer [*env*]]]))

(defn macroexpand-1 [form env] nil)

(defn parse [[op & args] env] nil)

(defn create-var [sym env] nil)

(defn var? [obj] nil)

(defn empty-env [] {})

(defn global-env [] (atom {}))

(defn run-passes [ast] ast)

(defn analyze
  "Analyzes a clojure form using tools.analyzer augmented with
  the Python specific special ops and returns an AST"
  ([form] (analyze form (empty-env) {}))
  ([form env] (analyze form env {}))
  ([form env opts]
   (binding [ana/macroexpand-1 macroexpand-1
             ana/create-var    create-var
             ana/parse         parse
             ana/var?          var?]
     (env/ensure (global-env)
       (run-passes (-analyze form env))))))
