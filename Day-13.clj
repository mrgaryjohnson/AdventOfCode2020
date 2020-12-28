
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
