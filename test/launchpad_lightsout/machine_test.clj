(ns launchpad-lightsout.machine-test
  (:require [clojure.test :refer :all]
            [launchpad-lightsout.machine :refer :all])
  (:use [automat.core :only [advance]]))

(defn- feed [machine symbols]
  "advances machine through all symbols"
  (let [reducer (fn [accumulator symbol]
                  (advance machine accumulator symbol))]
    (reduce reducer nil symbols)))

(deftest machine-test
  (let [machine (create-machine)]
    (testing "if machine accepts correct sequences of [:setup :configure]"
      (is (:accepted? (feed machine [:setup :setup])))
      (is (:accepted? (feed machine [:setup :configure :setup])))
      (is (:accepted? (feed machine [:setup :configure :configure :setup])))
      (is (:accepted? (feed machine [:configure :configure])))
      (is (:accepted? (feed machine [:configure :setup :configure])))
      (is (:accepted? (feed machine [:configure :setup :setup :configure])))
      (is (:accepted? (feed machine [:configure :configure :setup :configure :setup]))))
    (testing "if machine does not accept incorrect sequences of [:setup :configure]"
      (is (not (:accepted? (feed machine [:setup]))))
      (is (not (:accepted? (feed machine [:setup :configure]))))
      (is (not (:accepted? (feed machine [:setup :configure]))))
      (is (not (:accepted? (feed machine [:configure]))))
      (is (not (:accepted? (feed machine [:configure :setup]))))
      (is (not (:accepted? (feed machine [:configure :setup :setup])))))))
