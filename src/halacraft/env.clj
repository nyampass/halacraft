(ns halacraft.env
  (:import [org.bukkit Bukkit]))

(defonce plugin (atom nil))

(defonce world (atom nil))

(def ^:dynamic player)

(defn setup!
  ([_plugin] (setup! _plugin (.get (Bukkit/getWorlds) 0)))
  ([_plugin _world] 
   (do
     (reset! plugin _plugin)
     (reset! world _world))))