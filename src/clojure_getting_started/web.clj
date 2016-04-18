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
       (splash (sql/query
         "postgres://rzdnlqvtihaywe:h8YMM9hPA-0CEPGPHvUJ23lo6r@ec2-54-228-246-19.eu-west-1.compute.amazonaws.com:5432/d3kf2u2jt4bnt3"
         ["select * from salesforcecloj.case"]))))


(defn -main [& [port]]
 (let [port (Integer. (or port (env :port) 5000))]
   (jetty/run-jetty (site #'app) {:port port :join? false})))


;; For interactive development:
;; (.stop server)
;; (def server (-main))
