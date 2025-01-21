# Mini-Scheme

`Mini-Scheme` is an **interpreter** for a simplified version of Scheme, using a **grammar** defined in `scheme.g4` with ANTLR4 and an **evaluator** (visitor) in Python. It is characterized by a simple and concise syntax, the definition of functions and variables, a basic I/O system, support for higher-order functions, and recursion.

Below, the **structure** of the language, the available **data types**, the provided **instructions** or constructs, and some **usage examples** are explained.


## 1. Introduction

The language supports:

1. **Definition of constants** and **functions**.
2. **Evaluation of arithmetic** and **relational expressions**.
3. **Control flow** with `if`, `cond`, `and`, `or`, `not`.
4. **Creation and manipulation of lists** using `cons`, `car`, `cdr`, and `null?`.
5. **Evaluation of nested expressions** and **use of local environments** with `let`.
6. **Basic Input/Output**: `read`, `display`, and `newline`.
7. **Recursion** and **higher-order functions**.

The main program is contained in a `*.scm` file. When executing `python3 scheme.py file.scm`, this interpreter:

1. **Reads** the definitions of functions and global variables.
2. **Searches** for a function named `main` to execute it.
3. **Evaluates** the resulting expressions and displays the output.

Execution is performed with the following command:
```
python3 scheme.py file.scm < file.inp > file.out ; These last two depend on the use of I/O
```

## 2. General Grammar (`scheme.g4`)

The grammar in `scheme.g4` describes the **rules** of the language. The evaluation of each expression is implemented in `scheme.py` in Python.  
A program consists of one or more expressions at the root:

```
root : expr+ ;
```
Expressions (**expr**) include function calls, variable definitions, function definitions, control structures, predefined operations, and all the data types supported by `Mini-Scheme`.

## 3. Data Types
Given its minimalist design, `Mini-Scheme` does not support operations between different variable types.  
Below are the different data types supported by the language:

### Numbers
- Integers: `4`, `-10`...

### Variables
- Variables: Identifiers starting with a letter `[a-zA-Z]`, with subsequent characters including digits, hyphens, and question marks. These are used for declaring variables and functions.   
Examples: `x`, `func1`, `even?`, `my-function`.

### Booleans
- Booleans: `#t` (true) and `#f` (false). Internally, they are converted to `True` / `False` in Python.

### Strings
- Strings: `"Text strings"`. These are text strings that can include any UTF-8 character, provided they are enclosed in double quotes `"`.

### Lists:
- These are collections of elements that can be of different types. Internally, they are interpreted as Python lists. They are declared as follows: `'(element1 element2 element3 ...)`   
Examples: `'(1 2 3)`, `'(#t, #f, #t)`...  
Although elements can be of different types, mixing types is generally not useful, as the language does not support operations between different types for more complex functions on lists (besides the predefined list operators).

## 4. Expressions

### Defining Constants:
First, the variable name is defined, and then it is assigned a value, which, as previously mentioned, is an expression. `( 'define' ( <variable-name> <value> ))`.
```
(define x 42)
```

### Defining Functions:
First, the name and parameters are defined, followed by the expressions of the function. `( 'define' ( <function-name> <param1> <param2> ...) <expression1> <expression2> ... )`.
```
(define (my-function param1 param2) (+ x y) (* x y)
)
```

### Function Calls:
The first element must be a built-in operator or a previously defined function. The remaining elements are the arguments passed to the operator or function. `(<operator or function> <argument1> <argument2> ...)`
```
(my-function 42 30)

(+ 2 3)
```

### Built-in Operators:
The built-in operators are predefined in the language and allow for basic calculations and comparisons. They are divided into two main categories:
- Arithmetic: `+ ,-, *, /, mod`
- Relational: `>, <, >=, <=, =, <>`

### Conditionals:
`if`: Evaluates the first condition. If the condition is true (`#t`), it executes the first expression (`expr-if-true`); otherwise, it executes the second one (`expr-if-false`).
```
(if condition
    expr-if-true
    expr-if-false)
```

`cond`: Evaluates each condition in order. `else` can be replaced by `#t`.
```
(cond
    ((condition1) expr1)
    ((condition2) expr2)
    ...
    (else default-expr))
```
Example:
```
(cond
    ((> x 10) "Greater than 10")
    ((= x 10) "Equal to 10")
    (else "Less than 10"))
```

### Let
The `let` statement allows for defining **local variables** within an expression, enabling intermediate values to be used without modifying the global environment.
First, variables in the global environment are declared and assigned an expression. This expression can be any data type or an I/O expression. Multiple functions can then be evaluated. In my implementation, only one expression is allowed to return a result due to ambiguities in the specification.
```
(let ((var1 e1)
      (var2 e2)
      ...)
    expression1
    expression2
    ...)
```
Example:
```
(let ((x 5)
      (y 3))
    (display (+ x y))  ; Outputs: 8
    (* x y))           ; Result: 15
```

