(ns halacraft.graphics
  (:import [java.awt Color Font]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [java.io File]))

(defn pixels [text & {:keys [width height font font-size text-x text-y]}]
  (let [image (BufferedImage. width height BufferedImage/TYPE_BYTE_GRAY)]
    (doto (.createGraphics image)
      (.setFont (Font. (or font "TimesRoman") Font/PLAIN font-size))
      (.drawString text text-x text-y)
      (.dispose))
    (ImageIO/write image "png" (File. "/tmp/hoge.jpg"))
    image))

;; (def image (pixels "HaLaCraft" :width 80 :height 20 :font-size 10 :text-x 0 :text-y 20))

(defn on? [image x y]
  (= (.getRGB image x y) -1))

;; (set
;;  (for [x (range (.getWidth image))
;;        y (range (.getHeight image))]
;;    (on? image x y)))