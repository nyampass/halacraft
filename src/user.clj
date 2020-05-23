(ns user
  (:require [halacraft.api :refer :all]
            [halacraft.env :refer [plugin world player]]
            [halacraft.graphics :as g])
  (:import [org.bukkit Location]))

;; (def image (g/pixels "Nyampass" :width 80 :height 20 :font-size 10 :text-x 0 :text-y 15))

;; (let [l (location player)]
;;   (for [x (range (.getWidth image))
;;         y (range (.getHeight image))]
;;     (when (g/on? image x y)
;;       (update-type (block (location l x (- 0 y) 0))
;;                    (:legacy-gold-block materials)))))
