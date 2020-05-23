(ns halacraft.api
  (:require [halacraft.env :as env]
            [clojure.string :refer [lower-case]]
            [clojure.core.async :refer [chan <!! >!!]])
  (:import [org.bukkit.entity EntityType]
           [org.bukkit Material Location]
           [org.bukkit.craftbukkit.v1_15_R1.entity CraftPlayer]))

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

(defonce entities
  (let [entity-name
        #(-> %1
             .name
             (.replace \_ \-)
             lower-case
             keyword)]
    (into {} (map (fn [e] {(entity-name e) e}) (EntityType/values)))))

(defonce materials
  (let [entity-name
        #(-> %1
             .name
             (.replace \_ \-)
             lower-case
             keyword)]
    (into {} (map (fn [e] {(entity-name e) e}) (Material/values)))))

(defn spawn [entity-name player]
  (run
   (.spawnEntity @env/world (.getLocation player) (entity-name entities))))

env/player

(defprotocol ILocation 
  (to-location [l]))

(extend Location ILocation {:to-location identity})

(extend CraftPlayer ILocation {:to-location #(.getLocation %1)})

(defn location
  ([player-or-location] (to-location player-or-location))
  ([x y z] @env/world x y z)
  ([at inc-x inc-y inc-z]
   (let [l (to-location at)]
     (Location. @env/world
                (+ (.getX l) inc-x)
                (+ (.getY l) inc-y)
                (+ (.getZ l) inc-z)))))

(defn block [location]
  (.getBlock location))

(defn update-type [block material]
  (run (.setType block material)))