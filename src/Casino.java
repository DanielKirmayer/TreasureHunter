import java.text.ParseException;
import java.util.Scanner;

public class Casino {
    //Instance variables
    private int luckyDice1;
    private int luckyDice2;
    private int diceTotal;

    public void enter(Hunter hunter)
    {
        if (hunter.getGold() != 0)
        {
            // System.out.println("Luck chance: " + hunter.getLuckChance());
            System.out.println("You have "+hunter.getGold()+" gold, how much would you like to wager?");
            Scanner scanner1 = new Scanner(System.in);
            String wager = scanner1.nextLine();



            int playerChoice = 0;
            int wagerInt = 165723579;
            try{
                wagerInt = Integer.parseInt(String.valueOf(wager));
            }
            catch (NumberFormatException e){
                System.out.println("We don't allow you to bet garbage in this casino!");
            }
            if (wagerInt <= hunter.getGold() && wagerInt > 0){

            while(true) {
                System.out.println("Choose a number 2-12");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();



                try {
                    playerChoice = Integer.parseInt(choice);
                    break;
                } catch (NumberFormatException e){
                    System.out.println("Please choose a number.");
                    scanner.nextLine();
                }



            }


            luckyDice1 = (int) (Math.random() * 6) + 1;
            luckyDice2 = (int) (Math.random() * 6) + 1;
            diceTotal = luckyDice1 + luckyDice2;
            // diceTotal = 7;

            if (diceTotal == playerChoice){
                System.out.println("You got the right number! good job! You get double your money!");
                hunter.changeGold(wagerInt);
                hunter.changeLuckChance((wagerInt*2 / 10) * 2);
                // System.out.println("Luck chance: " + hunter.getLuckChance());
            } else if (Math.abs(diceTotal - playerChoice) <= 2) {
                System.out.println("The number was "+diceTotal+" you didn't get it, but you were within 2. Have your money back!");
            }
            else{
                System.out.println("Unlucky, the number was "+diceTotal+" you were too far off.");
                System.out.println("You lost "+wagerInt+" gold.");
                hunter.changeGold(-wagerInt);

                hunter.changeLuckChance(-(wagerInt / 10) * 2);
                // System.out.println("Luck chance: " + hunter.getLuckChance());
            }
            }
            else if (wagerInt != 165723579) {
                System.out.println("I know you don't have that much money to bet, get the hell out of my casino!");
            }


        }
        else
        {
            System.out.println("Come back once you've got some gold!");
        }

    }
}
