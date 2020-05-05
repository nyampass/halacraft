(ns halacraft.commands
  (:import [org.bukkit.command Command]))

(defprotocol ICommand (command-name [this]))

(extend-protocol ICommand
  String
  (command-name [this] (keyword this))
  clojure.lang.Keyword
  (command-name [this] this)
  Command
  (command-name [this] (-> this  .getName keyword)))

(defmulti run-command
  (fn [command & _]
    (prn :run-command command)
    (command-name command)))

(defmethod run-command :default [& _])