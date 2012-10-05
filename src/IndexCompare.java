import java.util.Comparator;
/**
 * The sole purpose of this class 
 * is to provide a Comparsion
 * between ArrayList of 
 * terms and ID Elements .
 * @author user
 *
 */
public class IndexCompare implements Comparator<InvertedIndexElement>
{
    public int compare(InvertedIndexElement node1, InvertedIndexElement node2)
    {
        if (node1.freq()> node2.freq())
        {
            return -1;
        }
        else if (node1.freq() < node2.freq())
        {
            return 1;
        }
        return 0;
    }
}