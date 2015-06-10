(ns launchpad-lightsout.game
  (use [clj-launchpad :only [open on-grid-pressed reset close]])
  (use [launchpad-lightsout.grid :only [lights-out-grid setup-grid]])
  (use [launchpad-lightsout.press :only [press]])
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

(def game (atom (create-game 5 5 2)))

(defn create-setup-handler [lpad game]
  "Create a setup handler to determine game"
  (let [next-color {2 3 3 4 4 2}]
    (fn [x y pressed?]
      (let [row (inc x) column (inc y)]
        (do
          (if pressed?
            (let [colors (if (and (= row (@game :rows)) (= column (@game :columns)))
                           (get next-color (@game :colors) 2)
                           2)]
              (swap! game (fn [game] (create-game row column colors)))))
          (push lpad (@game :grid)))))))

(def grid (atom (lights-out-grid 5)))

(def lpad (open "Launchpad Mini"))


(defn create-lights-out-handler [lpad grid colors]
  "create a lights out handler that cycles through a number of colors"
  (fn [x y pressed?]
    (do
      (if pressed? (swap! grid (fn [grid] (press grid x y colors))))
      (push lpad @grid))))

(on-grid-pressed lpad (create-lights-out-handler lpad grid 2))
(on-grid-pressed lpad (create-setup-handler lpad game))

(close lpad)
