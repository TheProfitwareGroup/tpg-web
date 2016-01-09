(ns tpg-web.page
  (:require [clojure.java.io :as io]
            [saxon :as x])
  (:gen-class))

(defn- compile-content
  [content]
  (-> content io/resource io/file x/compile-xml))

(def get-content
  (compile-content "content/content.xml"))
