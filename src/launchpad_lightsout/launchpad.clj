(ns launchpad-lightsout.launchpad
  (:use [clj-launchpad :only [clear-grid draw-grid]]))

(defn- number-to-color [n]
  "transforms a number to a color"
  (get {1 :red 2 :green 3 :amber} n :off))

(defn push
  ([lpad grid]
   "pushes a representation of grid to the Launchpad at :high intensity"
   (push lpad grid :high))
  ([lpad grid intensity]
   "pushes a representation of grid to the Launchpad at intensity"
   (do
     (clear-grid lpad)
     (doseq [[row-index row] (map vector (iterate inc 0) grid)
             [column-index value] (map vector (iterate inc 0) row)]
       (draw-grid lpad row-index column-index (number-to-color value) intensity)))))
