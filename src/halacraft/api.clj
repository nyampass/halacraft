(ns halacraft.api
  (:import [org.bukkit ChatColor Bukkit Material]))

(defn broadcast [msg]
  (Bukkit/broadcastMessage msg))

(defn echo [player s]
  {:pre [player]}
  (.sendMessage player (str s)))