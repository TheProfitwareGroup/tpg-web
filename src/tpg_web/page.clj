(ns tpg-web.page
  (:require [clojure.java.io :as io]
            [saxon :as x])
  (:gen-class))

(defn- compile-content
  [content]
  (-> content io/resource io/file x/compile-xml))

(def xml-content
  (compile-content "content/content.xml"))

(def site-menus
  (x/query
    "distinct-values(//root/head/menu/a/@href[not(contains(.,'/'))])"
    xml-content))

(def all-menus-map
  {:menus (map
    vector
    (x/query
      "distinct-values(//root/head/menu/a/@href)"
      xml-content)
    (x/query
      "distinct-values(//root/head/menu/a)"
      xml-content))})
