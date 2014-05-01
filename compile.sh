BIN_DIR=./bin
LIBS=.:./lib/wikitext/org.eclipse.mylyn.wikitext.core_2.1.0.N20140325-2210.jar:./lib/wikitext/org.eclipse.mylyn.wikitext.mediawiki.core_updated.jar:./lib/commons-lang/commons-lang3-3.3.2.jar:lib/wimmp.jar:$BIN_DIR

find -wholename "./it/*.java" > sources

mkdir -p $BIN_DIR

javac -cp $LIBS -d $BIN_DIR @sources
