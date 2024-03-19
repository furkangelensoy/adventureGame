import java.util.Scanner;

public class Game {
    private Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome to Adventure Game!");
        System.out.print("Please enter a name: ");
        String playerName = input.nextLine();
        Player player = new Player(playerName);
        System.out.println(player.getName() + " Welcome!");
        System.out.println("Please select a character.");
        player.selectChar();
        Cave cave = new Cave(player);
        River river = new River(player);
        Forest forest = new Forest(player);
        Mine mine = new Mine(player);

        Location location = null;
        boolean isWin = false;
        while (!isWin) {
            player.printPlayerStats();
            System.out.println("-------------------------------------");
            player.getInventory().printAwards();
            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("################## Locations ##################");
            System.out.println();
            System.out.print("1- Safe House ---> This location is safe for you, there is no enemy here.\n"
                    + "2- Store ---> You can buy weapon or armor here\n"
                    + "3- Cave ---> Award: <Food> , Be careful! Zombies live here!\n"
                    + "4- Forest ---> Award: <Firewood> , Be careful! Vampires live here!\n"
                    + "5- River ---> Award: <Water> , Be careful! Bears live here!\n"
                    + "6- Mine ---> Award: <Chance of winning items or money> , Be careful! Snakes live here!\n"
                    + "0- Give up ---> Close the game.\n"
                    + "Please select the location you want to go: ");
            int selectLocation = input.nextInt();
            switch (selectLocation) {
                case 0:
                    location = null;
                    break;
                case 1:
                    if (player.getInventory().checkAwards()) {
                        System.out.println("=============================================");
                        System.out.println("Congratulations, you won the game!");
                        System.out.println("=============================================");
                        isWin = true;
                    }
                    location = new SafeHouse(player);
                    break;
                case 2:
                    location = new ToolStore(player);
                    break;
                case 3:
                    location = cave;
                    break;
                case 4:
                    location = forest;
                    break;
                case 5:
                    location = river;
                    break;
                case 6:
                    location = mine;
                    break;
                default:
                    System.out.println("Please enter a valid region!");
                    location = new SafeHouse(player);
            }

            if (location == null) {
                System.out.println("You gave up quickly, see you soon!");
                break;
            }

            if (!location.onLocation()) {
                System.out.println("Game Over!");
                break;
            }
        }

    }
}
