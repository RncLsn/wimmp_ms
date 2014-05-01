WORKSPACE='/home/alessandro/Dropbox/Sapienza/Minr/Semester II/Natural Language Processing/Homework1/Malay/wimmp'
JAVA_LIB=.:$(WORKSPACE)/lib/wikitext/org.eclipse.mylyn.wikitext.core_2.1.0.N20140325-2210.jar:$(WORKSPACE)/lib/wikitext/org.eclipse.mylyn.wikitext.mediawiki.core_2.1.0.N20140325-2210.jar:$(WORKSPACE)/lib/commons-lang/commons-lang3-3.3.2.jar:$(WORKSPACE)/lib/wimmp.jar:$(JAVA_BIN)
JAVA_BIN=$(WORKSPACE)/bin

JAVA_CLASSES=it/uniroma1/lcl/wimmp/* it/uniroma1/lcl/wimmp/parse/et/* it/uniroma1/lcl/wimmp/parse/ms/* it/uniroma1/lcl/wimmp/parse/* it/uniroma1/lcl/wimmp/build/et/*

## src files of the java classes, with relative path ###
JAVA_SRC=$(JAVA_CLASSES:=.java)

## class files of the java classes, with relative path ###
JAVA_DOTCLASS=$(JAVA_CLASSES:=.class)

all:
	cd $(WORKSPACE) && javac -cp $(JAVA_LIB) -d $(JAVA_BIN) $(JAVA_SRC)

.PHONY: run
run:
	java -cp $(JAVA_LIB) it.uniroma1.lcl.wimmp.WiktionaryXmlParser parse /home/alessandro/Scaricati/Datasets/mswiktionary-20140315-pages-articles.xml ms
	#java -cp $(JAVA_LIB) it.uniroma1.lcl.wimmp.WiktionaryXmlParser build /home/alessandro/Scaricati/Datasets/enwiktionary-20140415-pages-articles.xml # > $(OUTPUT)2 #/home/alessandro/Scaricati/Datasets/et-temp.xml

.PHONY: clean
clean:
	cd $(WORKSPACE) && find . -name \*.class -type f -delete


