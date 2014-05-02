# variables #

## create PWD with escaped spaces ##
nullstring=# it's an empty string!
space=$(nullstring) # a space at the end
ESCAPED_PWD=$(subst $(space),\ ,$(PWD))

## workspace ##
WORKSPACE=$(ESCAPED_PWD)

## java libraries ##
JAVA_LIB=.:$(WORKSPACE)/lib/wikitext/org.eclipse.mylyn.wikitext.core_2.1.0.N20140325-2210.jar:$(WORKSPACE)/lib/wikitext/org.eclipse.mylyn.wikitext.mediawiki.core_2.1.0.N20140325-2210.jar:$(WORKSPACE)/lib/commons-lang/commons-lang3-3.3.2.jar:$(WORKSPACE)/lib/wimmp.jar:$(JAVA_BIN)

## bin dir ##
JAVA_BIN=$(WORKSPACE)/bin

## all the classes of the project with name package/subpack1/.../ClassName.java ##
JAVA_CLASSES=it/uniroma1/lcl/wimmp/* it/uniroma1/lcl/wimmp/parse/* it/uniroma1/lcl/wimmp/parser/*

## src files of the java classes, with relative path ###
JAVA_SRC=$(JAVA_CLASSES:=.java)

## class files of the java classes, with relative path ###
JAVA_DOTCLASS=$(JAVA_CLASSES:=.class)

INPUT_DATASET=/home/alessandro/Scaricati/Datasets/enwiktionary-20140415-pages-articles.xml

# rules #

all:
	cd $(WORKSPACE) && javac -cp $(JAVA_LIB) -d $(JAVA_BIN) $(JAVA_SRC)

.PHONY: run
run:
	java -cp $(JAVA_LIB) it.uniroma1.lcl.wimmp.Test Malay ms $(INPUT_DATASET)
#	java -cp $(JAVA_LIB) it.uniroma1.lcl.wimmp.WiktionaryXmlParser parse /home/alessandro/Scaricati/Datasets/mswiktionary-20140315-pages-articles.xml ms

.PHONY: clean
clean:
	cd rm -r $(JAVA_BIN)/*


