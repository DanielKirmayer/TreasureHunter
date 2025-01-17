/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all of the things a Hunter can do in town.
 */
public class Town
{
    //instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private Casino casino;
    private String printMessage;
    private boolean toughTown;
    private boolean hasDied;
    private String treasure;
    private boolean huntedTreasure;


    public boolean isDead() {
        return hasDied;
    }
    //Constructor
    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     * @param s The town's shoppe.
     * @param t The surrounding terrain.
     */
    public Town(Shop shop, double toughness)
    {
        this.shop = shop;
        casino = new Casino();
        this.terrain = getNewTerrain();

        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        hunter = null;

        printMessage = "";

        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
    }

    public String getLatestNews()
    {
        return printMessage;
    }

    public Shop getShop() {return shop;}

    /**
     * Assigns an object to the Hunter in town.
     * @param h The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter)
    {
        this.hunter = hunter;
        treasure = this.hunter.generateTreasure();
        huntedTreasure = false;
        printMessage = "Welcome to town, " + hunter.getHunterName() + ".";

        if (toughTown)
        {
            printMessage += "\nIt's pretty rough around here, so watch yourself.";
        }
        else
        {
            printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
        }
    }

    /**
     * Handles the action of the Hunter leaving the town.
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown()
    {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);
        if (canLeaveTown)
        {
            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
            if (checkItemBreak())
            {
                hunter.removeItemFromInventory(item);
                printMessage += "\nUnfortunately, your " + item + " broke.";
            }

            return true;
        }

        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    public void enterShop(String choice)
    {
        shop.enter(hunter, choice);
    }

    public void enterCasino()
    {
        casino.enter(hunter);
    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble()
    {
        double noTroubleChance;
        if (toughTown)
        {
            noTroubleChance = 0.66;
        }
        else
        {
            noTroubleChance = 0.33;
        }

        if (Math.random() > noTroubleChance)
        {
            printMessage = "You couldn't find any trouble";
        }
        else
        {
            printMessage = "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n";
            int goldDiff = (int)(Math.random() * 10) + 1;
            double victoryChance = Math.random();
            if (shop.getMarkdown() == 1) {
                victoryChance += 0.25;
                if(shop.isGetsMegaDiscounts()){
                    victoryChance += 10;
                    goldDiff = 100;}
            }

            //Makes fight easier if the difficulty is easy (uses shop data markdown to do this).
            if (victoryChance > noTroubleChance)
            {
                printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                printMessage += "\nYou won the brawl and receive " +  goldDiff + " gold.";
                hunter.changeGold(goldDiff);
            }
            else
            {
                printMessage += "That'll teach you to go lookin' fer trouble in MY town! Now pay up!";
                // System.out.println(goldDiff);
                hunter.changeGold(-1 * goldDiff);
                if (hunter.getGold() <= 0){
                    hasDied = true;
                }
                else
                {
                    printMessage += "\nYou lost the brawl and pay " +  goldDiff + " gold.";
                }


            }
        }
    }

    /**
     * The hunter will be able to attempt to hunt for treasure
     *
     */
    public void huntForTreasure()
    {
        if (!huntedTreasure)
        {
            if (treasureHunt(huntedTreasure))
            {
                hunter.addTreasure(foundTreasure());
            }
            else
            {
                printMessage = "\nUnfortunately, you found nothing";
            }
        }
        else
        {
            printMessage = "You have already tried to hunt for treasure.";
        }


        // tell hunter he already tried hunting
    }

    // to display if already hunted for treasure, check huntedTreasure before
    // calling this method

    /**
     * Hunter has a 1 in 10 chance of succeeding
     *
     * @param huntedTreasure
     * @return true for found treasure
     */
    public boolean treasureHunt(boolean huntedTreasure)
    {
        if (!huntedTreasure)
        {
            this.huntedTreasure = true;
//            System.out.println("Base chance: " + 1.0/100);
//            System.out.println("Luck chance: " + hunter.getLuckChance());
//            System.out.println("Total: " + (1.0 + hunter.getLuckChance()) / 100);
            if ((Math.random() * 1) <= (1.0 + hunter.getLuckChance()) / 100 )
            {
                return true;
            }
            return false;

        }
        return false;
    }

    // only call if treasure found is successful
    public String foundTreasure()
    {
        return treasure;
    }

    public String toString()
    {
        return "This nice little town is surrounded by " + terrain.getTerrainName() + ".";
    }

    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    private Terrain getNewTerrain()
    {
        double rnd = Math.random();
        if (rnd < .2)
        {
            return new Terrain("Mountains", "Rope");
        }
        else if (rnd < .4)
        {
            return new Terrain("Ocean", "Boat");
        }
        else if (rnd < .6)
        {
            return new Terrain("Plains", "Horse");
        }
        else if (rnd < .8)
        {
            return new Terrain("Desert", "Water");
        }
        else
        {
            return new Terrain("Jungle", "Machete");
        }
    }

    /**
     * Determines whether or not a used item has broken.
     * @return true if the item broke.
     */
    private boolean checkItemBreak()
    {
        double rand = Math.random();
        return (rand < 0.5);
    }
}