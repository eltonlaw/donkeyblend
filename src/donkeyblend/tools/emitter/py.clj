(ns donkeyblend.tools.emitter.py
  (:require [clojure.string :as str]
            [clojure.tools.analyzer.ast :as ana.ast]))

(defmulti -emit (fn [{:keys [op]}] op))

(defn emit
  "AST -> Python"
  [ast]
  (-emit ast))

(defmethod -emit :const
  [{:keys [type val]}]
  (case type
    :bool (str/capitalize val)
    :keyword (str "\"" (name val) "\"")
    :number (str val)
    (throw (Exception. (format "Emitting :const - unknown type %s with value %s" type val)))))

(defn -emit-invoke-import
  "Can't handle the deeply nested structure that clojure imports enable, can at most only
  do one layer of importing from a module"
  [{:keys [args]}]
  (letfn [(from-x-import-y [ast]
            (let [[x & ys] (:form ast)]
              (format "from %s import %s" x (str/join "," ys))))
          (import-x [ast]
            (str "import " (:class ast)))
          (dispatch [ast]
            (case (:op ast)
              :maybe-class (import-x ast)
              :invoke (from-x-import-y ast)))]
    (str/join "\n" (map dispatch args))))

(defn -emit-arithmetic [operator ast]
  (let [args-py-str (->> (ana.ast/children ast)
                         rest
                         (map -emit))]
    (->> args-py-str
         (str/join operator)
         (format "(%s)"))))

(defmethod -emit :invoke
  [{:keys [fn] :as ast}]
  (let [fn-name (name (get-in fn [:meta :name]))]
    (case fn-name
      "import" (-emit-invoke-import ast)
      ("+" "-" "*" "/")  (-emit-arithmetic fn-name ast)
      (throw (Exception. (format "Emitting :invoke - unknown var %s" fn-name))))))
