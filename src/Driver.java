import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/*THE FOLLOWING FUNCTIONS ARE LISTED IN THE ORDER IN WHICH THEY ARE PERFORMED*/
	public void hyphenSearch(String input)
	{
		//identify/separate the parts of a compound or duplicated word?
		 String [] parts=input.split("-");
	}
	public void dictionarySearch(String input)
	{
		//if we find a match in the dictionary, we stop?
	}
	public void inRemoval(String input){
		//remove the infix -in-
		removeInfix(input, "in");
		
	}
	public void prefixRemoval(String input)
	{
		//general-purpose prefix removal based on prefix rules file
		applyRules(input,"prefixrules.txt");
	}
	public void umRemoval(String input)
	{
		//remove the infix -um-
		removeInfix(input, "um");
	}
	public void partialReduplication(String input)
	{
		//removes partial reduplication (tatawag->tawag)
	}
	public void suffixRemoval(String input)
	{
		//general-purpose suffix removal based on prefix rules file
		applyRules(input,"suffixrules.txt");
	}
	public void fullReduplication(String input)
	{
		//identify and flag input as a fully reduplicated word (e.g. sama-sama) or compound word (e.g. lingkod-bayan)
		
	}
	//helper functions
	public void applyRules(String input, String filename)
	{
	
				try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				       //apply the rule
				    }
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public void removeInfix(String input, String infix)
	{
		//remove the infix
		
	}
}
