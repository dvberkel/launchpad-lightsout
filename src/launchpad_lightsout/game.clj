(ns launchpad-lightsout.game
  (use [clj-launchpad :only [open create-press-register reset close]])
  (use [launchpad-lightsout.grid :only [lights-out-grid setup-grid]])
  (use [launchpad-lightsout.press :only [press safe-increment]])
  (use [launchpad-lightsout.launchpad :only [push]]))

(defn create-game
  ([]
   "creates a 5 x 5 game with 2 colors"
   (create-game 5 5 2))
  ([n]
   "creates a n x n game with 2 colors"
   (create-game n n 2))
  ([m n]
   "creates a m x n game with 2 colors"
   (create-game m n 2))
  ([m n o]
   "creates a m x n game with o colors"
   (let [grid (setup-grid m n o)]
     { :rows m :columns n :colors o :grid grid})))


(defn create-setup-handler [lpad game]
  "Create a setup handler to determine game"
  (let [next-color {2 3 3 4 4 2}]
    (fn [x y pressed?]
      (if (and (< x 8) (< y 8))
        (let [row (inc x) column (inc y)]
          (do
            (if pressed?
              (let [colors (if (and (= row (@game :rows)) (= column (@game :columns)))
                             (get next-color (@game :colors) 2)
                             2)]
                (swap! game (fn [game] (create-game row column colors)))))
            (push lpad (@game :grid))))))))

(defn create-problem-handler [lpad game]
  "Create a problem handler that allows to setup a Lights Out problem"
  (fn [x y pressed?]
    (if (and (< x 8) (< y 8))
      (do
        (if pressed? (swap! game (fn [game]
                                   (assoc game :grid (safe-increment (game :grid) x y (game :colors))))))
        (push lpad (@game :grid))))))

(defn create-lights-out-handler [lpad game]
  "create a lights out handler that cycles through a number of colors"
  (fn [x y pressed?]
    (if (and (< x 8) (< y 8))
      (do
        (if pressed? (swap! game (fn [game]
                                   (assoc game :grid (press (game :grid) x y (game :colors))))))
        (push lpad (@game :grid))))))

(defn reset-game [game]
  "Resets the grid to be empty"
  (swap! game (fn [game]
                (assoc game :grid (lights-out-grid (game :rows) (game :columns))))))
