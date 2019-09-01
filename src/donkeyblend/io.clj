(ns donkeyblend.io
  (:require [clojure.java.io :refer [file make-parents]]))

(def ^{:dynamic true :private true} *filepath*)

(defn write!
  ([data] (write! data {}))
  ([data opts]
   (let [{:keys [filename filepath]
          :or {filename (str "donkeyblend_" (gensym) ".py")
               filepath "scripts/startup"}} opts]
     (binding [*filepath* (-> (file filepath) .getCanonicalPath)]
       (let [p (str *filepath* "/" filename)]
         ;; Will create the directory if it doesn't exist
         (make-parents p)
         (spit p data)
         p)))))
