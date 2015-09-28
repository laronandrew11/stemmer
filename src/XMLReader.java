import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class XMLReader {
	Hashtable<String,String> charset = new Hashtable<String,String>();
	
	public XMLReader () {
	}
	
	public void readDirectory(ArrayList<String> articleData, File articleDirectory) throws FileNotFoundException {
		if (!articleDirectory.isDirectory()) {
			readFile(articleData, articleDirectory);
			return;
		}
		
		File[] files = articleDirectory.listFiles();
			
		for (File f : files) {
			if (f.isDirectory())
				System.out.println("Entering directory " + f.getName());
			readDirectory(articleData, f);
		}
	}
	
	public void readFile(ArrayList<String> data, File f) {
		System.out.println("Reading filename " + f.getName());
		
		try {
			Scanner io = new Scanner(new FileInputStream(f));
			ArrayList<String> s = new ArrayList<String>();
			
			while (io.hasNextLine()) {
				String in = io.nextLine();
				if (in.startsWith("<body>")) {
					in = in.replace("<body>", "");
					in = in.replace("</body>", "");
					
					in = replaceEscapedCharacters(in);
					
					//System.out.println(in);
					s.add(in);
				}
			}
			
			data.addAll(s);
			
			io.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String replaceEscapedCharacters(String s) {
        s = s.replace("&lt;","<");
        s = s.replace("&gt;",">");
        s = s.replace("&amp;","&");
        s = s.replace("&quot;","\"");
        s = s.replace("&ntilde;","ñ");
		s = s.replace("&Ntilde;","Ñ");
		s = s.replace("&#145;", "`");
		s = s.replace("&#146;", "'");
		s = s.replace("&Atilde;&plusmn;", "ñ");
//		s = s.replace("&#150;", "");
		
		return s;
	}
}
