(ns pegthing.core
  (require [clojure.set :as set])
  (:gen-class))

(declare successful-move prompt-move game-over query-rows)


;;defn - combination of 'def' and 'fn' creates a function and names it
;;let - names something within scope of the 'let'(let [name <(function)/data>] <[another let]> (let body))
;;cons - returns list where data is added to the begining of a sequence (cons data sequence)
;;lazy-seq - returns a list and operation to perform on the list, the operations are perfomed (or the seq is realized) only when you try to interigate the sequence
;;inc - increments data by 1

;;this function has an arity of 2,
;;1st arity has no arguments and calls itself with arguments of 1 0 (servs as a default),
;;2nd arity takes 2 arguments, adds them together (calls them new-sum) and adds it to a lazy sequence
;;the lazy sequence calls the function
;;this is a clasic way of achiving recursion
(defn tri*
  "Generates lazy sequence of triangular numbers"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

;;def - names functions/data

;;this just calls tri* and names it tri
(def tri (tri*))

(take 5 tri)

;;= - used to check equality in 2 things, returns boolean
;;last - returns last element in a list (last sequence)
;;take-while - takes in function(pred) and a sequence. iterates over and takes elemets from the sequence until the pred returns false. (take-while (function/pred) sequence)
;;>= - used to check if something is greater than or equal to something else, returns boolean

;;this function takes one argument
;;it compares the argument with the elements in tri and takes elements from it until the argument is no longer bigger than the values within tri
;;this function then gets the last element from the list made of values from tri that are smaller or equal to the argument
;;it then checks to see if that last value is equal to the argument
(defn triangular?
  "Is the number triangular? e.g 1, 3, 6, 10, 15"
  [n]
  (= n (last (take-while #(>= n %) tri))))

(triangular? 5)
(triangular? 6)


;;take - returns the first n amount of elements within a seq (take int seq)

;;this function takes in an integer, that integer is used in a take form to return the first n numbers of the tri seq
;;it then grabs the last number of that seq
(defn row-tri
  "the triangular number at the end of row n"
  [n]
  (last(take n tri)))


(row-tri 1)
(row-tri 10)

;;count - counts all the elements within a seq
;;inc - increments a number by 1

;;this function takes in an integer(peg position) and compares the integer with the elements in tri and takes elements from it until the integer is no longer bigger than the values within tri
;;it then counts how many elements are within the returned seq and then incraments that by 1
(defn row-num
  "returns row number the position belongs to: pos 1 row 1, pos 2 and 3 row 2, etc"
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(row-num 1)
(row-num 5)
(row-num 10)


;;reudce - applys a function accross a seq a value can be optionaly provided so all elements in the seq gets evaluated with the val and not the next element in the seq (reduce function <val> seq)
;;assoc-in - associates values to a map takes a map a vector of keys and a value. the passed in map can be empty(populate an empty map) or full(change value in current map)  (assoc-in {map} [vector] value)

;;this function creates a nested map showing the connection between 2 point in both directions
;;it first checks if your destination is out of bounds if it is it just returns an un changed board

(defn connect
  "form a mutual connection between two positions"
  [board max-pos pos neighbor destination]
  (if (<= destination max-pos)
    (reduce (fn [new-board [p1 p2]]
              (assoc-in new-board [p1 :connections p2] neighbor))
            board
            [[pos destination] [destination pos]])
    board))

(connect {} 15 1 2 4)
