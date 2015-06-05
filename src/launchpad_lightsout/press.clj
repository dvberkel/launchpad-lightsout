(ns launchpad-lightsout.press)

(defn increment
  ([grid row-index column-index]
   "rolling increment of 2 for the value of the grid at row index and column index"
   (increment grid row-index column-index 2))
  ([grid row-index column-index n]
   "rolling increment of n for the value of the grid at row index and column index"
   (let [row (nth grid row-index)
         value (nth row column-index)
         next (mod (inc value) n)
         next-row (assoc row column-index next)]
     (assoc grid row-index next-row))))

(defn safe-increment
  ([grid row-index column-index]
   "if row-index and column-index are within bounds of grid performs a rolling increment of 2 on the grid with the specified values otherwise returns the grid"
   (safe-increment grid row-index column-index 2))
  ([grid row-index column-index n]
   "if row-index and column-index are within bounds of grid performs a rolling increment of n on the grid with the specified values otherwise returns the grid"
   (let [max-row (dec (count grid))
         max-column (dec (count (nth grid 0)))]
     (if (and (<= 0 row-index max-row) (<= 0 column-index max-column))
       (increment grid row-index column-index n)
       grid))))

(defn press
  ([grid row-index column-index]
   "press button row index and colum index of grid with a rolling increment of 2"
   (press grid row-index column-index 2))
  ([grid row-index column-index n]
   "press button row index and column index of grid with a rolling increment of n"
   (let [max-row (dec (count grid))
         max-column (dec (count (nth grid 0)))]
     (if (and (<= 0 row-index max-row) (<= 0 column-index max-column))
       (let [neighbors [[-1 0] [0 -1] [0 0] [0 1] [1 0]]
             coordinates (map #(map + [row-index column-index] %) neighbors)]
         (reduce #(safe-increment %1 (first %2) (second %2) n) grid coordinates))
       grid))))
