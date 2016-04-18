(ns clojure-getting-started.web
  (:require
    [clojure.java.jdbc :as jdbc]
    [ticks.db :as db]
    [compojure.core :refer [defroutes GET]]
    [compojure.handler :refer [site]]
    [compojure.route :as route]
    [ring.adapter.jetty :as jetty]
    [environ.core :refer [env]]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello from Heroku"})

(defroutes app
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
