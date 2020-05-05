(ns halacraft.commands.halake
  (:require [halacraft.env :as env]
            [halacraft.api :as api]
            [halacraft.commands :refer [run-command]]
            [nrepl.server :as nrepl]))

(def servers (atom {}))

(defn start-server! [player]
  (binding [env/player player]
    (let [server (nrepl/start-server)]
      (prn {:server server :player player})
      (swap! servers assoc [(and player (.getName player)) (:port server)] server)
      server)))

(defn stop-servers! []
  (doseq [server (vals @servers)]
    (nrepl/stop-server server))
  (reset! servers {}))

(defmethod run-command :halake [_ sender [command & args]]
  (condp = (keyword command)
    :repl (let [server (start-server! sender)]
            (api/send-message sender "start repl server. port:" (:port server)))
    :weather (api/weather :rain)
    (api/send-message sender "unknown command" command))
  true)