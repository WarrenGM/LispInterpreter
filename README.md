Scheme REPL
=========

The beginnings of a Scheme interpreter.

Current Features
---------------------
* First class functions with recursion supported.


Issues and to do list
----------------------
* Support complex numbers and numbers in base 2, base 8, and base 16.
* Implement other atoms: Strings, characters, and vectors.
* Accept pair syntax inputs such as: `(1 . (2 . ( 3 . () )))`.
* Accept comments.
* Recognize and display errors. The interpreter currently expects the program to be correct.
* Import files.
* Library functions such as `max`, `string`, `integer->string`.
* Proper closures and lexical scoping. Currently if we have
  ```
  (define x 2)
  (define (f n) x)
  (f 3) ;; == 2
  (define x 4)
  (f 3) ;; == 4
  ```