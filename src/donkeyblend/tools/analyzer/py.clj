(ns donkeyblend.tools.analyzer.py
  "Analyzer for clojure code, extends tools.analyzer with Python specific passes/forms
  **DOES NOT AIM TO COVER THE WHOLE LANGUAGE, FOCUS IS ON BLENDER'S PYTHON INTERFACE"
  (:refer-clojure :exclude [macroexpand-1 macroexpand])
  (:require [clojure.tools.analyzer
             :as ana
             :refer [analyze analyze-in-env wrapping-meta analyze-fn-method]
             :rename {analyze -analyze}]
            [clojure.tools.analyzer
             [utils :refer [mmerge] :as u]
             [env :as env :refer [*env*]]]))

(defn ^:dynamic run-passes
  "Function that will be invoked on the AST tree immediately after it has been constructed"
  [ast]
  ast)

(defn build-ns-map []
  (into {} (mapv #(vector (ns-name %)
                          {:mappings (merge (ns-map %) {'in-ns #'clojure.core/in-ns
                                                        'ns    #'clojure.core/ns})
                           :aliases  (reduce-kv (fn [a k v] (assoc a k (ns-name v)))
                                                {} (ns-aliases %))
                           :ns       (ns-name %)})
                 (all-ns))))

(defn global-env []
  (atom {:namespaces     (build-ns-map)
         :update-ns-map! (fn []
                           (swap! *env* assoc-in [:namespaces] (build-ns-map)))}))

(defmulti parse
  "Extension to tools.analyzer/-parse for CLJS special forms"
  (fn [[op & rest] env] op))

(defmethod parse :default
  [form env]
  (ana/-parse form env))

(defn create-var
  "Creates a var map for sym and returns it."
  [sym {:keys [ns]}]
  (with-meta {:op   :var
              :name sym
              :ns   ns}
    (meta sym)))

(defn macroexpand-1
  "If form represents a macro form or an inlineable function,returns its expansion,
   else returns form."
  ([form] (macroexpand-1 form (ana/empty-env)))
  ([form env]
   (env/ensure (global-env)
     form)))

(defn analyze-form
  [form env]
  (ana/-analyze-form form env))

(defn analyze
  "Analyzes a clojure form using tools.analyzer augmented with the Python specific special ops
   and returns its AST, after running #'run-passes on it.)"
  ([form] (analyze form (ana/empty-env) {}))
  ([form env] (analyze form env {}))
  ([form env opts]
   (with-bindings (merge {#'ana/macroexpand-1 macroexpand-1
                          #'ana/analyze-form  analyze-form
                          #'ana/create-var    create-var
                          #'ana/parse         parse
                          #'ana/var?          var?}
                         (:bindings opts))
     (env/ensure (global-env)
       (swap! env/*env* mmerge (select-keys opts [:passes-opts]))
       (run-passes (-analyze form env))))))
