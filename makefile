# Defina o nome do JAR gerado
JAR_NAME = cabralnavegador-1.0.jar

# Comando padrão (executado quando você digita apenas "make")
all: clean build run

# Limpa o projeto
clean:
	mvn clean

# Compila o projeto e gera o JAR
build:
	mvn package

# Executa o JAR gerado
run:
	cd target && java -jar $(JAR_NAME)

# Remove o JAR e outros arquivos gerados
distclean: clean
	rm -rf target