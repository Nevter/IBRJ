#TODO: Make this a script instead of a makefile


JC = javac
EXEC = java
SRC = ./src
BIN = ./bin
LIB = /home/luke/Honours/Project/JavaDBR/lib
LIBJAR = $(LIB)/bin/:$(LIB)/jxl.jar:$(LIB)/log4j-core.jar:$(LIB)/log4j.jar:$(LIB)/metouia.jar:$(LIB)/mysql.jar:$(LIB)/ojdbc14_g.jar:$(LIB)/openjgraph.jar:$(LIB)/postgresql.jar:$(LIB)/skinlf.jar
DBR = /edu/uct/dbr

default:
	$(JC) -d $(BIN) -cp .:$(LIBJAR) $(SRC)/$(DBR)/*.java $(SRC)/$(DBR)/gui/*.java $(SRC)/$(DBR)/implication/*.java

run:
	@$(EXEC) -cp .:$(BIN):$(LIBJAR) edu.uct.dbr.DBR

#doc:
#	javadoc -d doc -sourcepath ./src/ -subpackages javabayes
