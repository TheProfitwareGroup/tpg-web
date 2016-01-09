(ns tpg-web.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [tpg-web.page :as page]
            [tpg-web.tpl :as tpl])
  (:gen-class))

(def default-page
  "home")

(def static-root
  "http://theprofitwaregroup.github.io/tpg_su_cdn/default/")

(defroutes app
  (GET "/:page-id{|home|platform|eventflow|weblab|support|about}"
    [page-id]
    (let [
      filled-page-id (if (empty? page-id) default-page page-id)
      current-map {
        :pageId filled-page-id
        :indexPage default-page
        :isIndex (= filled-page-id default-page)
        :staticRoot static-root}
      content-page (tpl/render-content page/get-content current-map)
      content-menu (tpl/render-menu page/get-content current-map)
      content-title (tpl/render-title page/get-content current-map)
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
