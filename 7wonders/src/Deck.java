import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Deck
{
    private ArrayList < Card > ageOne;
    private ArrayList < Card > ageTwo;
    private ArrayList < Card > ageThree;
    private ArrayList < Card > discard;
    public Deck() throws IOException
    {
    	ageOne = new ArrayList<Card>();
    	ageTwo = new ArrayList<Card>();
    	ageThree = new ArrayList<Card>();
    	readInCards(new File("cards.txt"));
    }
    public Deck(ArrayList < Card > a1, ArrayList < Card > a2, ArrayList < Card > a3, ArrayList < Card > d)
    {
        ageOne = a1;
        ageTwo = a2;
        ageThree = a3;
        discard = d;
    } 
    public ArrayList < Card > getAgeOne()
    {
        return ageOne;
    }
    public ArrayList < Card > getAgeTwo()
    {
        return ageTwo;
    }
    public ArrayList < Card > getAgeThree()
    {
        return ageThree;
    }
    public ArrayList < Card > getDiscard()
    {
        return discard;
    }
    public void setAgeOne(ArrayList < Card > a1)
    {
        ageOne = a1;
    }
    public void setAgeTwo(ArrayList < Card > a2)
    {
        ageTwo = a2;
    }
    public void setAgeThree(ArrayList < Card > a3)
    {
        ageThree = a3;
    }
    public void setDiscard(ArrayList < Card > d)
    {
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
        if (age == 1)
        {
            p.addToHand(ageOne.remove(ageOne.size() - 1));
        }
        else if (age == 2)
        {
            p.addToHand(ageTwo.remove(ageTwo.size() - 1));
        }
        else if (age == 3)
        {
            p.addToHand(ageThree.remove(ageThree.size() - 1));
        }
    }
    public void shuffle(int age)
    {
        if (age == 1)
        {
            Random rand = new Random();
            for (int i = ageOne.size() - 1; i >= 1; i--)
            {
                int j = rand.nextInt(ageOne.size());
                Collections.swap(ageOne, i, j);
            }
        }
        else if (age == 2)
        {
            Random rand = new Random();
            for (int i = ageTwo.size() - 1; i >= 1; i--)
            {
                int j = rand.nextInt(ageTwo.size());
                Collections.swap(ageTwo, i, j);
            }
        }
        else if (age == 3)
        {
        	Random rand = new Random();
            for (int i = ageThree.size() - 1; i >= 1; i--)
            {
                int j = rand.nextInt(ageThree.size());
                Collections.swap(ageThree, i, j);
            }
        }
    }
    public void readInCards(File file) throws IOException
    {
        Scanner scan = new Scanner(file);
        scan.nextLine();
        scan.nextLine();
        while (scan.hasNextLine())
        {
            String input = scan.nextLine();
            if (!input.equals("DIVIDER TO CTRL-V"))
            {
	            String[] temp = input.split("|");
	            int age = Integer.parseInt(temp[3]);
	            Card card = new Card(temp[0], temp[1], temp[2], age, temp[4], temp[5], temp[6]);
	            if (age == 1) 
	            	getAgeOne().add(card);
	            else if (age == 2) 
	            	getAgeTwo().add(card);
	            else if (age == 3)
	            	getAgeThree().add(card);
            }
        }
        System.out.println(ageOne);
        scan.close();
    }
}