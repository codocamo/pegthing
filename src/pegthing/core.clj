(ns pegthing.core
  (require [clojure.set :as set])
  (:gen-class))

(declare successful-move prompt-move game-over query-rows)


;;defn - combination of 'def' and 'fn' creates a function and names it
;;let - names something within scope of the 'let'(let [name <(function)/data>] <[another let]> (let body))
;;cons - returns list where data is added to the begining of a sequence (cons data sequence)
;;lazy-seq -

;;this function has an arity of 2,
;;1st arity has no arguments and calls itself with arguments of 1 0 (servs as a default),
;;2nd arity takes 2 arguments, adds them together (calls them new-sum)

(defn tri*
  "Generates lazy sequence of triangular numbers"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))
