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

(defroutes app
  (GET (str "/:page-id{|" (s/join "|" page/site-menus) "}")
    [page-id]
    (let [
      filled-page-id (or page-id index-page)
      current-map {
        :pageId filled-page-id
        :indexPage index-page
        :isIndex (= filled-page-id index-page)
        :staticRoot static-root}
      content-page (tpl/render-content page/xml-content current-map)
      content-menu (tpl/render-menu page/xml-content current-map)
      content-title (tpl/render-title page/xml-content current-map)
      render-map (assoc
        current-map
        :contentPage content-page
        :contentMenu content-menu
        :contentTitle content-title)
      ]
      (tpl/render-page render-map))
    )
  (route/not-found "<h1>Page not found</h1>"))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
