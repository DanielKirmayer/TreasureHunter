/**
 * The Shop class controls the cost of the items in the Treasure Hunt game.<p>
 * The Shop class also acts as a go between for the Hunter's buyItem() method.<p>
 */
import java.util.Scanner;

public class Shop
{
    // Shop prices
    private int waterCost;
    private int ropeCost;
    private int macheteCost;
    private int horseCost;
    private int boatCost;

    private boolean getsMegaDiscounts = false;

    // instance variables
    private double markdown;
    private Hunter customer;

    private String shopMsg = "";

    //Constructor
    public Shop(double markdown)
    {
        this.markdown = markdown;
        // adjusts prices based on the markdown.
        if (markdown == 1) {
            waterCost = 1;
            ropeCost = 2;
            macheteCost = 3;
            horseCost = 6;
            boatCost = 10;
        }
        else if (markdown == 0.75){
            waterCost = 2;
            ropeCost = 3;
            macheteCost = 4;
            horseCost = 9;
            boatCost = 15;
        } else if (markdown == 1.01) {
            waterCost = 1;
            ropeCost = 1;
            macheteCost = 1;
            horseCost = 1;
            boatCost = 1;
            getsMegaDiscounts = true;
            this.markdown = 1;
        }
        customer = null;
    }
    public double getMarkdown() {
        return markdown;
    }
    public boolean isGetsMegaDiscounts(){
        return getsMegaDiscounts;
    }


    public String getShopMsg()
    {
        return shopMsg;
    }

    /** method for entering the shop
     * @param hunter  the Hunter entering the shop
     * @param buyOrSell  String that determines if hunter is "B"uying or "S"elling
     */
    public void enter(Hunter hunter, String buyOrSell)
    {
        customer = hunter;


        Scanner scanner = new Scanner(System.in);
        if (buyOrSell.equals("B") || buyOrSell.equals("b"))
        {
//            System.out.println("Welcome to the shop! We have the finest wares in town.");
//            System.out.println("Currently we have the following items:");
//            System.out.println(inventory());
//            System.out.print("What're you lookin' to buy? ");

            shopMsg = "Welcome to the shop! We have the finest wares in town.";
            shopMsg += "\nCurrently we have the following items:";
            shopMsg += "\n" + inventory();
            shopMsg += "\n What're you lookin' to buy? ";
//            String item = scanner.nextLine();
//            int cost = checkMarketPrice(item, true);
//            if (cost == 0)
//            {
//                System.out.println("We ain't got none of those.");
//            }
//            else
//            {
//                System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
//                String option = scanner.nextLine();
//
//                if (option.equals("y") || option.equals("Y"))
//                {
//                    buyItem(item);
//                }
//            }
        }
        else if (hunter.getInventorySize() != 0)
        {
            shopMsg = "What're you lookin' to sell? ";
            shopMsg += "\n You currently have the following items: " + customer.getInventory();
//            String item = scanner.nextLine();
//            int cost = checkMarketPrice(item, false);
//            if (cost == 0)
//            {
//                System.out.println("We don't want none of those.");
//            }
//            else
//            {
//                System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
//                String option = scanner.nextLine();
//
//                if (option.equals("y") || option.equals("Y"))
//                {
//                    sellItem(item);
//                }
//            }
        }
        else
        {
            shopMsg = "You ain't got nuthin, get out!";
        }
    }

    /** A method that returns a string showing the items available in the shop (all shops sell the same items)
     *
     * @return  the string representing the shop's items available for purchase and their prices
     */
    public String inventory()
    {
        String str = "Water: " + waterCost + " gold\n";
        str += "Rope: " + ropeCost + " gold\n";
        str += "Machete: " + macheteCost + " gold\n";
        str += "Horse: " + horseCost + " gold\n";
        str += "Boat: " + boatCost + " gold\n";

        return str;
    }

    /**
     * A method that lets the customer (a Hunter) buy an item.
     * @param item The item being bought.
     */
    public void buyItem(String item)
    {
        int costOfItem = checkMarketPrice(item, true);
        if (customer.buyItem(item, costOfItem))
        {
            System.out.println("Ye' got yerself a " + item + ". Come again soon.");
        }
        else
        {
            System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
        }
    }

    /**
     * A pathway method that lets the Hunter sell an item.
     * @param item The item being sold.
     */
    public void sellItem(String item)
    {
        int buyBackPrice = checkMarketPrice(item, false);
        if (customer.sellItem(item, buyBackPrice))
        {
            System.out.println("Pleasure doin' business with you.");
        }
        else
        {
            System.out.println("Stop stringin' me along!");
        }
    }

    /**
     * Determines and returns the cost of buying or selling an item.
     * @param item The item in question.
     * @param isBuying Whether the item is being bought or sold.
     * @return The cost of buying or selling the item based on the isBuying parameter.
     */
    public int checkMarketPrice(String item, boolean isBuying)
    {
        if (isBuying)
        {
            return getCostOfItem(item);
        }
        else
        {
            return getBuyBackCost(item);
        }
    }

    /**
     * Checks the item entered against the costs listed in the variables.
     *
     * @param item The item being checked for cost.
     * @return The cost of the item or 0 if the item is not found.
     */
    public int getCostOfItem(String item)
    {
        if (item.equalsIgnoreCase("Water"))
        {
            return waterCost;
        }
        else if (item.equalsIgnoreCase("Rope"))
        {
            return ropeCost;
        }
        else if (item.equalsIgnoreCase("Machete"))
        {
            return macheteCost;
        }
        else if (item.equalsIgnoreCase("Horse"))
        {
            return horseCost;
        }
        else if (item.equalsIgnoreCase("Boat"))
        {
            return boatCost;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Checks the cost of an item and applies the markdown.
     *
     * @param item The item being sold.
     * @return The sell price of the item.
     */
    public int getBuyBackCost(String item)
    {
        int cost = (int)(getCostOfItem(item) * markdown);
        return cost;
    }
}