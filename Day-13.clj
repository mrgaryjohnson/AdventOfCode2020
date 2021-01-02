
(defn time-to-wait [target schedule]
  (- schedule (rem target schedule)))

(defn shortest-time-to-wait [target schedules]
  (let [earliest (first (sort-by #(time-to-wait target %) schedules))]
   [earliest (- earliest (rem target earliest))]))

(let [test-target 939
      test-schedules [7 13 59 31 19]
      time-to-wait (shortest-time-to-wait test-target test-schedules)]
 (println (str "Part 1 - Test schedules, next bus: " (first time-to-wait)))
 (println (str "Part 1 - Test schdules, minutes to wait: " (second time-to-wait)))
 (println (str "Part 1 - Test schedules, product: " (apply * time-to-wait))))

(with-open [reader (clojure.java.io/reader "shuttle-schedules.txt")]
  (let [lines (line-seq reader) 
        target (clojure.edn/read-string (first lines))
        schedules (->> (second lines)
                       (#(clojure.string/split % #","))
                       (filter #(not= "x" %))
                       (map clojure.edn/read-string))
        time-to-wait (shortest-time-to-wait target schedules)]
   (println (str "Part 1 - Real schedules, next bus: " (first time-to-wait)))
   (println (str "Part 1 - Real schdules, minutes to wait: " (second time-to-wait)))
   (println (str "Part 1 - Real schedules, product: " (apply * time-to-wait)))))

(defn handle-targe [{:keys [timestamp interval]} [offset delta]]
  (if timestamp
    (let [new-ts (->> timestamp
                      (iterate #(+ % interval))
                      (drop-while #(not= 0 (rem (+ % offset) delta)))
                      (first))
          new-int (* interval delta)]
      {:timestamp new-ts :interval new-int})
    {:timestamp offset :interval delta}))

(defn get-earliest [line]
  (let [sorted-with-index (->> line
                               (#(clojure.string/split % #","))
                               (map-indexed vector)
                               (filter #(not= "x" (second %)))
                               (map #(vector (first %) (clojure.edn/read-string (second %)))))]
    (:timestamp (reduce handle-targe {} sorted-with-index))))

(let [earliest (get-earliest "7,13,x,x,59,x,31,19")]
  (println "Part 2 - test schedule 1, earliest timestamp: " earliest))
(let [earliest (get-earliest "17,x,13,19")]
  (println "Part 2 - test schedule 2, earliest timestamp: " earliest))
(let [earliest (get-earliest "67,7,59,61")]
  (println "Part 2 - test schedule 3, earliest timestamp: " earliest))
(let [earliest (get-earliest "67,x,7,59,61")]
  (println "Part 2 - test schedule 4, earliest timestamp: " earliest))
(let [earliest (get-earliest "67,7,x,59,61")]
  (println "Part 2 - test schedule 5, earliest timestamp: " earliest))
(let [earliest (get-earliest "1789,37,47,1889")]
  (println "Part 2 - test schedule 6, earliest timestamp: " earliest))

(with-open [reader (clojure.java.io/reader "shuttle-schedules.txt")]
  (let [lines (line-seq reader)
        line (second lines)
        earliest (get-earliest line)]
    (println "Part 2 - real schedule, earliest timestamp: " earliest)))