### Logical Operators:
Logical operators work with the previously described booleans (`#t` or `#f`) and allow for combining or inverting conditions.
- `(and expr1 expr2 ...)`: Returns `#t` if all expressions are true (`#t`).
- `(or expr1 expr2 ...)`: Returns `#t` if any expression is true (`#t`).
- `(not expr)`: Returns `#t` if `expr` is `#f`, and vice versa.

### List Operators:
**Lists** are a fundamental element in this language. They have several associated internal operators that provide multiple functionalities, as shown below.

- #### `Car`: Get the first element
    The `car` operator returns the first element of a list. If the list is empty, it returns `None`. `(car list)`  
    Example:
    ```
        (car '(1 2 3 4))            ; Result: 1
        (car '("hello" "world"))    ; Result: "hello"
        (car '())                   ; Result: None
    ```
- #### `Cdr`: Get the rest of the elements
    The `cdr` operator returns all elements of the list except the first. If the list is empty, it returns an empty list. `(cdr list)`  
    Example:
    ```
        (cdr '(1 2 3 4))            ; Result: '(2 3 4)
        (cdr '("hello" "world"))    ; Result: '("world")
        (cdr '())                   ; Result: '()
    ```
- #### `Null?`: Check if a list is empty
    The `null?` operator checks if a list is empty and returns `#t` if it is, or `#f` otherwise. `(null? list)`  
    Example:
    ```
        (null? '(1 2 3))            ; Result: #f
        (null? '())                 ; Result: #t
    ```
- #### `Cons`: Add an element to the beginning
    The `cons` operator adds an element to the beginning of a list. `(cons element list)`  
    Example:
    ```
        (cons 0 '(1 2 3 4))         ; Result: '(0 1 2 3 4)
        (cons "hello" '("world"))   ; Result: '("hello" "world")
        (cons #t '())               ; Result: '(#t)
    ```
These operators can be combined as shown in the examples below:
```
(car (cons 0 '(1 2 3)))             ; Result: 0
(null? (cdr '(1)))                  ; Result: #t
```
One interesting consideration about lists is their immutability. The operators only return an element or a new list without modifying the original list. To do so, a new list must be defined with the same name as the original, applying the necessary changes.
Example:
```
(cons 0 list)                       ; Returns a new list with 0 at the beginning and the rest as the original list.
(define list (cons 0 list))         ; This modifies (overwrites) the original list by applying the performed change.
```

## 5. I/O
This language includes three explicit expressions for managing input and output: `read`, `display`, and `newline`.
Input is performed by redirecting a file for input `< file.inp`, and output can be directed to the console (by default) or a file using ` > file.out`.

### `Read`
The `read` function allows reading a line from the standard input. In my solution, it parses it as an expression and evaluates it independently, returning a value from the `Mini-Scheme` data types. `(read)`

### `Display`
The `display` function writes the result of an expression in the standard output **without** including an automatic newline. `(display expression)`.
Example:
```
(display "Hello")
(display " world!")  ; Result: Hello world!
```

### `Newline`
The `newline` function prints a newline to the standard output. `(newline)`
Example:
```
(display "Hello")
(newline)
(display "world!")  ; Result: Hello
                                   world!
```

## 6. Functional Features
`Mini-Scheme` stands out for its compatibility with recursion and higher-order functions, two fundamental pillars of functional programming. These features allow for tackling complex problems in a clear and elegant way.

### Recursion
Recursion is primarily used for working with lists and performing repetitive calculations in the absence of loops (structures for performing iterations).
Example:
```
(define (factorial n)
    (if (= n 0)
        1
        (* n (factorial (- n 1)))))

(display (factorial 5))  ; Result: 120
```

### Higher-Order Functions
These functions can receive other functions as arguments and also return functions as results. In my `Mini-Scheme` implementation, although I should have created a well-structured stack, the way I approach higher-order functions is by creating **local environments** to store the **execution state** at that precise moment.  
When a function is called, the current environment is saved, allowing for the temporary modification of variables, with the possibility of overwriting existing ones. This ensures that once the call is completed, the original environment is restored, enabling both global and local functions. Local functions are also stored in the variable table within the environment, simplifying variable and function management.  
Example: Using a foldl implementation.
```
(define (foldl f acc list)      ; f is a function passed as an argument
  (if (null? list)
        acc
        (foldl f (f acc (car list)) (cdr list))))
(foldl sum 0 '(1 2 3 4 5))      ; Result: 15
```

## 7. Conclusion
Despite being a very simple language, `Mini-Scheme` already allows for interesting tasks involving recursion and higher-order functions, which I personally enjoyed, especially when realizing I could recreate Haskell functions, expanding the possibilities for computation and abstraction.  
Overall, this project has been one of the most interesting I have done so far, giving me the opportunity to explore the world of programming languages and understand how they are constructed, even if it’s just the tip of the iceberg.
