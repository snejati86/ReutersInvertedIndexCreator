import java.util.ArrayList;

/**
 * In this project each term is not a Single String class
 * each term is a Class consist of a String and an Object
 * of IDElement to store both the ID and Freq
 * Since java doesn't support creating matrix with unknown
 * Rows I decided to create classes
 */
public class InvertedIndexElement

{
String term;
ArrayList<IDElement> id;

public InvertedIndexElement (String term)
{
	this.term=term;
	id=new ArrayList<IDElement>();
}
// A function to count the total number of appearances of a word in all IDs.
public int freq ()
{
	int total=0;
	for (int i=0;i<id.size();++i)
	{
		total+=id.get(i).freq;
	}
 return total;
}



}

