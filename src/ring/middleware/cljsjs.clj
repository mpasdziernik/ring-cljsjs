(ns ring.middleware.cljsjs
  (:require [clojure.java.io :as io]
            [ring.middleware.head :as head]
            [ring.util.codec :as codec]
            [ring.util.request :as req]
            [ring.util.response :as resp]
            [ring.util.mime-type :as mime])
  (:import [java.util.jar JarFile]))

(def ^:private cljsjs-pattern
  #"cljsjs/([^/]+)/([^/]+)/(.*)")

(defn- asset-path [prefix resource]
  (if-let [[_ name type path] (re-matches cljsjs-pattern resource)]
    (str prefix "/" name "/" path)))

(defn- asset-map
  "Build map of uri to classpath uri.

  Finds all resources in cljsjs classpath prefix and parses those paths
  to build the map."
  [prefix]
  (->> (.getResources (.getContextClassLoader (Thread/currentThread)) "cljsjs")
       enumeration-seq
       (mapcat
         (fn [url]
           (if (= "jar" (.getProtocol url))
             (let [[_ jar] (re-find #"^file:(.*\.jar)\!/.*$" (.getPath url))]
               (->> (enumeration-seq (.entries (JarFile. (io/file jar))))
                    (remove #(.isDirectory %))
                    (map #(.getName %))
                    (filter #(.startsWith % "cljsjs")))))))
       set
       (keep (juxt (partial asset-path prefix) identity))
       (into {})))

(defn- request-path [request]
  (codec/url-decode (req/path-info request)))

(defn- add-mime-type [response path mime-types]
  (if-let [mime-type (mime/ext-mime-type path mime-types)]
    (resp/content-type response mime-type)
    response))

(defn wrap-cljsjs
  ([handler]
   (wrap-cljsjs handler nil))
  ([handler {:keys [prefix mime-types]
             :or {prefix "/cljsjs" mime-types {}}}]
   (let [assets (asset-map prefix)]
     (fn [request]
       (if (#{:head :get} (:request-method request))
         (if-let [path (assets (request-path request))]
           (-> (resp/resource-response path)
               (add-mime-type path mime-types)
               (head/head-response request))
           (handler request))
         (handler request))))))
