(ns halacraft.core
  (:require [halacraft.commands
             [clojure :refer [clj-command]]
             [halake :refer [halake-command]]])
  (:import [halacraft ICorePlugin]))

(def commands {:clj clj-command
               :halake halake-command})

(defn on-command [plugin sender command [sub-command & args]]
  (let [command (-> command .getName keyword commands)]
    (command plugin sender sub-command args)
    true))

(defrecord CorePlugin [plugin]
  ICorePlugin
  (onEnable [_]
            ((commands :halake) plugin nil "repl" nil))
  (onCommand [_ sender command label args]
    (on-command plugin sender command args)))
