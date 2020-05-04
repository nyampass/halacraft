(ns halacraft.api
  (:require [clojure.string :refer [upper-case]])
  (:import [org.bukkit Bukkit]
           [org.bukkit.entity EntityType]))

(def ^:dynamic plugin)
(def ^:dynamic player)

(defonce current-world (atom nil))

(defn set-world! 
  ([] (set-world! (.get (Bukkit/getWorlds) 0)))
  ([world] (reset! current-world world)))

(defmacro with-environment [& body]
  `(let [scheduler# (.. plugin getServer getScheduler)]
     (.runTask scheduler# plugin
               (fn []
                 ~@body))))

(defn send-message [sender & messages]
  (when sender
    (.sendMessage sender (apply print-str messages))))

(defn set-storm [flg]
  (with-environment (.setStorm @current-world flg)))

(defn set-thundering [flg]
  (with-environment (.setThundering @current-world flg)))

(defn weather [type]
  (set-storm (contains? #{:rain :thunder} type))
  (set-thundering (= type :thunder)))

(defn entity-name [k]
  (-> k name upper-case))

(defn spawn [entity-name player]
  (let [entity-name (-> entity-name name upper-case)
        [entity] (filter #(= (.name %1) entity-name) (seq (EntityType/values)))]
    (with-environment
      (.spawnEntity @current-world (.getLocation player) entity))))