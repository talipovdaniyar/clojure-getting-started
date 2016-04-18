(ns clojure-getting-started.web
  (:require
    [clojure.java.jdbc :as sql]
    [clojure-getting-started.db :as db]
    [compojure.core :refer [defroutes GET]]
    [compojure.handler :refer [site]]
    [compojure.route :as route]
    [ring.adapter.jetty :as jetty]
    [environ.core :refer [env]]))


(defn migrated? []
  (-> (jdbc/query db/spec
    [(str "select count(*) from information_schema.tables "
    "where table_name='ticks'")])
    first :count pos?))

(defn migrate []
  (when (not (migrated?))
  (print "Creating database structure...") (flush)
  (jdbc/db-do-commands db/spec
    (jdbc/create-table-ddl
      :ticks
      [:id :serial "PRIMARY KEY"]
      [:body :varchar "NOT NULL"]
      [:tick :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
      (println " done")))

(defroutes app
  (GET "/" []
       (str "Test: " (sql/query "postgres://rzdnlqvtihaywe:h8YMM9hPA-0CEPGPHvUJ23lo6r@ec2-54-228-246-19.eu-west-1.compute.amazonaws.com:5432/d3kf2u2jt4bnt3"
       ["select * from salesforce.case"])))


(defn -main [& [port]]
 (let [port (Integer. (or port (env :port) 5000))]
   (jetty/run-jetty (site #'app) {:port port :join? false})))


;; For interactive development:
;; (.stop server)
;; (def server (-main))
