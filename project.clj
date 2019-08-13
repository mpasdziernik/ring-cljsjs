(defproject ring-cljsjs "0.2.0-SNAPSHOT"
  :description "Ring middleware to serve assets from Cljsjs packages"
  :url "https://github.com/deraen/ring-cljsjs"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.7.1"]]
  :profiles
  {:dev {:dependencies [[cljsjs/react-mdl "1.4.3-0"]
                        [ring/ring-mock "0.2.0"]]}})
