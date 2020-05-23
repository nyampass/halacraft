(ns halacraft.api
  (:require [halacraft.env :as env]
            [clojure.string :refer [upper-case]]
            [clojure.core.async :refer [chan <!! >!!]])
  (:import [org.bukkit Bukkit]
           [org.bukkit.entity EntityType]))

(defn scheduler []
  (.. @env/plugin getServer getScheduler))

(defmacro run [& body]
  `(let [c# (chan)]
    (.runTask (scheduler) @env/plugin
              #(>!! c# (try
                        (or (do ~@body) true)
                        (catch Exception e# e#))))
    (<!! c#)))

(defn send-message [sender & messages]
  (when sender
    (.sendMessage sender (apply print-str messages))))

(defn set-storm [flg]
  (run (.setStorm @env/world flg)))

(defn set-thundering [flg]
  (run (.setThundering @env/world flg)))

(defn weather [type]
  (set-storm (contains? #{:rain :thunder} type))
  (set-thundering (= type :thunder)))

(defn entity-name [k]
  (-> k name upper-case))

(defn spawn [entity-name player]
  (let [entity-name (-> entity-name name upper-case)
        [entity] (filter #(= (.name %1) entity-name) (seq (EntityType/values)))]
    (run
     (.spawnEntity @env/world (.getLocation player) entity))))