(ns clojure-getting-started.db
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (require [clojure.java.jdbc :as jdbc]
           [jdbc.pool.c3p0    :as pool]))

(def db-uri
 (java.net.URI. (or
   ("postgres://rzdnlqvtihaywe:h8YMM9hPA-0CEPGPHvUJ23lo6r@ec2-54-228-246-19.eu-west-1.compute.amazonaws.com:5432/d3kf2u2jt4bnt3")
   "postgresql://localhost:5432/ticks")))

(def user-and-password
 (if (nil? (.getUserInfo db-uri))
   nil (clojure.string/split (.getUserInfo db-uri) #":")))

(def spec
 (pool/make-datasource-spec
   {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user (get user-and-password 0)
   :password (get user-and-password 1)
   :subname (if (= -1 (.getPort db-uri))
               (format "//%s%s" (.getHost db-uri) (.getPath db-uri))
               (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))}))
