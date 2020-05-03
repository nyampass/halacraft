(ns halacraft.core
  (:require [clojure.string :refer [join]]
            [halacraft.api :as api]
            [nrepl.server :refer [start-server stop-server]])
  (:import [halacraft ICorePlugin]))

(defonce server (atom nil))

(defn on-enable []
  (reset! server (start-server :port 7888)))

(defn on-disable []
  (stop-server @server))

(require '[user :as u])

(defn on-command [sender command label args]
  (prn command)
  (println "command" (.getName command) (join " " args))
  (u/weather (keyword (get  args 0)))
  ;; (.sendMessage sender (str (eval (read-string (join " " args)))))
  true)

(defrecord CorePlugin []
  ICorePlugin
  (onEnable [_]
    (on-enable))
  (onDisable [_]
    (on-disable))
  (onCommand [_ sender command label args]
             (on-command sender command label args)))
