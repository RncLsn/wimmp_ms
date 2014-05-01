BIN_DIR=./bin
LIBS=.:./lib/wikitext/org.eclipse.mylyn.wikitext.core_2.1.0.N20140325-2210.jar:./lib/wikitext/org.eclipse.mylyn.wikitext.mediawiki.core_updated.jar:./lib/commons-lang/commons-lang3-3.3.2.jar:lib/wimmp.jar:$BIN_DIR

java -cp $LIBS it.uniroma1.lcl.wimmp.Test Estonian et /media/dunadan/F04C5AD14C5A91E8/Downloads/enwiktionary-20140328-pages-meta-current.xml
