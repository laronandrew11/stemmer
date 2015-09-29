import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		XMLReader reader = new XMLReader();
		ArrayList<String> dataset = new ArrayList<String>();
		ArrayList<String> answerSet = new ArrayList<String>();
		Driver stemmer = new Driver();
		
		try {
			reader.readDirectory(dataset, new File("src/March.xml"));
			
			int size = dataset.size();
			for (int i = 0; i < size; i++) {
				String s = dataset.get(i);
				
				s = s.replaceAll("[^\\w\\s-]", "");
				
				String[] list = s.split("\\s");
				
				System.out.println(s);
				StringBuilder sb = new StringBuilder();
				
				for (String word : list) {
					String output = stemmer.stem(word).toLowerCase();
					//System.out.println(word + " -> " + output);
					sb.append(" " + output);
				}
				
				answerSet.add(sb.toString().trim());
				System.out.println(sb.toString().trim());
			}
			
			FileWriter fw = new FileWriter(new File("output.txt"));
			
			for(String s : answerSet)
				fw.write(s + "\n");
			
			fw.flush();
			fw.close();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO: for each word in the file, get the stem by calling stem() on it
	}
	public String stem(String input)
	{
		String stem = input;
//		String stem=hyphenSearch(input);
//		stem=dictionarySearch(stem);

		stem=inRemoval(stem);
		
		stem=prefixRemoval(stem);
		stem=umRemoval(stem);
		stem=partialReduplication(stem);
		stem=suffixRemoval(stem);

		
//		stem=fullReduplication(stem);
//		System.out.println("AFTER FULL REDUP - " + stem);
		return stem;
		
	}
	/*THE FOLLOWING FUNCTIONS ARE LISTED IN THE ORDER IN WHICH THEY ARE PERFORMED*/
//	public String hyphenSearch(String input)
//	{
//		//identify/separate the parts of a compound or duplicated word?
//		 String [] parts=input.split("-");
//		 return input;//for now; subject to change
//	}
//	public String dictionarySearch(String input)
//	{
//		//if we find a match in the dictionary, we stop?
//		return input;
//	}
	public String inRemoval(String input){
		//remove the infix -in-
		input=removeInfix(input, "in");
		
		return input;
	}
	public String prefixRemoval(String input)
	{
		//general-purpose prefix removal based on prefix rules file
		input=applyPrefixRules(input,"prefixrules.txt");
		return input;
	}

	public String umRemoval(String input)
	{
		//remove the infix -um-
		input=removeInfix(input, "um");
		return input;
	}
	public String partialReduplication(String input)
	{
		//removes partial reduplication (tatawag->tawag)
		if (input.length() < 6)
			return input;
		
		String first = input.substring(0,1);
		String firstTwo = input.substring(0,2);
		String firstThree = input.substring(0,3);
		
		if (input.length() >= 2 && !isConsonant(first.charAt(0))) {
			if (input.charAt(0) == input.charAt(1))
				return input.substring(1);
		}
		
		if (input.length() >= 4 && !isConsonant(firstTwo.charAt(firstTwo.length()-1))) {
			if (input.substring(2).startsWith(firstTwo))
				return input.substring(2);
		}
		
		if (input.length() >= 6 && !isConsonant(firstThree.charAt(firstThree.length()-1))) {
			if (input.substring(3).startsWith(firstThree))
				return input.substring(3);
		}
		
		return input;//or return flag?
		
		// ayy lmao
	}
	public String suffixRemoval(String input)
	{
		//general-purpose suffix removal based on prefix rules file
		input=applySuffixRules(input,"suffixrules.txt");
		return input;
	}
	public String fullReduplication(String input)
	{
		//identify and flag input as a fully reduplicated word (e.g. sama-sama) or compound word (e.g. lingkod-bayan)
		return input;
	}
	//helper functions
	public String getMatchedRegex(String input, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		return m.group();
	}
	public String applyPrefixRules(String input, String filename)
	{
		String result = input;
		String oldResult = result;
		do {
			oldResult = result;
			Scanner sc;
			try {
				sc = new Scanner(new File(filename));
				while (sc.hasNext()) {
					String prefix = sc.next();
					String newResult = "";
					if(result.startsWith(prefix))
						 newResult = result.replaceFirst(prefix, "");
					
					if (checkAcceptability(newResult))
						result = newResult;
				}
				
				sc.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while(!oldResult.equals(result));
		 
		return result;
	}

	public String applySuffixRules(String input, String filename)
	{
		String result = input;
		String oldResult = result;
		do {
			oldResult = result;
			try {
				Scanner sc = new Scanner(new File(filename));

				while (sc.hasNext()) {
					String suffix = sc.next();
					String newResult = "";
					if(result.endsWith(suffix))
						 newResult = result.substring(0, result.length() - suffix.length());
					
					if (checkAcceptability(newResult))
						result = newResult;
				}
				
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while(!oldResult.equals(result));
		 
		return result;
	}
	public String removeInfix(String input, String infix)
	{
		String output = input;
		if (input.length() > 3) {
			output = input.charAt(0) + "";
			output += input.substring(1, input.length()-1).replaceAll(infix, "");
			output += input.charAt(input.length()-1);
		}
		return output;
	}
	
	public boolean checkAcceptability(String stemmedInput) {
		if (stemmedInput.length() == 0)
			return false;
		if (!isConsonant(stemmedInput.charAt(0))) {
			if (stemmedInput.length() < 3)
				return false;
			for (int i = 0; i < stemmedInput.length(); i++)
				if (isConsonant(stemmedInput.charAt(i)))
					return true;
			
			return false;
		}
		
		if (stemmedInput.length() < 4)
			return false;
		
		for (int i = 0; i < stemmedInput.length(); i++)
			if (isConsonant(stemmedInput.charAt(i)))
				return true;

		return false;
	}
	
	public String applyAssimilatoryConditions(String input, String prefix) {
		if (isConsonant(input.charAt(0)))
			return input;
		
		if (prefix.endsWith("m"))
			input = "b" + input; // should be b or p
		else if (prefix.endsWith("n"))
			input = "d" + input; // should be d l s or t
		else if (prefix.endsWith("ng"))
			input = "k" + input; // d l s t k or null
		
		return input;
	}
	
	public String applyPrefixPhonemicTransformation(String input, String prefix){
		//after removing prefix
		String newString=input;
		if(newString.charAt(0)=='r' && !isConsonant(newString.charAt(1)) && !isConsonant(prefix.charAt(prefix.length()-1)))
				newString.replaceFirst("r", "d");
		return newString;
		
	}
	
	public String applySuffixPhonemicTransformation(String input){
		String newString=input;
		if(newString.charAt(newString.length()-2)=='u'&&isConsonant(newString.charAt(newString.length()-1)))
				newString=newString.substring(0,newString.length()-3)+'o'+newString.charAt(newString.length()-1);
		return newString;
	}
	
	public boolean isConsonant(char character)
	{
		switch(character)
		{
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				return false;
		}
		return true;
	}
}
