(ns launchpad-lightsout.press-test
  (:require [clojure.test :refer :all]
            [launchpad-lightsout.grid :refer :all]
            [launchpad-lightsout.press :refer :all]))

(deftest press-test
  (testing "if pressing a button changes state of button and its neighbors"
    (let [grid (lights-out-grid 3)]
      (is (= [[1 1 0] [1 0 0] [0 0 0]] (press grid 0 0)))
      (is (= [[1 1 1] [0 1 0] [0 0 0]] (press grid 0 1)))
      (is (= [[0 1 1] [0 0 1] [0 0 0]] (press grid 0 2)))
      (is (= [[1 0 0] [1 1 0] [1 0 0]] (press grid 1 0)))
      (is (= [[0 1 0] [1 1 1] [0 1 0]] (press grid 1 1)))
      (is (= [[0 0 1] [0 1 1] [0 0 1]] (press grid 1 2)))
      (is (= [[0 0 0] [1 0 0] [1 1 0]] (press grid 2 0)))
      (is (= [[0 0 0] [0 1 0] [1 1 1]] (press grid 2 1)))
      (is (= [[0 0 0] [0 0 1] [0 1 1]] (press grid 2 2)))))
  (testing "if pressing outside the boundaries does not change the grid"
    (let [grid (lights-out-grid 2)]
      (is (= grid (press grid -1 -1)))
      (is (= grid (press grid -1  0)))
      (is (= grid (press grid -1  1)))
      (is (= grid (press grid -1  2)))
      (is (= grid (press grid  0  2)))
      (is (= grid (press grid  1  2)))
      (is (= grid (press grid  2  2)))
      (is (= grid (press grid  2  1)))
      (is (= grid (press grid  2  0)))
      (is (= grid (press grid  2 -1)))
      (is (= grid (press grid  1 -1)))
      (is (= grid (press grid  0 -1))))))
