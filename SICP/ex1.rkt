#lang racket

; 1.3 wrong translation should be sum of square
(define (sum-max2 x y z)
  (+ x y z (- (min x y z))))

(define (sum-of-squares x y z)
  (let ([m (max x y)]
        [n (max (min x y) z)])
    (+ (* n n) (* m m))))