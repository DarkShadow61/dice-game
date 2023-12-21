
package dicegame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Dicegame {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Welcome to the Dice Game");
        System.out.println("Press '1' to start the game or '0' to quit:");
        String gameChoice = scanner.nextLine().trim().toLowerCase();

        if ("0".equals(gameChoice)) {
            System.out.println("Game quit successfully.");
            return; // return for the exit the game if user select 0
        } else if (!"1".equals(gameChoice)) {
            System.out.println("Invalid input. Exiting the game.");
            return; // basically here if user enter invalid input then game over..
        }

        
        System.out.print("Enter name for Player 1: ");
        String player1Name = scanner.nextLine().trim();

        System.out.print("Enter name for Player 2: ");
        String player2Name = scanner.nextLine().trim();

        
        int[][] scores = new int[8][3];
        boolean[][] usedCategories = new boolean[7][3];

    ScoreTable(scores, player1Name, player2Name);

      
        
        for (int round = 1; round <= 7; round++) {// 7 round of game loop
            for (int player = 1; player <= 2; player++ ) {//2 player loop
            System.out.println("---------------------");
              String currentPlayerName = player == 1 ? player1Name : player2Name;
            System.out.println(" Round " + round +" : "+ currentPlayerName+ " 's turn: "); // showing each round name with player name and turns
            System.out.println("---------------------");

     
                // this lsit save the value of dice
                List<Integer> dice = new ArrayList<>();
                List<Integer> asideDice = new ArrayList<>();
                List<Integer> selectedForSequence = new ArrayList<>();

                
                int category = 0;
                boolean categorySelected = false;
                boolean deferredLastChance = false; 

               
                for (int chance = 1; chance <= 3; chance++) {// 3 chance in each round loop

                    System.out.println("Chance " + chance + ": ");
                    System.out.println("enter 't' to throw dice or 'd' to defer: ");

                    
                    String throwChoice = scanner.nextLine().trim();
                    if ("t".equalsIgnoreCase(throwChoice)) {
                        if (chance == 1 || deferredLastChance) {
                            // here in 1st chance throwing 5 dice and if user defer the chance so in second chance still user have can throw 5 dice
                            throwDice(dice, 5, random); 
                        } else if (category == 7) {
                            // sepecially for C-7 where throwing only those dice which are not select by user in privous ch..
                            dice.removeAll(selectedForSequence); 
                            throwDice(dice, 5 - selectedForSequence.size(), random); 
                        } else {
                           
                            dice.removeAll(asideDice);  // For categories 1 to 6, throw only unselected  dice after removing selected ones
                            throwDice(dice, dice.size(), random); 
                        }

                        // showing throwing dice
                        printDice(dice);
                        deferredLastChance = false; 

                      

                        while (!categorySelected) {
                         System.out.print("Press 's' to select a category or 'd' to defer: ");
                       String categoryChoice = scanner.nextLine().trim();

                          if ("s".equalsIgnoreCase(categoryChoice)) {
                          boolean validCategory = false;

                      while (!validCategory) {
                             System.out.println("Select a category : ");
                            System.out.println("enter 1 to select the category 1");
                            System.out.println("enter 2 to select the category 2");
                            System.out.println("enter 3 to select the category 3");
                            System.out.println("enter 4 to select the category 4");
                            System.out.println("enter 5 to select the category 5");
                            System.out.println("enter 6 to select the category 6");
                            System.out.println("enter 7 to select the category 7 for Sequnce :");
                    if (scanner.hasNextInt()) {
                    category = scanner.nextInt();
                   scanner.nextLine(); //this code mainly use to add input by user in new line
 
                 // Check if the number is within the valid range (1-7)
                 if (category >= 1 && category <= 7) {
                    validCategory = true;

                    if (!usedCategories[category - 1][player]) {// here this code check category alreday in use or not or if user select the input diffrent thing then they got the message to add number in between 1 to 7
                        categorySelected = true; 
                        usedCategories[category - 1][player] = true;
                    } else {
                        System.out.println("Category already in used. Please select  a different category.");
                    }
                } else {
                    System.out.println("This category does not exist. Please enter a category number between 1 and 7.");
                }
            } else {
                 System.out.println("This category does not exist. Please enter a category number between 1 and 7.");
                scanner.next(); 
            }
        }
    } else if ("d".equalsIgnoreCase(categoryChoice)) {
        System.out.println("Category selection deferred.");
        deferredLastChance = true; 
        break; 
    }
}

                        // code for sequence-------------------------------------------------------------
                        if (categorySelected && category == 7) {
                            System.out.print("if you want to make sequnce plaesae enter 'Y' for yes or 'N' for no : ");
                            String sequenceChoice = scanner.nextLine().trim();

                            if ("y".equalsIgnoreCase(sequenceChoice)) {
                                selectedDiceForSequence(dice, selectedForSequence, scanner);
                                if (selectedForSequence.size() == 5) {
                                    if (Sequencechecking(selectedForSequence)) {
                                        System.out.println("It's a sequence! congratulation you got 20 point.");
                                        break; // this break use for the sequence , when user got sequence then they not need to finish remaining chance 
                                    } else {
                                        System.out.println("No sequence found with selected dice.");
                                    }
                                }
                            }
                        } else if (categorySelected) {
                            // Logic for categories 1 to 6: removing selected dice
                            SelectedDice(dice, asideDice, category);
                            System.out.println("Selected Dice after Chance " + chance + ": " + asideDice);
                        }
                    } else if ("d".equalsIgnoreCase(throwChoice)) {
                        System.out.println("Chance " + chance + " deferred.");
                        deferredLastChance = true; // Marking the chance as deferred
                      
                    }
                }


                // Updating the scores in the scores array
                playerscore(scores, player, category, asideDice, selectedForSequence);

                // Displaying the current score table
               ScoreTable(scores, player1Name, player2Name);

               
                System.out.println(); // Adding a newline in between players
            }
        }

        // Calculating total scores for both players
        int player1TotalScore = scores[7][1];
        int player2TotalScore = scores[7][2];

        // Displaying final scores and showing the winner
        System.out.println("Final Scores:");
       ScoreTable(scores, player1Name, player2Name);

        if (player1TotalScore > player2TotalScore) {
            System.out.println("player1Name +  wins!");
        } else if (player2TotalScore > player1TotalScore) {
            System.out.println("player2Name + wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

// code for printing the current dice values -------------------------------------------------------------------------------------------------------------------
private static void printDice(List<Integer> dice) {
    System.out.print("Rolled Dice: ");
    for (int die : dice) {
        System.out.print("[" + die + "] "); // Print each die value in brackets
    }
    System.out.println(); 
}

// code for rolling dice-------------------------------------------------------------------------------------------------------------------------------------
private static void throwDice(List<Integer> dice, int numberOfDice, Random random) {
    dice.clear(); // Clearing existing dice values
    for (int i = 0; i < numberOfDice; i++) {
        dice.add(random.nextInt(6) + 1); // Add a random number between 1 and 6 to the dice list
    }
}

// code for removing selected dice based on the selected category---------------------------------------------------------------------------
private static void SelectedDice(List<Integer> dice, List<Integer> removedDice, int category) {
    List<Integer> diceToRemove = new ArrayList<>(); // List to hold dice to be removed

    // Find dice that match the category number
    for (int die : dice) {
        if (die == category) {
            diceToRemove.add(die); // Add matching dice to the removal list
        }
    }

    // Remove matching dice from the original list and add them to the selected Dice list
    for (int die : diceToRemove) {
        dice.remove(Integer.valueOf(die)); // Remove die from original list
        removedDice.add(die); // Add die to removedDice list
    }

    
}

// code to check if the selected dice create a sequence--------------------------------------------------------------------------------
private static boolean Sequencechecking(List<Integer> dice) {
    Collections.sort(dice); // Sort the dice to make sequence checking easier

    // Loop through the sorted dice to check for consecutive numbers
    for (int i = 0; i < dice.size() - 1; i++) {
        if (dice.get(i) + 1 != dice.get(i + 1)) {
            return false; 
        }
    }

    return true; 
}

// code for give option to user to make sequence throw select the dice------------------------------------------------------------------------------------
private static void selectedDiceForSequence(List<Integer> dice, List<Integer> selectedForSequence, Scanner scanner) {
    System.out.println("Select dice to keep for sequence (enter numbers separated by spaces, e.g., 1 2 3):");
    // Displaying available dice for selection
    for (int i = 0; i < dice.size(); i++) {
        System.out.println((i + 1) + ". [" + dice.get(i) + "]");
    }

    // Reading player's choices for dice to keep for sequence
    String[] choices = scanner.nextLine().split("\\s+");
    for (String choice : choices) {
        try {
            int index = Integer.parseInt(choice.trim()) - 1; // Convert choice to index
            // Add the chosen die to the selecteddiceForSequence list if it's a valid choice
            if (index >= 0 && index < dice.size() && !selectedForSequence.contains(dice.get(index))) {
                selectedForSequence.add(dice.get(index));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + choice); 
        }
    }

    // showing selected dice for sequence
    System.out.println("Selected Dice for Sequence:");
    for (Integer die : selectedForSequence) {
        System.out.print("[" + die + "] ");
    }
    System.out.println();
}

// code to check for a sequence at the end of the chance---------------------------------------------------------------------------------------
private static void endroundSequenceCheck(List<Integer> selectedForSequence) {
    // Check if a sequence is doce with 5 dice
    if (selectedForSequence.size() < 5 || !Sequencechecking(selectedForSequence)) {
    
    } else {
        System.out.println("A sequence was successfully created! congratulation you got 20 points.");
    }
}
//code for score table------------------------------------------------------------------------------------------------------------
private static void ScoreTable(int[][] scores, String player1Name, String player2Name) {
    System.out.println("Score Table:");
            System.out.println("--------------------------------------------------");
System.out.printf("%-15s|%-15s|%-15s |\n", "Category", player1Name, player2Name);

            System.out.println("--------------------------------------------------");
    for (int i = 0; i < 8; i++) {
        if (i < 7) {
            System.out.printf("%-15d|%-15d|%-15d |\n",  i + 1, scores[i][1], scores[i][2]);
        } else {
            System.out.println("--------------------------------------------------");
            System.out.printf("%-15s|%-15d|%-15d |\n", "Total", scores[i][1], scores[i][2]);
        }
            System.out.println("--------------------------------------------------");
    }
}


// code to update the scores based on the player's actions-------------------------------------------------------------------
private static void playerscore(int[][] scores, int player, int category, List<Integer> removedDice, List<Integer> selectedForSequence) {
    int score = 0;
    if (category == 7) {
        // Score calculation for category 7 (sequence)
        if (selectedForSequence.size() == 5 && Sequencechecking(selectedForSequence)) {
            score = 20; // Assign 20 points for a successful sequence
        } else {
            score = 0; // No points for an unsuccessful sequence
        }
    } else {
        // Score calculation for categories 1 to 6: sum of selected dice values
        for (int die : removedDice) {
            score += die;
        }

                    // Final check for sequence in c-7
                    endroundSequenceCheck(selectedForSequence);
    }
    // Update the scores array with the new score
    scores[category - 1][player] += score; // Update score for specific category
    scores[7][player] += score; // Update total score
}

}