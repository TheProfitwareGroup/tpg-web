(ns ru.profitware.tpg-web.tpl
  (:require [clojure.java.io :as io]
            [saxon :as x]
            [clostache.parser :as m])
  (:gen-class))

(defn- compile-template [template]
  (-> template
      io/resource
      io/file
      x/compile-xslt))

(defn render-content [xml-node map-data]
  (let [rendered-content ((compile-template "templates/content.xsl") xml-node map-data)]
    rendered-content))

(def render-menu
  (compile-template "templates/menu.xsl"))

(def render-title
  (compile-template "templates/title.xsl"))

(defn render-page [data]
  (m/render-resource "templates/page.mustache" data))
