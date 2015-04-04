#lang racket

; 1.3 wrong translation. should be sum of square
(define (sum-max2 x y z)
  (+ x y z (- (min x y z))))

(define (square x) (* x x))

(define (sum-of-squares x y z)
  (let ([m (max x y)]
        [n (max (min x y) z)])
    (+ (square n) (square m))))

; 1.8
(define (cube-root x)
  (define (cube n) (* n n n))
  (define (good-enough? guess)
    (< (abs (- (cube guess) x)) 0.0001))
  (define (improve guess)
    (/ (+ (/ x (square guess)) guess guess) 3))
  (define (cube-root-iter guess)
    (if (good-enough? guess)
        guess
        (cube-root-iter (improve guess))))
  (cube-root-iter 1.0))