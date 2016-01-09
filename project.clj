(defproject tpg-web "0.1.0-SNAPSHOT"
  :description "The Profitware Group Website"
  :url "http://tpg.su/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "1.0.0"]
                 [compojure "1.4.0"]
                 [clojure-saxon "0.9.4"]
                 [de.ubercode.clostache/clostache "1.4.0"]]
  :plugins [[lein-ring "0.9.7"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler tpg-web.core/app}
  :main ^:skip-aot tpg-web.core
  :uberjar-name "tpg-web.jar"
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
