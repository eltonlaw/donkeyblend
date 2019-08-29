(set-env!
  :resource-paths #{"src" "test"}
  :dependencies '[[adzerk/boot-reload "0.6.0" :scope "test"]
                  [adzerk/boot-test "1.2.0" :scope "test"]
                  [com.taoensso/timbre "4.10.0"]
                  [org.clojure/clojure "1.10.0"]
                  [org.clojure/tools.nrepl "0.2.13"]])

(task-options!
  pom {:project 'donkeyblend
       :version "0.1.0-SNAPSHOT"
       :description "Clojure scripting interface for Blender"}
  jar {:main 'donkeyblend.core})

(require '[donkeyblend.core :as donkeyblend]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-test :refer :all])

(deftask dev []
  (repl :no-color true :init-ns 'donkeyblend.core))

(deftask local-install []
  (comp (pom) (jar) (install)))
