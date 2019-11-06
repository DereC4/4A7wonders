import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
private ArrayList<Card> ageOne;
private ArrayList<Card> ageTwo;
private ArrayList<Card> ageThree;
private ArrayList<Card> discard;
public Deck(ArrayList<Card> a1,ArrayList<Card> a2,ArrayList<Card> a3,ArrayList<Card> d)
{
ageOne = a1;
ageTwo = a2;
ageThree = a3;
discard = d;
}
public void addDiscard(Card c)
{
	discard.add(c);
}
public void removeDiscard(Card c)
{
	discard.remove(c);
}
public void draw(Player p, int age)
{
	if(age==1)
	{
		ageOne.remove(ageOne.size()-1);
		//add card to player
	}
	else if(age==2)
	{
		ageTwo.remove(ageTwo.size()-1);
		//add card to player
	}
	else if(age==3)
	{
		ageThree.remove(ageThree.size()-1);
		//ad card to player
	}
	
}
public void shuffle(int age)
{
	if(age==1)
	{
		Random rnd = ThreadLocalRandom.current();
	    for (int i = ageOne.size()-1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ageOne.get(i);
	     ageOne.get(index) = ageOne.get(i);
	      ar[i] = a;
	    }
	}
	else if(age==2)
	{
		
	}
	else if(age==3)
	{
		
	}
}
 
}
