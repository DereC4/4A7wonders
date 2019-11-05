import java.util.ArrayList;

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
 
}
