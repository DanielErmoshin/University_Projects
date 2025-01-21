# Variables
SRC_DIR = src/main/java
OUT_DIR = ../EXE/EXECUTABLE
JAVA_FILES = $(shell find $(SRC_DIR) -name "*.java")
CLASS_FILES = $(JAVA_FILES:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)
MAIN_CLASS = Main
LIBRARY_DIR = lib
LIBRARY_FILES = $(wildcard $(LIBRARY_DIR)/*.jar)
JAR_NAME = $(OUT_DIR)/Programa.jar

JAVAC = javac
JAVAC_FLAGS = -d $(OUT_DIR) -sourcepath $(SRC_DIR) -cp $(LIBRARY_FILES)

JVM_FLAGS = -cp $(OUT_DIR):$(LIBRARY_FILES)

all: $(CLASS_FILES)

build: all

$(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	mkdir -p $(dir $@)
	$(JAVAC) $(JAVAC_FLAGS) $<

clean:
	rm -rf $(OUT_DIR)/*

run: all
	java $(JVM_FLAGS) $(MAIN_CLASS)

jar: all
	jar cfe $(JAR_NAME) $(MAIN_CLASS) -C $(OUT_DIR) .

help:
	@echo "Objectius del Makefile:"
	@echo "  all    - Compilar tots els arxius .java"
	@echo "  build  - Alies per 'all'"
	@echo "  clean  - Netejar el directori de sortida"
	@echo "  run    - Executar el programa principal"
	@echo "  jar    - Generar l'arxiu .jar del projecte"
	@echo "  help   - Mostrar aquesta ajuda"
