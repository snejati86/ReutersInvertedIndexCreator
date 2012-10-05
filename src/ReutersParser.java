import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ReutersParser 

{
	


public static void main(String[] args) throws IOException 
	{
	//Input two files.
	File input = new File("reut2-008.sgm");
	File input2= new File("reut2-020.sgm");
	File stopword=new File("StopwordList.txt");
	
	//Create inverted index
	ReuterInvertIndx file1=new ReuterInvertIndx(input,stopword);
	ReuterInvertIndx file2=new ReuterInvertIndx(input2,stopword);
	ArrayList<InvertedIndexElement>invertindex=(file1.getInvert());
	//Merge two
	invertindex.addAll(file2.getInvert());
	//Remove redundant words between files.
	invertindex=ReuterInvertIndx.remInvert(invertindex);
	//Sort
	Collections.sort(invertindex,new IndexCompare());
	//Print the top ten.
	System.out.print("The Top Ten Terms in these two files are :\n");
	for (int g=0;g<10;g++)
	{
		System.out.print((g+1)+") "+"Term: "+invertindex.get(g).term+" . With "+invertindex.get(g).freq()+" Number of appearences\n");
	}
	
	System.out.print("\n The bottom Ten Terms in these two files are :\n");
	for (int g=invertindex.size()-1;g>invertindex.size()-11;g--)
	{
		System.out.print((invertindex.size()-g)+") "+"Term: "+invertindex.get(g).term+" . With "+invertindex.get(g).freq()+" Number of appearences\n");
	}
		
    }
	
}

