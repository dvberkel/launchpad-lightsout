(ns launchpad-lightsout.core
  (:gen-class))

(defn lights-out-grid
  ([n]
   "Creates a symmetric n x n lights out grid"
   (lights-out-grid n n))
  ([m n]
   "Creates a m x n lights out grid"
   (let [row (map (constantly 0) (range n))]
     (map (constantly row) (range m)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
