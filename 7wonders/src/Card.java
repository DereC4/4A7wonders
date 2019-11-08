import java.util.ArrayList;

public class Card implements Comparable
{
    private int age;
    private String name;
    private ArrayList < Resources > cost; //Resource cost
    private String chain;
    private boolean isFree;
    private String type; //also color
    private String effect;
    //public static final String[] cards; //Will read in text file
    public Card()
    {
        this("", "", "", 0, "", "");
    }
    public Card(String name, String color, String effect, int age, String chain, String cost)
    {
        this.age = age;
        this.name = name;
        this.type = color;
        this.chain = chain;
        this.effect = effect;
        this.cost = new ArrayList < Resources > ();
        String[] temp = cost.split(",");
        for (int i = 0; i < temp.length; i++) this.cost.add(new Resources(temp[i]));
    }
    public int compareTo(Object obj)
    {
        Card card = (Card) obj;
        if (getType().compareTo(card.getType()) > 0) return 1;
        else if (getType().compareTo(card.getType()) < 0) return -1;
        else if (getName().compareTo(card.getName()) > 0) return 1;
        else if (getName().compareTo(card.getName()) < 0) return -1;
        return 0;
    }
    public String toString()
    {
        return getName() + "," + getEffect();
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public ArrayList < Resources > getCost()
    {
        return cost;
    }
    public void setCost(ArrayList < Resources > cost)
    {
        this.cost = cost;
    }
    public String getChain()
    {
        return chain;
    }
    public void setChain(String chain)
    {
        this.chain = chain;
    }
    public boolean isFree()
    {
        return isFree;
    }
    public void setFree(boolean isFree)
    {
        this.isFree = isFree;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getEffect()
    {
        return effect;
    }
    public void setEffect(String effect)
    {
        this.effect = effect;
    }
}