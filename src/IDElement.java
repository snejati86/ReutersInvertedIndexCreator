/** this class creates IDElement which consists of 
 * ID number of the <REUTERS> collection the term 
 * has appeared in , and the frequency (freq)
 * on that.
 * 
 */
public class IDElement
{
int ID;
int freq;


public IDElement (int id)
{
	this.ID=id;
	//Initially the freq is set to 1 because the first time the word is found we have 1 appearances
	this.freq=1;
}


}
