(defproject ru.profitware/tpg-web "0.1.0-SNAPSHOT"
  :description "The Profitware Group Website"
  :url "https://tpg.su/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [environ "1.0.0"]
                 [compojure "1.4.0"]
                 [clojure-saxon "0.9.4"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]]
  :plugins [[lein-ring "0.9.7"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler ru.profitware.tpg-web.core/app}
  :main ^:skip-aot ru.profitware.tpg-web.core
  :uberjar-name "tpg-web.jar"
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
