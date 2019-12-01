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
    	discard = new ArrayList<Card>();
    	readInCards(new File("cards.txt"));
    	shuffle(1);
    	shuffle(2);
    	shuffle(3);
//    	printall();
    }
    public Deck(ArrayList < Card > a1, ArrayList < Card > a2, ArrayList < Card > a3, ArrayList < Card > d)
    {
        ageOne = a1;
        ageTwo = a2;
        ageThree = a3;
        discard = d;
    } 
    /** Debug tool */
    public void printall()
    {
    	System.out.println(ageOne);
    	System.out.println(ageTwo);
    	System.out.println(ageThree);
    }
    public ArrayList < Card > getAgeOne()
    {
//    	System.out.println(ageOne);
        return ageOne;
    }
    public ArrayList < Card > getAgeTwo()
    {
//    	System.out.println(ageTwo);
        return ageTwo;
    }
    public ArrayList < Card > getAgeThree()
    {
//    	System.out.println(ageThree);
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
    //Fisher-Yates Shuffle
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
	            String[] temp = input.split("\\|");
//	            for(String s:temp)
//	            	System.out.print(s);
//	            System.out.println("\n"+temp[3]);
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
//      System.out.println(ageOne);
        scan.close();
    }
}