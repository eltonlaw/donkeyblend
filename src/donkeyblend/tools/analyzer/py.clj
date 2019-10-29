(ns donkeyblend.tools.analyzer.py
  "Analyzer for clojure code, extends tools.analyzer with Python specific passes/forms
  **focus is on blender's python interface**"
  (:refer-clojure :exclude [var? macroexpand-1 macroexpand *ns*])
  (:require [clojure.tools.analyzer
             :as ana
             :refer [analyze analyze-in-env wrapping-meta analyze-fn-method]
             :rename {analyze -analyze}]
            [clojure.tools.analyzer
             [env :as env :refer [*env*]]]
            [clojure.spec.alpha :as s]))

(s/def ::ns symbol?) ;; unanalyzed form
(s/def ::namespaces (s/map-of symbol? var?))
(s/def ::form clojure.lang.PersistentList) ;; unanalyzed form
(s/def ::locals (s/map-of symbol? ::ast))
(s/def ::context #{:ctx/expr :ctx/return :ctx/statement})
(s/def ::env (s/keys :req-un [::locals ::context ::ns]))
(s/def ::op keyword?)
(s/def ::children (s/coll-of keyword?)) ;; child nodes in execution order
(s/def ::ast (s/keys :req-un [::op ::form ::env]
                     :opt-un [::children]))

(defn parse [[op & args] env] nil)

(defn create-var [sym env] nil)

(defn var? [obj] nil)

(def ^:dynamic *ns* 'donkeyblend.user)

(s/fdef empty-env
  :ret (s/keys :req-un [::context ::locals ::ns]))

(defn empty-env []
  {:context :ctx/statement
   :locals {}
   :ns *ns*})

(s/fdef empty-global-env
  :ret (s/keys :req-un [::namespaces]))

(defn empty-global-env []
  {:namespaces {}})

(defn run-passes [ast] ast)

(s/fdef analyze
  :args (s/alt
          :1 (s/cat :form ::form)
          :2 (s/cat :form ::form
                    :env ::env)
          :3 (s/cat :form ::form
                    :env ::env
                    :opts map?))
  :ret ::ast)

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
     (env/ensure (atom (empty-global-env))
       (run-passes (-analyze form env))))))
