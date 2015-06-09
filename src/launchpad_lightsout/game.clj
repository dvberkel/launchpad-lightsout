(ns launchpad-lightsout.game
  (use [clj-launchpad :only [open on-grid-pressed reset close]])
  (use [launchpad-lightsout.grid :only [lights-out-grid]])
  (use [launchpad-lightsout.press :only [press]])
  (use [launchpad-lightsout.launchpad :only [push]]))

(def grid (atom (lights-out-grid 5)))

(def lpad (open "Launchpad Mini"))

(defn lights-out-handler [x y pressed?]
  (do
    (if pressed? (swap! grid (fn [grid] (press grid x y 3))))
    (push lpad @grid)))

(on-grid-pressed lpad lights-out-handler)

(close lpad)
