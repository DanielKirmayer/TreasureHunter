import java.util.HashSet;

/**
 * Hunter Class<br /><br />
 * This class represents the treasure hunter character (the player) in the Treasure Hunt game.
 */
public class Hunter
{

    //Treasures
    private String[] treasures = {"Ruby", "Emerald", "Sapphire"};
    private HashSet<String> treasuresOwned = new HashSet<String>();

    //Inventory
    private HashSet<String> inventory = new HashSet<String>();

    //instance variables
    private String hunterName;
    private int gold;
    private int luckChance;

    //Constructor
    /**
     * The base constructor of a Hunter assigns the name to the hunter and an empty kit.
     *
     * @param hunterName The hunter's name.
     */
    public Hunter(String hunterName, int startingGold)
    {
        this.hunterName = hunterName;
        gold = startingGold;
        luckChance = 0;
    }

    //Accessors
    public String getHunterName()
    {
        return hunterName;
    }

    public int getGold()
    {
        return gold;
    }

    public int getLuckChance()
    {
        return luckChance;
    }

    public void changeLuckChance(int change)
    {
        luckChance += change;
    }

    public void changeGold(int modifier)
    {
        gold += modifier;
        if (gold < 0)
        {
            gold = 0;
        }
    }

    /**
     * Generates a treasure out of the treasure pool
     * @return String representation of treasure
     */
    public String generateTreasure()
    {
        return treasures[(int) (Math.random() * 3)];
    }

    /**
     * Adds treasure to treasure hunter's pool.
     * If hunter already has the treasure, tells them that they already do
     * Else, add the treasure to the HashSet
     *
     * @param treasure
     */
    public void addTreasure(String treasure)
    {
        int size = treasuresOwned.size();
        treasuresOwned.add(treasure);
        if (size == treasuresOwned.size())
        {
            System.out.println("Unfortunately, you already have that treasure.");
        }
        else
        {
            System.out.println("You have found the legendary " + treasure + "!");
        }
    }

    /**
     * Checks if the hunter has all treasures
     *
     * @return
     */
    public boolean hasAllTreasure()
    {
        return treasuresOwned.size() == 3;
    }

    /**
     * Buys an item from a shop.
     *
     * @param item The item the hunter is buying.
     * @param costOfItem  the cost of the item
     *
     * @return true if the item is successfully bought.
     */
    public boolean buyItem(String item, int costOfItem)
    {
        if (costOfItem == 0 || gold < costOfItem || hasItemInInventory(item))
        {
            return false;
        }

        gold -= costOfItem;
        addItem(item);
        return true;
    }

    /**
     * The Hunter is selling an item to a shop for gold.<p>
     * This method checks to make sure that the seller has the item and that the seller is getting more than 0 gold.
     *
     * @param item The item being sold.
     * @param buyBackPrice  the amount of gold earned from selling the item
     * @return true if the item was successfully sold.
     */
    public boolean sellItem(String item, int buyBackPrice)
    {
        if (buyBackPrice <= 0 || !hasItemInInventory(item))
        {
            return false;
        }

        gold += buyBackPrice;
        removeItemFromInventory(item);
        return true;
    }


    /**
     * Removes item from the inventory
     *
     * @param item item to be removed
     */
    public void removeItemFromInventory(String item)
    {
        inventory.remove(item.toLowerCase());
    }

    /**
     * Adds item to the inventory.
     * Because the inventory is a HashSet and all items are
     * converted to lowercase beforehand, no duplicates can exist
     *
     * @param item to be added
     */
    public void addItem(String item)
    {
        inventory.add(item.toLowerCase());
    }


    /**
     * Searches inventory for a specified item
     *
     * @param item The search item
     * @return true if item is found
     */
    public boolean hasItemInInventory(String item)
    {
        return inventory.contains(item.toLowerCase());
    }

    /** Returns a printable representation of the inventory, which
     *  is a list of item in the inventory. If the Hunter has more than one
     *  item, a comma is added to separate each item
     *
     * @return  The printable String representation of the inventory
     */
    public String getInventory()
    {

        String inventory = "";
        for (String item : this.inventory)
        {
            if (this.inventory.size() == 1)
            {
                return " " + item;
            }
            inventory += " " + item + ",";
        }
        inventory = inventory.substring(0, inventory.length() - 1);
        return inventory;
    }

    /**
     * @return A string representation of the hunter.
     */
    public String toString()
    {
        String str = hunterName + " has " + gold + " gold";
        if (inventory.size() != 0)
        {
            str += " and" + getInventory();
        }
        return str;
    }
}
