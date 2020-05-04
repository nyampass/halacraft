(ns halacraft.commands.halake
  (:require [halacraft.api :as api]
            [nrepl.server :as nrepl]))

(def servers (atom {}))

(defn start-server! [plugin sender-name]
  (binding [api/plugin plugin]    
    (let [server (nrepl/start-server)]
      (swap! servers assoc [sender-name (:port server)] server)
      server)))

(defn stop-servers! []
  (doseq [server (vals @servers)]
    (nrepl/stop-server server))
  (reset! servers {}))

(defn halake-command [plugin sender command args]
  (condp = command
    "repl" (let [server (start-server! plugin nil)]
             (when sender
               (api/send-message sender "repl" (:port server))))
    "weather" (binding [api/plugin plugin]
                (api/weather (api/world) :rain))
    (api/send-message sender "unknown command" command)))