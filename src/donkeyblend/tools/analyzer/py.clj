(ns donkeyblend.tools.analyzer.py
  "Analyzer for clojure code, extends tools.analyzer with Python specific passes/forms
  **DOES NOT AIM TO COVER THE WHOLE LANGUAGE, FOCUS IS ON BLENDER'S PYTHON INTERFACE"
  (:refer-clojure :exclude [macroexpand-1 macroexpand])
  (:require [clojure.tools.analyzer
             :as ana
             :refer [analyze analyze-in-env wrapping-meta analyze-fn-method]
             :rename {analyze -analyze}]
            [clojure.tools.analyzer
             [env :as env :refer [*env*]]]))

(defn ^:dynamic run-passes
  "Function that will be invoked on the AST tree immediately after it has been constructed"
  [ast]
  ast)

(defn empty-env
  "Returns an empty env map"
  []
  {:context    :ctx/expr
   :locals     {}
   :ns         (ns-name *ns*)})

(defn global-env
  "Commmon keys are `:namespaces`
  
  - `tools.analyzer.js` has `:js-dependency-index`
  - `tools.analyzer.jvm` has `:update-ns-map!`"
  []
  (atom {:namespaces     (build-ns-map)

         :update-ns-map! (fn update-ns-map! []
                           (swap! *env* assoc-in [:namespaces] (build-ns-map)))})
  (atom {:namespaces (merge '{goog {:mappings {}, :js-namespace true, :ns goog}
                              Math {:mappings {}, :js-namespace true, :ns Math}}
                            @core-env)
         :js-dependency-index (deps/js-dependency-index {})}))

(defn macroexpand-1
  "If form represents a macro form or an inlineable function,returns its expansion,
   else returns form."
  ([form] (macroexpand-1 form (empty-env)))
  ([form env]
   (env/ensure (global-env))))

(defn analyze
  "Analyzes a clojure form using tools.analyzer augmented with the Python specific special ops
   and returns its AST, after running #'run-passes on it.)"
  ([form] (analyze form (empty-env) {}))
  ([form env] (analyze form env {}))
  ([form env opts]
   (with-bindings (merge {#'ana/macroexpand-1 macroexpand-1}
                         (:bindings opts))
     (-analyze form env))))
