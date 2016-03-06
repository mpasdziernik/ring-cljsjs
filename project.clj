(defproject ring-cljsjs "0.1.0"
  :description "Ring middleware to serve assets from Cljsjs packages"
  :url "https://github.com/deraen/ring-cljsjs"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.4.0"]]
  :profiles
  {:dev {:dependencies [[cljsjs/react-mdl "1.4.3-0"]
                        [ring/ring-mock "0.2.0"]]}})
