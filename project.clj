(defproject ru.profitware/tpg-web "0.0.4"
  :description "The Profitware Group Website"
  :url "https://tpg.su/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.json "0.2.6"]
                 [environ "1.0.0"]
                 [compojure "1.5.1"]
                 [clojure-saxon "0.9.4"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-openshift "0.1.0"]
            [arohner/lein-docker "0.1.4"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler ru.profitware.tpg-web.core/app
         :port 8080}
  :main ^:skip-aot ru.profitware.tpg-web.core
  :uberjar-name "tpg-web.jar"
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :openshift {:namespace "tpg-web"
              :domains ["profitware.tech"
                        "tpg.su"
                        "dekoprofit.ru"]}
  :docker {:repo "profitware/tpg-web"})
