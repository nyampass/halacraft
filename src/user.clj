(ns user
  (:require [halacraft.api :refer :all]
            [halacraft.env :refer [plugin world player]])
  (:import [org.bukkit Location]))

;; (let [l (location player)]
;;   (dotimes [i 100]
;;     (update-type (block (location l 0 i 0))
;;                  (:diamond-block materials))))