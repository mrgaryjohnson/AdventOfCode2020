;; Part - 1
(defn find-product [target raw-nums]
  (let [sorted-nums (vec (sort raw-nums))
        match (first
                (drop-while #(not= target (+ (first %) (last %)))
                            (iterate #(let [sum (+ (first %) (last %))]
                                        (if (> sum target)
                                          (pop %)
                                          (subvec % 1)))
                                     sorted-nums)))]
    (when (seq? match)
      (* (first match) (last match)))))

(defn simple-test []
  (find-product 2020 [1721 979 366 299 675 1456]))

(defn large-test []
  (let [lines (slurp "C:\\dev\\2020-Advent\\expense-report.txt")
        nums (read-string (str "[" lines "]"))]
    (find-product 2020 nums)))


;; Part - 2
(defn find-binary-product [target sorted-nums]
  (first (drop-while #(and (>= (count %) 2) (not= target (+ (first %) (last %))))
                     (iterate #(let [sum (+ (first %) (last %))]
                                 (if (> sum target)
                                   (pop %)
                                   (subvec % 1)))
                              sorted-nums))))

(defn find-trinary-product [target raw-nums]
  (let [sorted-nums (vec (sort raw-nums))
        starting-point [(first sorted-nums) (subvec sorted-nums 1)]
        match (first (drop-while (fn [[base remaining]] (and (>= (count remaining) 2) (not= target (+ base (first remaining) (last remaining)))))
                                 (iterate (fn [[base remaining]]
                                            (let [sub-target (- target base)
                                                  sub-match (find-binary-product sub-target remaining)]
                                              (if (and sub-match (>= (count sub-match) 2))
                                                [base sub-match]
                                                [(first remaining) (subvec remaining 1)])))
                                          starting-point)))]
    (when (and match (>= (count (second match)) 2))
      (println (str "Found: " (first match) ", " (first (second match)) ", " (last (second match))))
      (* (first match) (first (second match)) (last (second match))))))

(defn simple-trinary-test []
  (find-trinary-product 2020 [1721 979 366 299 675 1456]))

(defn large-trinary-test []
  (let [lines (slurp "C:\\dev\\2020-Advent\\expense-report.txt")
        nums (read-string (str "[" lines "]"))]
    (find-trinary-product 2020 nums)))
