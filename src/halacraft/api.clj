(ns halacraft.api
  (:import [org.bukkit Bukkit]))

(defn world [] (.get (Bukkit/getWorlds) 0))

(defn send-message [sender & messages]
  (.sendMessage sender (apply print-str messages)))

(def set-storm #(.setStorm %1 %2))
(def set-thundering #(.setThundering %1 %2))

(defn weather [type]
  (set-storm (world) (#{:rain :thunder} type))
  (set-thundering (world) (= type :thunder)))