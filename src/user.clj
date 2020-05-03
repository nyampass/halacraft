(ns user
  (:import [org.bukkit Bukkit]
           [org.bukkit World]))

(defn  current-world [] (.get (Bukkit/getWorlds) 0))

(def set-storm #(.setStorm %1 %2))
(def set-thundering #(.setThundering %1 %2))

(defn weather [type]
  (prn :weather type)
  (let [world (current-world)]
    (condp = type
      :clear (do (set-storm world false)
                 (set-thundering world false))
      :rain (do (set-storm world true)
                (set-thundering world false))
      :thunder (do (set-storm world true)
                   (set-thundering world true)))))