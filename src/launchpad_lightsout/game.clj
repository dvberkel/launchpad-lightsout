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
   (let [grid (setup-grid m n)]
     { :height m :width n :colors o :grid grid})))

(def game (atom (create-game 5 5 2)))

(def grid (atom (lights-out-grid 5)))

(def lpad (open "Launchpad Mini"))


(defn create-lights-out-handler [lpad grid colors]
  "create a lights out handler that cycles through a number of colors"
  (fn [x y pressed?]
    (do
      (if pressed? (swap! grid (fn [grid] (press grid x y colors))))
      (push lpad @grid))))

(on-grid-pressed lpad (create-lights-out-handler lpad grid 2))

(close lpad)
