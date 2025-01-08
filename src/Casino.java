import java.util.Scanner;

public class Casino {
    //Instance variables
    private int luckyDice;

    public void enter(Hunter hunter)
    {
        if (hunter.getGold() != 0)
        {
            Scanner scanner = new Scanner(System.in);


            luckyDice = (int) (Math.random() * 11) + 2;
        }
        else
        {
            System.out.println("Come back once you've got some gold!");
        }

    }
}
