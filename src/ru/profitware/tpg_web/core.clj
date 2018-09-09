(ns ru.profitware.tpg-web.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [environ.core :refer [env]]
            [ru.profitware.tpg-web.page :as page]
            [ru.profitware.tpg-web.tpl :as tpl])
  (:gen-class))

(def index-page
  "home")

(def static-root
  "https://theprofitwaregroup.github.io/tpg_su_cdn/default/")

(def hire-us-form
  "https://docs.google.com/forms/d/e/1FAIpQLSepI581ewDX-XA_qw95IxM2F84gvWyCyTzSxix-d8p-6FtCkQ/viewform")

(defn get-redirect [page-id server-name]
  (get {"f" "https://raw.githubusercontent.com/prde/prde-install-fedora/master/install.sh"
        "eventflow" (str "https://" server-name "/platform#eventflow")}
       page-id))

(defn- render-map-by-current-map [current-map]
  (let [language (keyword (get current-map :language :en))
        content (get page/xml-content language)
        content-page (tpl/render-content content current-map)
        content-menu (tpl/render-menu content current-map)
        content-title (tpl/render-title content current-map)]
    (assoc current-map
           :contentPage content-page
           :contentMenu content-menu
           :contentTitle content-title)))

(defroutes app-routes
  (GET (str "/:page-id{|" (s/join "|" page/site-menus) "}") [page-id :as {server-name :server-name}]
       (let [filled-page-id (if (empty? page-id) index-page page-id)
             language (if (= "tpg.su" server-name) "ru" "en")
             current-map {:pageId filled-page-id
                          :indexPage index-page
                          :isIndex (= filled-page-id index-page)
                          :staticRoot static-root
                          :hireUsForm hire-us-form
                          :serverName server-name
                          :language language}]
         (if (.contains ["localhost" "tpg.su" "profitware.tech"]
                        server-name)
           (tpl/render-page
            (render-map-by-current-map current-map))
           (response/redirect (str "https://"
                                   (if (.endsWith server-name ".ru")
                                     "tpg.su"
                                     "profitware.tech")
                                   "/"
                                   page-id)))))

  (GET "/menus.js" [:as {server-name :server-name}]
       (let [language (if (= "tpg.su" server-name) "ru" "en")]
         (response/content-type (response/response (str "tpgMenuCallback("
                                                        (json/write-str (page/all-menus-map language))
                                                        ");"))
                                "application/javascript")))

  (GET "/:page-id{f|eventflow}" [page-id :as {server-name :server-name}]
       (response/redirect (get-redirect page-id server-name)))

  (route/not-found (tpl/render-page
                    (render-map-by-current-map {:pageId "404"
                                                :indexPage index-page
                                                :isIndex false
                                                :staticRoot static-root}))))

(def app app-routes)

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
