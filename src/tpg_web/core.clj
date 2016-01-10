(ns tpg-web.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [tpg-web.page :as page]
            [tpg-web.tpl :as tpl])
  (:gen-class))

(def index-page
  "home")

(def static-root
  "http://theprofitwaregroup.github.io/tpg_su_cdn/default/")

(defn- render-map-by-current-map
  [current-map]
  (let [
    content-page (tpl/render-content page/xml-content current-map)
    content-menu (tpl/render-menu page/xml-content current-map)
    content-title (tpl/render-title page/xml-content current-map)]
    (assoc
      current-map
      :contentPage content-page
      :contentMenu content-menu
      :contentTitle content-title)))

(defroutes app
  (GET (str "/:page-id{|" (s/join "|" page/site-menus) "}")
    [page-id]
    (let [
      filled-page-id (if (empty? page-id) index-page page-id)
      current-map {
        :pageId filled-page-id
        :indexPage index-page
        :isIndex (= filled-page-id index-page)
        :staticRoot static-root}]
      (tpl/render-page
        (render-map-by-current-map current-map))))
  (route/not-found
    (tpl/render-page
      (render-map-by-current-map {
        :pageId "404"
        :indexPage index-page
        :isIndex false
        :staticRoot static-root
        }))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
