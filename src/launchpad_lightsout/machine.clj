(ns launchpad-lightsout.machine
  (require [automat.core :as automat])
  (use [clj-launchpad :only [open draw-grid create-press-register reset close]])
  (use [launchpad-lightsout.game :only [create-game
                                        reset-game
                                        create-setup-handler
                                        create-problem-handler
                                        create-lights-out-handler]]))

(def machine-definition (automat/*
                         (automat/or
                          (automat/* [:setup (automat/$ :doSetup)
                                      (automat/* :configure)
                                      :setup (automat/$ :doPlay)])
                          (automat/* [:configure (automat/$ :doConfigure)
                                      (automat/* :setup)
                                      :configure (automat/$ :doPlay)]))))

(defn create-machine
  ([]
   "creates a machine with no-op reducers"
   (let [no-op (fn [state input] state)] (create-machine {:doSetup no-op
                                                          :doConfigure no-op
                                                          :doPlay no-op})))
  ([reducers]
   "creates a machine with reducers"
   (automat/compile machine-definition {:reducers reducers})))

(defn start [lpad]
  "sets up and starts a game of Lights Out on lpad"
  (let [game (atom (create-game 5 5 2))
        actions (create-press-register lpad)
        setup-handler (create-setup-handler lpad game)
        problem-handler (create-problem-handler lpad game)
        play-handler (create-lights-out-handler lpad game)
        do-setup (fn [state input] (do
                                     (reset-game game)
                                     (reset lpad)
                                     (draw-grid lpad 0 8 :red :high)
                                     ((actions :unregister) play-handler)
                                     ((actions :register) setup-handler)))
        do-configure (fn [state input] (do
                                         (reset-game game)
                                         (reset lpad)
                                         (draw-grid lpad 1 8 :red :high)
                                         ((actions :unregister) play-handler)
                                         ((actions :register problem-handler))))
        do-play (fn [state input] (do
                                    (draw-grid lpad 0 8 :off)
                                    (draw-grid lpad 1 8 :off)
                                    ((actions :unregister) setup-handler)
                                    ((actions :unregister) problem-handler)
                                    ((actions :register) play-handler)))
        machine (create-machine {:doSetup do-setup :doConfigure do-configure :doPlay do-play})
        current-state (atom nil)
        setup-handler (fn [x y pressed?]
                        (do
                          (if (and pressed? (= 0 x) (= 8 y))
                            (swap! current-state (fn [state]
                                                   (do
                                                     (draw-grid lpad 8 0 :red :flashing)
                                                     (automat/advance machine state :setup)))))))
        configure-handler (fn [x y pressed?]
                            (if (and pressed? (= 1 x) (= 8 y))
                              (swap! current-state (fn [state]
                                                     (automat/advance machine state :configure)))))]
    (do
      ((actions :register) setup-handler)
      ((actions :register) configure-handler)
      (do-play {} nil)
      actions)))

(def lpad (open "Launchpad Mini"))

(def actions (start lpad))

(reset lpad)

(close lpad)
