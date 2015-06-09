(ns launchpad-lightsout.game
  (use [clj-launchpad :only [open on-grid-pressed reset close]])
  (use [launchpad-lightsout.grid :only [lights-out-grid]])
  (use [launchpad-lightsout.press :only [press]])
  (use [launchpad-lightsout.launchpad :only [push]]))

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
