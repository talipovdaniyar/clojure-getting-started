(ns clojure-getting-started.web
  (:require
    [clojure.java.jdbc :as sql]
    [compojure.core :refer [defroutes GET]]
    [compojure.handler :refer [site]]
    [compojure.route :as route]
    [ring.adapter.jetty :as jetty]
    [environ.core :refer [env]]))


(defn splash [data]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str "Hello from Heroku" data)})

(defroutes app
  (GET "/" []
       (splash (sql/query (System/getenv "DATABASE_URL")
       ["select * from salesforcecloj.case"]))))


(defn -main [& [port]]
 (let [port (Integer. (or port (env :port) 5000))]
   (jetty/run-jetty (site #'app) {:port port :join? false})))


;; For interactive development:
;; (.stop server)
;; (def server (-main))
