(ns launchpad-lightsout.grid-test
  (:require [clojure.test :refer :all]
            [launchpad-lightsout.grid :refer :all]))

(deftest grid-test
  (testing "if symmetric grids can be made"
    (is (= [[0]] (lights-out-grid 1)))
    (is (= [[0 0] [0 0]] (lights-out-grid 2)))
    (is (= [[0 0 0] [0 0 0] [0 0 0]] (lights-out-grid 3))))
  (testing "if rectangular grids can be made"
    (is (= [[0] [0]] (lights-out-grid 2 1)))
    (is (= [[0 0]] (lights-out-grid 1 2)))
    (is (= [[0] [0] [0]] (lights-out-grid 3 1)))
    (is (= [[0 0] [0 0] [0 0]] (lights-out-grid 3 2)))
    (is (= [[0 0 0]] (lights-out-grid 1 3)))
    (is (= [[0 0 0] [0 0 0]] (lights-out-grid 2 3)))))
