#lang racket
(require racket/trace)

; 1.3
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

;1.11
(define (rec-f n)
  ;(trace rec-f)
  (if (< n 3)
      n
      (+ (rec-f (- n 1)) (* 2 (rec-f (- n 2))) (* 3 (rec-f (- n 3))))))

(define (iter-f n)
  (define (iter-f-helper a b c x)
    (let ([fx (+ c b b a a a)])
      (if (< x n)
          (iter-f-helper b c fx (+ x 1))
          fx)))
  ;(trace iter-f-helper)
  (if (< n 3)
      n  
      (iter-f-helper 0 1 2 3)))


;1.12
(define (pascal x y)
  (if (or (= x 0) (= x y))
      1
      (+ (pascal (- x 1) (- y 1)) (pascal x (- y 1)))))

;1.16
(define (power b n)
  (define (power-helper a t n)
    (cond ((= n 0) a)
          ((even? n) (power-helper a (* t t) (/ n 2)))
          (else (power-helper (* a t) t (- n 1)))))
  ;(trace power-helper)
  (power-helper 1 b n))

;1.17, 1.18
(define (multiply x n)
  (define (double x) (+ x x))
  (define (halve x) (/ x 2))
  (define (multi-helper a t n)
    (cond ((= n 0) a)
          ((even? n) (multi-helper a (double t) (halve n)))
          (else (multi-helper (+ a t) t (- n 1)))))
  ;(trace multi-helper)
  (if (< n 0)
      (multi-helper 0 (- x) (- n))
      (multi-helper 0 x n)))
     

     
     