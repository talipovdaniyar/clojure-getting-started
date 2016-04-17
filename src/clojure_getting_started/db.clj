(ns clojure-getting-started.db
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (require [clojure.java.jdbc :as jdbc]
           [jdbc.pool.c3p0    :as pool]))
