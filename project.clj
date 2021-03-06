(defproject launchpad-lightsout "1.0.0-SNAPSHOT"
  :description "A Lights Out game for the Noviation Launchpad."
  :url "https://github.com/dvberkel/launchpad-lightsout"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-launchpad "0.3.2"]
                 [automat "0.2.0-alpha1"]]
  :main ^:skip-aot launchpad-lightsout.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
