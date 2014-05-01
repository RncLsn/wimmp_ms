package it.uniroma1.lcl.wimmp.parser;

public interface TextProcessor {
    public void startProcess();
    public void processText(String title, String text);
    public void endProcess();
}
