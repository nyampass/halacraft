(ns halacraft.core
  (:require [clojure.string :refer [join]]
            [halacraft.api :as api]
            [nrepl.server :refer [start-server stop-server]])
  (:import [halacraft ICorePlugin]))

(defonce repl-servers (atom {}))

  ;; (reset! server (start-server :port 7888)))
  ;; (stop-server @server))

(defn clj-command [sender args]
  (let [command (join " " args)]
    (api/send-message sender ">" command)
    (api/send-message sender (str (eval (read-string command))))))

(defn on-command [sender command args]
  (condp = (.getName command)
    "clj" (clj-command sender args)
    "weather-clear" (api/weather (api/world) :clear))
  true)

(defrecord CorePlugin []
  ICorePlugin
  (onEnable [_])
  (onDisable [_])
  (onCommand [_ sender command label args]
             (on-command sender command args)))
