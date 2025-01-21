from antlr4 import *
import sys
from schemeLexer import schemeLexer
from schemeParser import schemeParser
from schemeVisitor import schemeVisitor
from functools import reduce


ops = {
    '+': lambda exps: exps[0] + exps[1],
    '-': lambda exps: exps[0] - exps[1],
    '*': lambda exps: exps[0] * exps[1],
    '/': lambda exps: exps[0] // exps[1],

    '>': lambda exps: exps[0] > exps[1],
    '<': lambda exps: exps[0] < exps[1],
    '>=': lambda exps: exps[0] >= exps[1],
    '<=': lambda exps: exps[0] <= exps[1],
    '=': lambda exps: exps[0] == exps[1],
    '<>': lambda exps: exps[0] != exps[1],
    'mod': lambda exps: exps[0] % exps[1]
}

# Conversion from boolean to BOOL in scheme.g4 grammar
def to_scheme_boolean(value):
    return "#t" if value else "#f"

# Conversion from BOOL to boolean
def to_python_boolean(value):
    return value == "#t"

# Print results depending on the result type
def myPrint(result):
    if isinstance(result, list):
        # Checks that lists do not have any empty values
        formatted_elements = []
        for r in result:
            if isinstance(r, bool):
                r = to_scheme_boolean(r)
            if r is not None:
                formatted_elements.append(str(r))
        print(f"({' '.join(formatted_elements)})")
    elif isinstance(result, bool):
        print(to_scheme_boolean(result))
    elif result is not None:
        print(result)


