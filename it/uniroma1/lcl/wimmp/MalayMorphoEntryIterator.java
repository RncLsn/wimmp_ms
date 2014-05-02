package it.uniroma1.lcl.wimmp;

import it.uniroma1.lcl.wimmp.MorphoEntryListener;
import it.uniroma1.lcl.wimmp.parser.WiktionaryParser;
import it.uniroma1.lcl.wimmp.parse.MalayTextProcessor;

import java.lang.IllegalArgumentException;
import java.lang.InterruptedException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class MalayMorphoEntryIterator extends MorphoEntryIterator {
    
    private BlockingQueue<MorphoEntry> queue;
    private MalayMorphoEntryProducer producer;
    
    class MalayMorphoEntryProducer implements Runnable, MorphoEntryListener {
        
        private final String dataset;
        private final BlockingQueue<MorphoEntry> queue;
        private boolean finish = false;
        
        MalayMorphoEntryProducer(String dataset, BlockingQueue<MorphoEntry> queue) {
            this.dataset = dataset;
            this.queue = queue;
        }
        
        public void run() {
            WiktionaryParser parser = new WiktionaryParser(dataset);
            MalayTextProcessor processor = new MalayTextProcessor();
            processor.addMorphoEntryListener(this);
            parser.addTextProcessor(processor);
            try {
                parser.parse();
            } catch(Exception ex) {
                ex.printStackTrace();
                finish = true;
            }
        }
        
        public void morphoEntry(MorphoEntry entry) {
            try {
                queue.put(entry);
            } catch(InterruptedException ex) {
                ex.printStackTrace();
                finish = true;
            }
        }
        
        public void finish() {
            finish = true;
        }
        
        public boolean isFinished() {
            return finish;
        }
    }

    public MalayMorphoEntryIterator(String[] dumps) {
        super(dumps);
        
        if (dumps.length < 1) {
            throw new IllegalArgumentException("No dump file received.");
        }
        
        queue = new ArrayBlockingQueue<MorphoEntry>(20);
        producer = new MalayMorphoEntryProducer(dumps[0], queue);
        new Thread(producer).start();
    }
    
    private MorphoEntry next = null;
    private boolean used = false;
    
    public boolean hasNext() {
        if (!(queue.isEmpty() && producer.isFinished())) {
            try {
                next = queue.take();
            } catch(InterruptedException ex) {
                ex.printStackTrace();
                return false;
            }
            used = false;
            return true;
        }
        return false;
    }
    
    public MorphoEntry next() {
        if(!used) {
            return next;
        } else if (hasNext()) {
            return next;
        } else {
            return null;
        }
    }
}
