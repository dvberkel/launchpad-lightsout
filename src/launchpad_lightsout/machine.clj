(ns launchpad-lightsout.machine
  (require [automat.core :as automat]))

(def machine (automat/compile
              (automat/*
               (automat/or
                (automat/* [:setup (automat/* :configure) :setup])
                (automat/* [:configure (automat/* :setup) :configure])))))
