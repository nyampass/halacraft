(ns halacraft.api
  (:require [halacraft.env :as env]
            [clojure.string :refer [upper-case]])
  (:import [org.bukkit Bukkit]
           [org.bukkit.entity EntityType]))

(defmacro run-task [& body]
  `(let [scheduler# (.. @env/plugin getServer getScheduler)]
     (.runTask scheduler# @env/plugin
               (fn []
                 ~@body))))

(defn send-message [sender & messages]
  (when sender
    (.sendMessage sender (apply print-str messages))))

(defn set-storm [flg]
  (run-task (.setStorm @env/world flg)))

(defn set-thundering [flg]
  (run-task (.setThundering @env/world flg)))

(defn weather [type]
  (set-storm (contains? #{:rain :thunder} type))
  (set-thundering (= type :thunder)))

(defn entity-name [k]
  (-> k name upper-case))

(defn spawn [entity-name player]
  (let [entity-name (-> entity-name name upper-case)
        [entity] (filter #(= (.name %1) entity-name) (seq (EntityType/values)))]
    (run-task
      (.spawnEntity @env/world (.getLocation player) entity))))