import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath="";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter path and filename of file to be parsed: ");
		try {
			filePath=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO: for each word in the file, get the stem by calling stem() on it
	}
	public String stem(String input)
	{
		String stem=hyphenSearch(input);
		stem=dictionarySearch(stem);
		stem=inRemoval(stem);
		stem=prefixRemoval(stem);
		stem=umRemoval(stem);
		stem=partialReduplication(stem);
		stem=suffixRemoval(stem);
		stem=fullReduplication(stem);
		return stem;
		
	}
	/*THE FOLLOWING FUNCTIONS ARE LISTED IN THE ORDER IN WHICH THEY ARE PERFORMED*/
	public String hyphenSearch(String input)
	{
		//identify/separate the parts of a compound or duplicated word?
		 String [] parts=input.split("-");
		 return input;//for now; subject to change
	}
	public String dictionarySearch(String input)
	{
		//if we find a match in the dictionary, we stop?
		return input;
	}
	public String inRemoval(String input){
		//remove the infix -in-
		input=removeInfix(input, "in");
		return input;
	}
	public String prefixRemoval(String input)
	{
		//general-purpose prefix removal based on prefix rules file
		input=applyRules(input,"prefixrules.txt");
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
		return input;//or return flag?
	}
	public String suffixRemoval(String input)
	{
		//general-purpose suffix removal based on prefix rules file
		input=applyRules(input,"suffixrules.txt");
		return input;
	}
	public String fullReduplication(String input)
	{
		//identify and flag input as a fully reduplicated word (e.g. sama-sama) or compound word (e.g. lingkod-bayan)
		return input;
	}
	//helper functions
	public String applyRules(String input, String filename)
	{
	
				try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				       //apply the rule
				    }
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				return input;
	}
	public String removeInfix(String input, String infix)
	{
		//remove the infix
		return input;
	}
}
