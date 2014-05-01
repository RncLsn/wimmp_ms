package it.uniroma1.lcl.wimmp.parse;

import it.uniroma1.lcl.wimmp.Configuration;

import org.eclipse.mylyn.wikitext.mediawiki.core.TemplateResolver;
import org.eclipse.mylyn.wikitext.mediawiki.core.Template;
import java.util.regex.*;
import java.util.*;
import java.io.*;

public class MalayTemplateResolver extends TemplateResolver {

    private static final Pattern templatePattern = Pattern.compile("(et-[^}\\{\\|]*|head)");
    private static final Pattern namePattern = Pattern.compile("\\{\\{\\{name\\}\\}\\}");
    
    private static Map<String, String> templates = new HashMap();
	static {
	    String srcPath = (String) Configuration.getResource("src");
	    String etTemplatesPath = (String) Configuration.getResource("et-templates");
	    String fileName = srcPath + etTemplatesPath;
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] ls = line.split("=");
				templates.put(ls[0].trim(), ls[1].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public Template resolveTemplate(String templateName) {
        templateName = templateName.trim().toLowerCase();
        if (templatePattern.matcher(templateName).matches()) {
            Template t = new Template();
            t.setName(templateName);
            String pattern = findPattern(templateName); 
            if (pattern != null) {
                pattern = namePattern.matcher(pattern).replaceAll(templateName);
                t.setTemplateMarkup(pattern);
                return t;
            }
        }
        
        return null;
    }
    
    private String findPattern(String templateName) {
        for(String p : templates.keySet()) {
            if(Pattern.matches(p, templateName)) {
                return templates.get(p);
            }
        }
        return null;
    }
}
