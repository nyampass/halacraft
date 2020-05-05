(ns halacraft.commands.clojure
  (:require [halacraft.api :as api]
            [halacraft.commands :refer [run-command]]
            [clojure.string :refer [join]]))

(defn run-code [code]
  (try
    (str (eval (read-string code)))
    (catch Exception e
      (str "Error: " (.getMessage e)))))

(defmethod run-command :clj [_ sender args]
  (let [code (join " " args)]
    (api/send-message sender ">" code)
    (api/send-message sender (run-code code)))
  true)