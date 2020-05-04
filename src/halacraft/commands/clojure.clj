(ns halacraft.commands.clojure
  (:require [halacraft.api :as api]
            [clojure.string :refer [join]]))

(defn run [code]
  (try
    (str (eval (read-string code)))
    (catch Exception e
      (str "Error: " (.getMessage e)))))

(defn clj-command [_ sender command args]
  (let [code (join " " (concat [command] args))]
    (api/send-message sender ">" code)
    (api/send-message sender (run code))))