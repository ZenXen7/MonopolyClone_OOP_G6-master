import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MonopolyGame extends JFrame {
    private JPanel mainPanel;
    private JLabel backgroundImageLabel;
    private JTextField displayText;
    private JTextField displayProp;
    private JButton btnBuy;
    private JButton btnPayRent;
    private JButton btnRoll;
    private JButton btnSellProperty;
    private JButton btnP1;
    private JButton btnP2;
    private Dice dice;

    private Jail jail;

    private FreeParking freeParking;

    private int currentPlayerIndex;  // Declare as a class field
    private Player player1;  // Declare as a class field
    private Player player2;  // Declare as a class field
    private Player currentPlayer;

    private StreetProperty mediterraneanAve;
    private StreetProperty balticAve;
    private StreetProperty orientalAve;
    private StreetProperty vermontAve;
    private StreetProperty connecticutAve;
    private StreetProperty STcharlesPlace;
    private StreetProperty statesAve;
    private StreetProperty virginiaAve;
    private StreetProperty STjamesPlace;
    private StreetProperty tennesseeAve;
    private StreetProperty newyorkAve;
    private StreetProperty kentuckyAve;
    private StreetProperty indianaAve;
    private StreetProperty illinoisAve;
    private StreetProperty atlanticAve;
    private StreetProperty ventnorAve;
    private StreetProperty marvinGardens;
    private StreetProperty pacificAve;
    private StreetProperty northcarolinaAve;
    private StreetProperty pennsylvaniaAve;
    private StreetProperty parkPlace;
    private StreetProperty boardWalk;
    private boolean gameIsRunning;


    public void updateDisplayText(String text) {
        displayText.setText(text);
    }

    public static void main(String[] args) {
        MonopolyGame frame = new MonopolyGame();
        // Set up the main frame
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(frame.mainPanel);
        frame.setSize(1280, 900);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Initialize game variables and start the game
        new Thread(() -> frame.startGame()).start();
    }

    public MonopolyGame() {
        // Load the background image
        ImageIcon backgroundImageIcon = new ImageIcon("monopoly_original.jpg");
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1280, 900, Image.SCALE_DEFAULT);
        backgroundImageIcon = new ImageIcon(backgroundImage);


        gameIsRunning = true;
        // Set the preferred size of the main panel
        mainPanel.setPreferredSize(new Dimension(1280, 900));

        // Set the preferred size of the displayText field
        displayText.setPreferredSize(new Dimension(100, 150));
        displayProp.setPreferredSize(new Dimension(100, 300));

        dice = new Dice();

        btnRoll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameIsRunning) {
                    btnRoll.setEnabled(false);
                    new Thread(() -> {
                        int diceRoll = dice.roll();
                        updateDisplayText("Dice rolled: " + diceRoll);
                        handleDiceRoll(diceRoll);
                        btnRoll.setEnabled(true);
                    }).start();
                }
            }
        });

        btnP1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameIsRunning) {
                    showPlayerProperties(player1);
                }
            }
        });

        btnP2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameIsRunning && !mediterraneanAve.isOwned()) {
                    switchToNextPlayer();
                }
            }
        });


    }

    private void showPlayerProperties(Player player) {
        StringBuilder message = new StringBuilder();
        message.append("Properties owned by ").append(player.getName()).append(":\n");

        for (Property property : player.getOwnedProperties()) {
            message.append(property.getName()).append("\n");
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Player Properties", JOptionPane.INFORMATION_MESSAGE);
    }

    private void switchToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        currentPlayer = (currentPlayerIndex == 0) ? player1 : player2;
        displayText.setText("Next turn: " + currentPlayer.getName());
    }

    private void handleBuyButtonClick(Player currentPlayer, Property currentProperty) {
        // Implement the logic for handling the buy button click
        // For example:
        // Call the onPurchase method to handle the purchase logic
        // Add your specific logic for buying the property here
        if (currentPlayer.canAfford(currentProperty.getPrice())) {
            currentPlayer.pay(currentProperty.getPrice());
            currentProperty.setOwner(currentPlayer);
            displayProp.setText(currentPlayer.getName() + " bought " + currentProperty.getName() + " for $" + currentProperty.getPrice());
            currentPlayer.pay(currentProperty.getPrice());
            currentPlayer.addProperty(currentProperty);

        } else {
            displayProp.setText(currentPlayer.getName() + " cannot afford " + currentProperty.getName());
            // Implement additional logic, e.g., bankrupt the player
        }
    }


    private void handleDiceRoll(int diceRoll) {
        currentPlayer = player1;
        currentPlayer.move(diceRoll);

        if (currentPlayer.getPosition() % 40 == 1) {
            int rotations = currentPlayer.getPosition() / 40;
            int salary = 200 * rotations;
            currentPlayer.addToMoney(salary);
            updateDisplayText(currentPlayer.getName() + " passed Go and collected $" + salary);
        }

        switch (currentPlayer.getPosition() % 40) {
//                case 1: // Go, Collect Salary
//                    // Already handled when passing Go
//                    break;
            case 2: // Mediterranean Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, mediterraneanAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !mediterraneanAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, mediterraneanAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    displayProp.setText("Exception occurred on Mediterranean Avenue: " + e.getMessage());
                }
                break;
            case 3: // Community Chest
                CommunityChestCard communityChestCard = new CommunityChestCard("Community Chest Card Description"); // add desc here
                communityChestCard.executeAction(currentPlayer);
                break;
            case 4: // Baltic Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, balticAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !balticAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, balticAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Baltic Avenue: " + e.getMessage());
                }
                break;
            case 5: // Income Tax
                IncomeTax incomeTax = new IncomeTax("Income Tax", 200);
                incomeTax.collectIncomeTax(currentPlayer);
                break;
            case 6: // Reading Railroad
                Handle.handleRailroadProperty(currentPlayer, "Reading Railroad", 200);
                break;
            case 7: // Oriental Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, orientalAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !orientalAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, orientalAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Oriental Avenue: " + e.getMessage());
                }
                break;
            case 8: // Chance
                ChanceCard chanceCard = new ChanceCard("Chance Card Description"); // Add pata desc here
                chanceCard.executeAction(currentPlayer);
                break;
            case 9: // Vermont Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, vermontAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !vermontAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, vermontAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Vermont Avenue: " + e.getMessage());
                }
                break;
            case 10: // Connecticut Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, connecticutAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !connecticutAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, connecticutAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Connecticut Avenue: " + e.getMessage());
                }
                break;
            case 11: // Just Visiting/In Jail
                if (!jail.isPlayerInJail(currentPlayer)) {
                    displayProp.setText(currentPlayer.getName() + " is just visiting Jail.");
                } else {
                    displayProp.setText(currentPlayer.getName() + " is in Jail. Pay $50 to get out or roll doubles on your next turn.");
                    // Implement additional logic for handling Jail, e.g., paying to get out or rolling doubles
                }
                break;
            case 12: // St. Charles Place
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, STcharlesPlace));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !STcharlesPlace.isOwned()) {
                                handleBuyButtonClick(currentPlayer, STcharlesPlace);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on St Charles Place: " + e.getMessage());
                }
                break;
            case 13: // Electric Company
                try {
                    displayProp.setText(Handle.handleUtilityProperty(currentPlayer, "Electric Company", 150));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayProp.setText(Handle.BuyUtility("yes", currentPlayer, "Electric Company", 150));
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Mediterranean Avenue: " + e.getMessage());
                }
                break;
            case 14: // States Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, statesAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !statesAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, statesAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on StatesAve Avenue: " + e.getMessage());
                }
                break;
            case 15: // Virginia Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, virginiaAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !virginiaAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, virginiaAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Virginia Avenue: " + e.getMessage());
                }
                break;
            case 16: // Pennsylvania Railroad
                Handle.handleRailroadProperty(currentPlayer, "Pennsylvania Railroad", 200);
                break;
            case 17: // St. James Place
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, STjamesPlace));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !STjamesPlace.isOwned()) {
                                handleBuyButtonClick(currentPlayer, STjamesPlace);
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on St. James Place: " + e.getMessage());
                }
                break;
            case 18: // Community Chest
                CommunityChestCard communityChestCard2 = new CommunityChestCard("Community Chest Card Description"); // Add pata desc here
                communityChestCard2.executeAction(currentPlayer);
                break;
            case 19: // Tennessee Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, tennesseeAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !tennesseeAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, tennesseeAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Tennessee Avenue: " + e.getMessage());
                }
                break;
            case 20: // New York Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, newyorkAve));
                    switchToNextPlayer();
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !newyorkAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, newyorkAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on New York Avenue : " + e.getMessage());
                }
                break;
            case 21: // Free Parking
                freeParking.handleFreeParking(currentPlayer);
                break;
            case 22: // Kentucky Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, kentuckyAve));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !kentuckyAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, kentuckyAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Kentucky Avenue : " + e.getMessage());
                }
                break;
            case 23: // Chance
                ChanceCard chanceCard2 = new ChanceCard("Chance Card Description"); // Add pata desc here
                chanceCard2.executeAction(currentPlayer);
                break;
            case 24: // Indiana Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, indianaAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !indianaAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, indianaAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Indiana Avenue : " + e.getMessage());
                }
                break;
            case 25: // Illinois Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, illinoisAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !illinoisAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, illinoisAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Illinois Avenue : " + e.getMessage());
                }
                break;
            case 26: // B. & O. Railroad
                Handle.handleRailroadProperty(currentPlayer, "B. & O. Railroad", 200);
                break;
            case 27: // Atlantic Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, atlanticAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !atlanticAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, atlanticAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Atlantic Avenue : " + e.getMessage());
                }
                break;
            case 28: // Ventnor Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, ventnorAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !ventnorAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, ventnorAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Ventnor Avenue : " + e.getMessage());
                }
                break;
            case 29: // Water Works
                try {
                    displayProp.setText(Handle.handleUtilityProperty(currentPlayer, "Water Works", 200));

                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayProp.setText(Handle.BuyUtility("yes", currentPlayer, "Water Works", 200));
                            switchToNextPlayer();
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Water Works: " + e.getMessage());
                }
                break;
            case 30: // Marvin Gardens
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, marvinGardens));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !marvinGardens.isOwned()) {
                                handleBuyButtonClick(currentPlayer, marvinGardens);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Marvin Gardens: " + e.getMessage());
                }
                break;
            case 31: // Go to Jail
                jail.sendToJail(currentPlayer);
                break;
            case 32: // Pacific Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, pacificAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !pacificAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, pacificAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Pacific Avenue : " + e.getMessage());
                }
                break;
            case 33: // North Carolina Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, northcarolinaAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !northcarolinaAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, northcarolinaAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on North Carolina Avenue : " + e.getMessage());
                }
                break;
            case 34: // Community Chest
                CommunityChestCard communityChestCard3 = new CommunityChestCard("Community Chest Card Description"); // Add pata desc here
                communityChestCard3.executeAction(currentPlayer);
                break;
            case 35: // Pennsylvania Avenue
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, pennsylvaniaAve));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !pennsylvaniaAve.isOwned()) {
                                handleBuyButtonClick(currentPlayer, pennsylvaniaAve);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Indiana Avenue : " + e.getMessage());
                }
                break;
            case 36: // Short Line
                Handle.handleRailroadProperty(currentPlayer, "Short Line", 200);
                break;
            case 37: // Chance
                ChanceCard chanceCard3 = new ChanceCard("Chance Card Description"); // Add pata desc here
                chanceCard3.executeAction(currentPlayer);
                break;
            case 38: // Park Place
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, parkPlace));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !parkPlace.isOwned()) {
                                handleBuyButtonClick(currentPlayer, parkPlace);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on Park Avenue : " + e.getMessage());
                }
                break;
            case 39: // Luxury Tax
                ChanceCard.LuxuryTax luxuryTax = new ChanceCard.LuxuryTax("Luxury Tax", 100);
                luxuryTax.collectLuxuryTax(currentPlayer);
                break;
            case 40: // Boardwalk //
                try {
                    displayProp.setText(Handle.handleStreetProperty(currentPlayer, boardWalk));
                    btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameIsRunning && !boardWalk.isOwned()) {
                                handleBuyButtonClick(currentPlayer, boardWalk);
                                switchToNextPlayer();
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Exception occurred on BoardWalk Avenue : " + e.getMessage());
                }
                break;
            // CASE 40 IS UNREACHABLE == ILISAN UG "case 0:" NEED FIX!
