(ns ring.middleware.cljsjs-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [ring.middleware.cljsjs :refer :all]
            [ring.mock.request :as mock]))

(defn- slurp-response [handler uri]
  (some-> (mock/request :get uri) handler :body slurp))

(defn- slurp-cljsjs [path]
  (slurp (io/resource (str "cljsjs/" path))))

(deftest test-wrap-cljsjs
  (let [handler (wrap-cljsjs (constantly nil))]
    (is (nil? (handler (mock/request :get "/foo"))))
    (is (nil? (handler (mock/request :get "/cljsjs"))))
    (is (nil? (handler (mock/request :get "/cljsjs/bootstrap"))))
    (is (= (slurp-response handler "/cljsjs/react-mdl/material.min.css")
           (slurp-cljsjs "react-mdl/production/material.min.css")))))
