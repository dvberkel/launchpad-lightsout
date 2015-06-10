(ns launchpad-lightsout.grid)

(defn- create-grid [m n value]
  "Creates a m x n grid filled with value"
  (let [row (vec (map (constantly value) (range n)))]
     (vec (map (constantly row) (range m)))))

(defn lights-out-grid
  ([n]
   "Creates a symmetric n x n lights out grid"
   (lights-out-grid n n))
  ([m n]
   "Creates a m x n lights out grid"
   (create-grid m n 0)))

(defn setup-grid
  ([m n]
   "Creates a m x n setup grid with 2 colors"
   (setup-grid m n 2))
  ([m n colors]
   "Creates a m x n setup grid with colors"
   (create-grid m n (dec colors))))
