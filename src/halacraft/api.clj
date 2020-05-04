(ns halacraft.api
  (:import [org.bukkit Bukkit]))

(def ^:dynamic plugin)

(defmacro with-plugin [& body]
  `(let [_# (do (prn {:plugin  plugin, :server (.getServer plugin)}))
         scheduler# (.. plugin getServer getScheduler)]
     (.runTask scheduler# plugin
               (fn []
                 ~@body))))

(defn world [] (.get (Bukkit/getWorlds) 0))

(defn send-message [sender & messages]
  (when sender
    (.sendMessage sender (apply print-str messages))))

(defn set-storm [world flg]
  (with-plugin (.setStorm world flg)))

(defn set-thundering [world flg]
  (with-plugin (.setThundering world flg)))

(defn weather [world type]
  (set-storm world (contains? #{:rain :thunder} type))
  (set-thundering world (= type :thunder)))