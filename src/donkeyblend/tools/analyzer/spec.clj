(ns donkeyblend.tools.analyzer.spec)

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
