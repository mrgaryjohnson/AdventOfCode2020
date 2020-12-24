(def test-boot-lines ["nop +0"
                      "acc +1"
                      "jmp +4"
                      "acc +3"
                      "jmp -3"
                      "acc -99"
                      "acc +1"
                      "jmp -4"
                      "acc +6"])

(def line-re #"(\w{3})\s([+-]\d+)")

(defn parse-line [line]
  (let [groups (re-find (re-matcher line-re line))]
    [(keyword (second groups)) (Integer/parseInt (last groups))]))
  
(defn execute-inst [[op num] [inst-counter accum]]
  (case op 
    :nop [(inc inst-counter) accum]
    :acc [(inc inst-counter) (+ accum num)]
    :jmp [(+ inst-counter num) accum]))

(defn execute-step [[memory state]]
  (let [inst-counter (first state)
        inst (first (memory inst-counter))
        new-state (execute-inst inst state)
        new-memory (assoc-in memory [inst-counter 1] false)]
    ;(println (str "Memory: " new-memory))
    ;(println (str "State: " new-state))
    [new-memory new-state]))

(defn not-looping
  ([[memory state]]
   (not-looping memory state))
  ([memory [inst-counter]]
   (second (memory inst-counter))))

(defn within-bounds
  ([[memory state]]
   (within-bounds memory state))
  ([memory [inst-counter]]
   (< inst-counter (count memory))))

(defn run [memory]
  (->> [memory [0 0]] ; Initial memory/state
    (iterate execute-step)
    (drop-while #(and (within-bounds %) (not-looping %)))
    (first) ; Final memory/state
    (second))) ; State

(let [memory (vec (->> test-boot-lines
                       (map parse-line)
                       (map #(vector % true))))
      final-state (run memory)]
     (println (str "Part 1 - Accumulator for test code: " (second final-state))))

(with-open [reader (clojure.java.io/reader "boot-code.txt")]
  (let [lines (line-seq reader)
        memory (vec (->> lines
                         (map parse-line)
                         (map #(vector % true))))
        final-state (run memory)]
     (println (str "Part 1 - Accumulator for real code: " (second final-state)))))

(defn find-fix [memory]
  (->> (range (count memory))
       (map #(case (get-in memory [% 0 0])
               :nop (assoc-in memory [% 0 0] :jmp)
               :jmp (assoc-in memory [% 0 0] :nop)
               nil))
       (filter seq)
       (map #(vector % (run %)))
       (drop-while within-bounds)
       (first)))

(let [memory (vec (->> test-boot-lines
                       (map parse-line)
                       (map #(vector % true))))
      [_ final-state] (find-fix memory)]
     (println (str "Part 2 - Accumulator for test code: " (second final-state))))

(with-open [reader (clojure.java.io/reader "boot-code.txt")]
  (let [lines (line-seq reader)
        memory (vec (->> lines
                         (map parse-line)
                         (map #(vector % true))))
        [_ final-state] (find-fix memory)]
    (println (str "Part 2 - Accumulator for real code: " (second final-state)))))
