(ns launchpad-lightsout.grid)

(defn lights-out-grid
  ([n]
   "Creates a symmetric n x n lights out grid"
   (lights-out-grid n n))
  ([m n]
   "Creates a m x n lights out grid"
   (let [row (vec (map (constantly 0) (range n)))]
     (vec (map (constantly row) (range m))))))