class EvalVisitor(schemeVisitor):
    def __init__(self):
        self.vars = {}  # Global variables
        self.func = {}  # Defined functions
        self.lines = None  # Stores the lines from standard input
        self.line_index = 0  # Index to track the current line

    def visitRoot(self, ctx):
        total_exps = list(ctx.getChildren())

        # Verify that there are no standalone functions
        for exps in total_exps:
            if exps.getChild(1).getText() != 'define':
                raise Exception(f"Invalid expression outside of a defined function: {exps.getText()}")

        # Process all function and variable definitions
        for exps in total_exps:
            self.visit(exps)

        # Automatically execute 'main' if defined
        if 'main' in self.func:
            result = self.call_function(self.func['main'], [])
            return result  # Directly return the result of 'main'
        else:
            # Raise an exception if 'main' is not defined
            raise Exception("A defined 'main' function was not found.")

    def get_callable(self, name):
        # Local function in self.vars
        if name in self.vars and isinstance(self.vars[name], tuple):
            return self.vars[name] 
        
        # Global function in self.func
        if name in self.func:
            return self.func[name]  
        
        # Internal operator
        if name in ops:
            return ops[name] 
        
        return None

    def call_function(self, func_op, args):
        if isinstance(func_op, tuple):
            parameters, body = func_op
            if len(args) != len(parameters):
                raise Exception(f"The function expects {len(parameters)} arguments, but {len(args)} were passed")

            # Creates a temporary "stack" to store the association between parameters and arguments
            local_vars = self.vars.copy()
            for param, arg in zip(parameters, args):
                # If the argument is a global function, store it in the parameter entry in the "stack"
                if isinstance(arg, str) and arg in self.func:
                    local_vars[param] = self.func[arg]
                # If the argument is a local function, store it in the parameter entry in the "stack"
                elif isinstance(arg, str) and arg in self.vars and isinstance(self.vars[arg], tuple):
                    local_vars[param] = self.vars[arg]
                # Assign as a regular value to the parameter in the "stack"
                else:
                    local_vars[param] = arg
            
            previous_vars = self.vars
            self.vars = local_vars

            result = None
            # Take the last result that is NOT None
            for expr in body:
                current_result = self.visit(expr)
                if current_result is not None:
                    result = current_result

            self.vars = previous_vars
            return result
        else:
            # Internal operator, apply it to 'args'.
            return func_op(args)

    def visitDefaultOp(self, ctx):
        # Get the list of children and extract the operator name
        full_expression = list(ctx.getChildren())
        operator = full_expression[1]
        operator_name = operator.getText()

        # Evaluate the arguments
        args = [self.visit(e) for e in ctx.expr()]

        # Check if it's a local, global function or internal operator
        call = self.get_callable(operator_name)
        if call is None:
            raise Exception(f"Operator or function {operator_name} not defined.")

        # Call the function / operator
        return self.call_function(call, args)

    def visitConstAssig(self, ctx):
        const = ctx.VAR() 
        value = self.visit(ctx.expr())
        self.vars[const.getText()] = value

    def visitDefineFunc(self, ctx):
        func_name = ctx.VAR(0).getText() # Take the function name as the first VAR that appears
        parameters = [param.getText() for param in ctx.VAR()[1:]] # Get the parameters (all VARs after the first one)
        exprs = [exp for exp in ctx.expr()] # Get all expressions
        
        self.func[func_name] = (parameters, exprs)

    def visitLetClasue(self, ctx):
        local_vars = self.vars.copy()

        # Read everything passed to let
        for local in ctx.myLet():
            var_name = local.VAR().getText()      
            value = self.visit(local.expr())         
            local_vars[var_name] = value

        # Save global variables and set local ones
        previous_vars = self.vars
        self.vars = local_vars

        # Evaluate the expression within the local environment
        let_results = [self.visit(expr) for expr in ctx.expr()]

        # Return to the original global environment
        self.vars = previous_vars

        # Return the only expression that is not None
        for result in let_results:
            if result is not None:
                return result

        return None


    ########################################## I/O #############################################
    def visitRead(self, ctx):
        # Initialize the input stream only once
        if self.lines is None:
            self.lines = [line.strip() for line in sys.stdin]  # Read all lines
            self.line_index = 0

        # Check if there are more lines to read
        if self.line_index >= len(self.lines):
            raise Exception("No more data in the stream to read")

        # Get the current line and advance the index
        current_line = self.lines[self.line_index]
        self.line_index += 1

        # Process and evaluate the read expression
        input_stream = InputStream(current_line)
        local_lexer = schemeLexer(input_stream)
        local_tokens = CommonTokenStream(local_lexer)
        local_parser = schemeParser(local_tokens)
        sub_tree = local_parser.expr()


        result = self.visit(sub_tree)
        return result

    # Takes the passed value and prints it 
    def visitDisplay(self, ctx):
        [_, _, value, _] = list(ctx.getChildren())
        myPrint(self.visit(value))

    def visitNewLine(self, ctx):
        print()

    ############################################################################################  

    def visitConditional(self, ctx):
        for clause in ctx.cond():
            # If it's an 'else', process the associated expression directly
            if clause.getChild(1).getText() == 'else':
                return self.visit(clause.getChild(2))

            # Otherwise, evaluate the condition and the body
            condition = self.visit(clause.expr(0))
            if condition:
                return self.visit(clause.expr(1))

        return None

    def visitIf(self, ctx):
        cond = self.visit(ctx.expr(0))  # Condition
        if cond:
            return self.visit(ctx.expr(1)) 
        return self.visit(ctx.expr(2)) 
    
    def visitAnd(self, ctx):
        for e in ctx.expr():
            if not self.visit(e):
                return False
        return True  

    def visitOr(self, ctx):
        for e in ctx.expr():
            if self.visit(e):
                return True
        return False

    def visitNot(self, ctx):
        condition = self.visit(ctx.expr())
        return not condition
    
    def visitList(self, ctx):
        elem = list(ctx.getChildren())[2:-1]
        elem_inList = [self.visit(e) for e in elem]
        return elem_inList
    
    def visitCar(self, ctx):
        llista = self.visit(ctx.expr())  
        if self.visitNull(ctx):  
            return None  
        return llista[0] 

    def visitCdr(self, ctx):
        llista = self.visit(ctx.expr()) 
        if self.visitNull(ctx):  
            return []  
        return llista[1:] 

    def visitCons(self, ctx):
        element = self.visit(ctx.expr(0))
        llista = self.visit(ctx.expr(1))
        return [element] + llista 

    def visitNull(self, ctx):
        llista = self.visit(ctx.expr())
        if (len(llista) == 0): 
            return True
        return False
    
    def visitNumero(self, ctx):
        [num] = list(ctx.getChildren())
        return int(num.getText())

    def visitVariable(self, ctx):
        var_name = ctx.VAR().getText()
        # 1) Check if the variable is in the local environment
        if var_name in self.vars:
            return self.vars[var_name]
        # 2) Check if it is in global functions
        elif var_name in self.func:
            return var_name 
        else:
            return None  

    def visitBoolean(self, ctx):
        boolean = ctx.BOOL().getText()
        return to_python_boolean (boolean)
    
    def visitString(self, ctx):
        return ctx.getText()


################################################################################################
if len(sys.argv) < 2:
        print("Usage: python3 scheme.py file.scm")
        sys.exit(1)
file_path = sys.argv[1]

input_stream = FileStream(file_path, encoding='utf-8')
lexer = schemeLexer(input_stream)
token_stream = CommonTokenStream(lexer)
parser = schemeParser(token_stream)
tree = parser.root()

evaluator = EvalVisitor()
result = evaluator.visit(tree)

myPrint(result)
