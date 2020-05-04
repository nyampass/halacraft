(ns user
  (:require [halacraft.api :refer :all]))

(defn sleep [seconds]
  (Thread/sleep (* seconds 1000)))