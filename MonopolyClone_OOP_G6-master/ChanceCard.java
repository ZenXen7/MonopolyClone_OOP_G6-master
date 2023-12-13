import java.util.Random;

public class ChanceCard extends Card {
    public ChanceCard(String description) {
        super(description);
    }
    public void executeAction(Player player) {

        Random random = new Random();
        int actionType = random.nextInt(10);

        switch (actionType) {
            case 0:
                collectMoney(player);
                break;
            case 1:
                movePlayer(player);
                break;
            case 2:
                payRent(player);
                break;
            case 3:
                goToJail(player);
                break;
            case 4:
                getOutOfJailFreeCard(player);
                break;
            case 5:
                repairProperties(player);
                break;
            // Add more cases for other actions
        }
    }

    private void collectMoney(Player player) {
        int amount = generateRandomAmount();
        player.receiveMoney(amount);
        System.out.println(player.getName() + " collected $" + amount + " from Chance.");
    }

    private void movePlayer(Player player) {
        int steps = generateRandomSteps();
        player.move(steps);
        System.out.println(player.getName() + " moved " + steps + " steps from Chance.");
    }

    private void payRent(Player player){
        int amount = generateRandomAmount();
        player.payRent(amount);
        System.out.println(player.getName() + " paid $" + amount + " in rent from Chance.");
    }

    private void goToJail(Player player){
        player.setInJail(player.isInJail());
        System.out.println(player.getName() + " goes to jail from Chance");
        player.sendToJail(player);
    }
    private void getOutOfJailFreeCard(Player player){
        player.receiveGetOutOfJailFreeCard();
        System.out.println(player.getName() + " received a 'Get Out of Jail Free' card from Chance.");
    }

    private int generateRandomAmount() {
        Random random = new Random();
        return random.nextInt(151) + 50;
    }

    private int generateRandomSteps() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    private void repairProperties(Player player){
       // int houseCount = player.gett;
    }

    public static class LuxuryTax {
        private String name;
        private int taxAmount;

        public LuxuryTax(String name, int taxAmount) {
            this.name = name;
            this.taxAmount = taxAmount;
        }

        public void collectLuxuryTax(Player player) {
            player.pay(taxAmount);
            System.out.println(player.getName() + " paid $" + taxAmount + " for luxury tax at " + name);
        }
    }
}