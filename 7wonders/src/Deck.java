import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Deck
{
    private ArrayList < Card > ageOne;
    private ArrayList < Card > ageTwo;
    private ArrayList < Card > ageThree;
    private ArrayList < Card > discard;
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
            Random random = new Random();
            // start from end of the list
            for (int i = ageOne.size() - 1; i >= 1; i--)
            {
                // get a random index j such that 0 <= j <= i
                int j = random.nextInt(i + 1);
                // swap element at i'th position in the list with element at
                // randomly generated index j
                Card obj = ageOne.get(i);
                ageOne.set(i, ageOne.get(j));
                ageOne.set(j, obj);
            }
        }
        else if (age == 2)
        {
            Random random = new Random();
            // start from end of the list ageTwo
            for (int i = ageTwo.size() - 1; i >= 1; i--)
            {
                // get a random index j such that 0 <= j <= i
                int j = random.nextInt(i + 1);
                // swap element at i'th position in the list with element at
                // randomly generated index j
                Card obj = ageTwo.get(i);
                ageTwo.set(i, ageTwo.get(j));
                ageTwo.set(j, obj);
            }
        }
        else if (age == 3)
        {
            Random random = new Random();
            for (int i = ageThree.size() - 1; i >= 1; i--)
            {
                // get a random index j such that 0 <= j <= i ageThree
                int j = random.nextInt(i + 1);
                // swap element at i'th position in the list with element at
                // randomly generated index j
                Card obj = ageThree.get(i);
                ageThree.set(i, ageThree.get(j));
                ageThree.set(j, obj);
            }
        }
    }
    public void readInCards(File file) throws IOException
    {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
        {
            String input = sc.nextLine();
            String[] temp = input.split("|");
            int age = Integer.parseInt(temp[3]);
            Card card = new Card(temp[0], temp[1], temp[2], age, temp[4], temp[5], temp[6]);
            if (age == 1) getAgeOne().add(card);
            else if (age == 2) getAgeTwo().add(card);
            else if (age == 3) getAgeThree().add(card);
        }
    }
}