(ns ru.profitware.tpg-web.page
  (:require [clojure.java.io :as io]
            [clojure.set]
            [saxon :as x])
  (:gen-class))

(defn- compile-content [content]
  (-> content io/resource io/input-stream x/compile-xml))

(defn get-xml-content []
  (let [languages ["en" "ru"]]
    (into {}
          (map (fn [language]
                 {(keyword language) (compile-content (str "content/content_" language ".xml"))})
               languages))))

(def xml-content
  (get-xml-content))

(def site-menus
  (apply clojure.set/union
         (map (comp set (partial x/query
                                 "distinct-values(//root/head/menu/a/@href[not(contains(.,'/'))])"))
              (vals xml-content))))

(defn all-menus-map [language]
  (let [content (get xml-content
                     (keyword language))]
    {:menus (map vector
                 (x/query "distinct-values(//root/head/menu/a/@href)" content)
                 (x/query "distinct-values(//root/head/menu/a)" content))}))