//                currentPlayer.getPosition() % 40 range from 0 to 39, not including 40. Therefore,
//                    the case 40: is unreachable because the maximum value it can take is 39.
//                To fix this issue, you can remove the case 40: and handle the case when
//                        currentPlayer.getPosition() % 40 is 0 separately.
            default:
                // throw new IllegalStateException("Unexpected value: " + currentPlayer.getPosition() % 40);
                System.err.println("Unexpected value: " + currentPlayer.getPosition() % 40);
                break;
        }

        if (currentPlayer.getMoney() <= 0) {
            updateDisplayText(currentPlayer.getName() + " is bankrupt. Game over!");
            gameIsRunning = false;
        }



        if (!gameIsRunning) {
            // Display a dialog to end the game
            JOptionPane.showMessageDialog(this, "Game over!");
        }
    }


    private void startGame() {
        // Create players and board spaces
        String playerName1 = JOptionPane.showInputDialog("Enter Player 1's Name:");
        String playerName2 = JOptionPane.showInputDialog("Enter Player 2's Name:");


        player1 = new Player(playerName1);
        player2 = new Player(playerName2);

        mediterraneanAve = new StreetProperty("Mediterranean Avenue", 60, "Brown");
        balticAve = new StreetProperty("Baltic Avenue", 60, "Brown");
        orientalAve = new StreetProperty("Oriental Avenue", 100, "LightBlue");
        vermontAve = new StreetProperty("Vermont Avenue", 100, "LightBlue");
        connecticutAve = new StreetProperty("Connecticut Avenue", 100, "LightBlue");
        STcharlesPlace = new StreetProperty("St. Charles Place", 140, "Pink");
        statesAve = new StreetProperty("States Avenue", 140, "Pink");
        virginiaAve = new StreetProperty("Virginia Avenue", 160, "Pink");
        STjamesPlace = new StreetProperty("St. James Place", 140, "Orange");
        tennesseeAve = new StreetProperty("Tennessee Avenue", 180, "Orange");
        newyorkAve = new StreetProperty("New York Avenue", 200, "Orange");
        kentuckyAve = new StreetProperty("Kentucky Avenue", 220, "Red");
        indianaAve = new StreetProperty("Indiana Avenue", 220, "Red");
        illinoisAve = new StreetProperty("Illinois Avenue", 240, "Red");
        atlanticAve = new StreetProperty("Atlantic Avenue", 260, "Yellow");
        ventnorAve = new StreetProperty("Ventnor Avenue", 260, "Yellow");
        marvinGardens = new StreetProperty("Marvin Gardens", 280, "Yellow");
        pacificAve = new StreetProperty("Pacific Avenue", 300, "Green");
        northcarolinaAve = new StreetProperty("North Carolina Avenue", 300, "Green");
        pennsylvaniaAve = new StreetProperty("Pennsylvania Avenue", 320, "Green");
        parkPlace = new StreetProperty("Park Place", 350, "Blue");
        boardWalk = new StreetProperty("Board Walk", 400, "Blue");

        jail = new Jail();
        freeParking = new FreeParking();

        // Initialize game variables


        Scanner scanner = new Scanner(System.in);

        while (gameIsRunning) {

            while (gameIsRunning) {
                currentPlayer = (currentPlayerIndex == 0) ? player1 : player2;

                // Simulate dice roll


                // Check for passing Go and collect salary


                // Handle board spaces based on player position


                // Check if a player is bankrupt


                // Switch to the next player


                // Ask for user input to continue the game
//            String gameInfo = "Press Enter to continue or type 'quit' to end the game:";
//            System.out.println(gameInfo);
//            updateDisplayText(gameInfo);

            }
            // Close the scanner
            scanner.close();
        }
    }
}