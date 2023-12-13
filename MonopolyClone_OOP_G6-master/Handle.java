import java.util.Scanner;

public interface Handle {
    Scanner scanner = new Scanner(System.in);

    static String handleStreetProperty(Player currentPlayer, StreetProperty streetProperty) {
        if (!streetProperty.isOwned()) {
            // The property is not owned, ask the player if they want to buy it
            return "Do you want to buy " + streetProperty.getName() + " for $" + streetProperty.getPrice() + "? (yes/no)";

        } else {
            // The property is owned, collect rent from the player
            streetProperty.collectRent(currentPlayer);
            return "This property is owned";
        }
    }

    static String BuyProperty (String BuyDecision, Player currentPlayer, StreetProperty streetProperty) {
        String buyDecision = scanner.nextLine().toLowerCase();
        if (buyDecision.equals("yes")) {
            // Player wants to buy the property
            if (currentPlayer.canAfford(streetProperty.getPrice())) {
                currentPlayer.pay(streetProperty.getPrice());
                streetProperty.setOwner(currentPlayer);
                return currentPlayer.getName() + " bought " + streetProperty.getName() + " for $" + streetProperty.getPrice();
            } else {
                return currentPlayer.getName() + " cannot afford " + streetProperty.getName();
                // Implement additional logic, e.g., bankrupt the player
            }
        }
        return currentPlayer.getName() + " decided to not buy";

    }

    static String handleUtilityProperty(Player currentPlayer, String utilityName, int utilityPrice) {
        UtilityProperty utilityProperty = new UtilityProperty(utilityName, utilityPrice);

        if (!utilityProperty.isOwned()) {
            // The utility is not owned, ask the player if they want to buy it
            return "Do you want to buy " + utilityName + " for $" + utilityPrice + "?";

        } /*else {
            // The utility is owned, calculate and collect rent from the player
            int diceRoll = currentPlayer.rollTwoDice();
            int numOwnedUtilities = currentPlayer.getNumOwnedUtilities(); // Implement this method in Player class
            int rent = utilityProperty.calculateRent(diceRoll, numOwnedUtilities);
            currentPlayer.pay(rent);
            utilityProperty.getOwner().receiveRent(rent);
            System.out.println(currentPlayer.getName() + " paid rent of $" + rent + " to " + utilityProperty.getOwner().getName());
        }*/
        return "This property is owned.";
    }

    static String BuyUtility (String buyUtility, Player currentPlayer, String utilityName, int utilityPrice){
        UtilityProperty utilityProperty = new UtilityProperty(utilityName, utilityPrice);
        String buyDecision = scanner.nextLine().toLowerCase();
        if (buyDecision.equals("yes")) {
            // Player wants to buy the utility
            if (currentPlayer.canAfford(utilityPrice)) {
                currentPlayer.pay(utilityPrice);
                utilityProperty.setOwner(currentPlayer);
                System.out.println(currentPlayer.getName() + " bought " + utilityName + " for $" + utilityPrice);
            } else {
                System.out.println(currentPlayer.getName() + " cannot afford " + utilityName);
                // Implement additional logic, e.g., bankrupt the player
            }
        } // WALA PATA ANANG rollTwoDice() & getNumOwnedUtilities();
        return currentPlayer.getName() + " decided to not buy";
    }

    static void handleRailroadProperty(Player currentPlayer, String railroadName, int railroadPrice) {
        RailroadProperty railroadProperty = new RailroadProperty(railroadName);

        if (!railroadProperty.isOwned()) {
            // The railroad is not owned, ask the player if they want to buy it
            System.out.println("Do you want to buy " + railroadName + " for $" + railroadPrice + "? (yes/no)");
            String buyDecision = scanner.nextLine().toLowerCase();

            if (buyDecision.equals("yes")) {
                // Player wants to buy the railroad
                if (currentPlayer.canAfford(railroadPrice)) {
                    currentPlayer.pay(railroadPrice);
                    railroadProperty.setOwner(currentPlayer);
                    System.out.println(currentPlayer.getName() + " bought " + railroadName + " for $" + railroadPrice);
                } else {
                    System.out.println(currentPlayer.getName() + " cannot afford " + railroadName);
                    // Implement additional logic, e.g., bankrupt the player
                }
            } // DAGHAN PA WALA NGA METHODS HERE
        } /*else {
            // The railroad is owned, collect rent from the player
            int rent = railroadProperty.calculateRent(currentPlayer.getNumOwnedRailroads()); // Implement this method in Player class
            currentPlayer.pay(rent);
            railroadProperty.getOwner().receiveRent(rent);
            System.out.println(currentPlayer.getName() + " paid rent of $" + rent + " to " + railroadProperty.getOwner().getName());
        }*/
    }
}
