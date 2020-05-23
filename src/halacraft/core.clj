(ns halacraft.core
  (:require [halacraft.commands :refer [run-command]]
            [halacraft.commands clojure halake]
            [halacraft.env :refer [setup!]])
  (:import [halacraft ICorePlugin]))

(defonce _sender_ (atom nil))
(defonce _command_ (atom nil))
(defonce _label_ (atom nil))
(defonce _args_ (atom nil))

(defrecord CorePlugin [plugin]
  ICorePlugin
  (onEnable [_]
            (setup! plugin)
            (run-command :halake nil ["repl"]))
  (onDisable [_])
  (onCommand [_ sender command label args]
             (do (prn sender command label args)
                 (reset! _sender_ sender)
                 (reset! _command_ command)
                 (reset! _args_ args)
                 (run-command command sender args))))