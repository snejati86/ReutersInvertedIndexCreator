import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.StringTokenizer;
import org.jsoup.*;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * This Class is the main class that gets a Reuter collection
 * and a stopword List and creates an Inverted Index
 * @author user
 */
public class ReuterInvertIndx 
{
/* 
 * Class Variables like the Reuter collection
 * The StopwordList file 
 * and the Inverted Index are stored here 
 */
File ReuterCollection;
static File stop;
ArrayList<InvertedIndexElement> invertedindex;
	
 public ReuterInvertIndx (File rcollection,File Stopword) throws IOException
 {
	    
	    ArrayList<String> sword=CreateStopWord(Stopword);
		//Initialization of InvertedIndex.
	    invertedindex=new ArrayList<InvertedIndexElement>();
		/*In this part we use JSOUP to parse the document and get 
	      the content of what is in the <TEXT> tag of each <REUTERS> collection.
	    */
	    Document doc = null;
		try {
		doc = Jsoup.parse(rcollection, "UTF-8");
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	    Elements elements = doc.getElementsByTag("REUTERS");
		
		/*
		 * Element contains content of <REUTERS>
		 */
		
		for (int i=0;i<elements.size();++i)
		{
			//ArrayList<String> termInText=new ArrayList<String>();
			Attributes attributes = elements.get(i).attributes();
			//Storing ID for creating term Objects.
			int ID=Integer.parseInt(attributes.get("NEWID"));	
			//Using JSOUP to get the <TEXT>
			Elements child=elements.get(i).getElementsByTag("TEXT");
		    
			String StringText=child.first().text().toLowerCase();
		    //Using Trimmer function to get rid of symbols
			StringText=trimmer(StringText);
		    
			ArrayList<String> termInText=ApplyStopWord(StringText, sword);
		    
			
		    for (int it=0;it<termInText.size();++it)
		    	{
		    		//Creating instances of Term+Object and Object
		    		InvertedIndexElement termIE=new InvertedIndexElement(termInText.get(it));
		    		IDElement termid=new IDElement(ID);
		    		//We get rid of redundant words with adding the Freq to their respective ID+Freq Object
		    		for(int j=it+1;j<termInText.size();++j)
		    		{
		    		 if (termInText.get(it).equalsIgnoreCase(termInText.get(j)))
		    		 	{
		    			termid.freq++;
		    			termInText.remove(j);
		    		 	}		    		
		    		}
		    		
		    	    //The terms remaining are added to the inverted index.
		    		termIE.id.add(termid);
		    		invertedindex.add(termIE);
		    	}
	}
	//Removing redundant Objects of String+ID+Freq by matching the string.
	invertedindex=remInvert(invertedindex);
	//Sort for finding top 10.
	Collections.sort(invertedindex,new IndexCompare());
	
	} 
/**
 * This function removes matching Strings from
 * an Inverted Index . by adding their IDs to the first one.
 * @param in
 * @return
 */
 public static ArrayList<InvertedIndexElement> remInvert(ArrayList<InvertedIndexElement> in)
 {
	for (int iter=0;iter<in.size();iter++)
	{
		for (int k=iter+1;k<in.size();k++)
		{
			if(in.get(iter).term.equalsIgnoreCase(in.get(k).term))
			{
				in.get(iter).id.addAll(in.get(k).id);
				in.remove(k);
			}
		}
	}
	return in;
	
 } 	
 /**
  * This function creates an ArrayList of Strings
  * with a give stopword list
  * @param stopfile
  * @return
  * @throws IOException
  */
 private static ArrayList<String> CreateStopWord(File stopfile) throws IOException
	{
	String s;	    
	FileReader tt=new FileReader(stopfile);	 
	BufferedReader buf=new BufferedReader(tt);
	ArrayList<String> sword=new ArrayList<String>();
	while((s = buf.readLine())!= null)
		{
		if (s.isEmpty())
			{
			s=buf.readLine();
			}
		sword.add(s);	
	}buf.close();
	return sword;
	}
 /**
  * This function gets rid of the symbols,tags,etc.
  * @param str
  * @return
  */
 private static String trimmer(String str)
 {
 	 str=str.replaceAll("[0-9]", "");
 	 str=str.replaceAll("[.]", "");
 	 str=str.replaceAll("[,]", "");
 	 str=str.replaceAll("[-]", " ");
 	 str=str.replaceAll("[']", "");
 	 str=str.replaceAll("[:]", "");
 	 str=str.replaceAll("[/]", "");
 	 str=str.replaceAll("[\"]", "");
 	 str=str.replaceAll("[*]", "");
 	 str=str.replaceAll("\\b[\\w']{1,2}\\b", "");
 	 str=str.replaceAll("<[^>]+>", "");
 	 str=str.replaceAll("[<]", "");
 	 str=str.replaceAll("[>]", "");
 	 str=str.replaceAll("[)]", "");
 	 str=str.replaceAll("[(]", "");
 	 return str;	
 }
/**
 * This function takes an ArrayList of Strings
 * and removes the Strings matching a gives ArrayList of
 * Stop words.
 * @param in
 * @param sword
 * @return
 */
 private static ArrayList<String> ApplyStopWord(String in,ArrayList<String> sword)
 {
 	ArrayList<String> last=new ArrayList<String>();
 	StringTokenizer st = new StringTokenizer(in);
     while(st.hasMoreTokens())
     	{
     	String temp=st.nextToken();
     	int flag = 0;
     	for (int k=0;k<sword.size();k++)
     		{
     		if (temp.equalsIgnoreCase(sword.get(k)))
     		{  flag =1; }
     	}
     if (!(flag==1))
     	{
     	last.add(temp);
        }
  
     	}
 	
 	
 	return last;
 	
 }
/**
 * Return the Inverted Index
 * @return
 */
 public ArrayList<InvertedIndexElement> getInvert()
 {
	return invertedindex;
 }


}
