# Variables
ANTLR4 = antlr4  # Commando ja configurat al PATH
GRAMMAR = scheme.g4

# Objetivos principales
all: schemeLexer.py schemeParser.py schemeVisitor.py

# Generar archivos de ANTLR
schemeLexer.py schemeParser.py schemeVisitor.py: $(GRAMMAR)
	$(ANTLR4) -Dlanguage=Python3 -no-listener -visitor $(GRAMMAR)

# Limpiar archivos generados
clean:
	rm -f schemeLexer.py schemeParser.py schemeVisitor.py *.interp *.tokens
