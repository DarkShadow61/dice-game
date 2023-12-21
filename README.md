Dice-Game.java
A Dice Game made in java using Apache NetBeans IDE 12.6 edition configured as a NetBeans ‘Ant’ build

A Java 8 program (as a NetBeans project) to simulate a simple dice game for two players sitting at the console, i.e., taking turns by sharing the keyboard. The game is a scoring game that requires five dice. The aim of the game is to be the player with the highest cumulative score after seven rounds of play. A description of the game follows. Game description The program will be required to represent the following scoresheet at the console:

![291011335-e70d03d0-b3ff-4efc-97c1-f3e6eb20967f](https://github.com/DarkShadow61/dice-game/assets/65812350/090d5ef5-cd1f-4214-b3b3-77e79477f355)


Figure 1: Score table for dice game

The game is played over exactly seven rounds i.e., each player will take seven turns (alternating). For each turn, a player has up to three throws of the dice. The aim is for the player to produce a selected die number (mapped to the score table) with as many of the five dice as possible to gain the highest score for that turn. Each turn challenges the player to make an early choice of category and then go for it. A different category must be selected for each turn, i.e., all seven categories must be selected over the seven turns. The game is mainly luck, but with some strategy.

The game progresses as follows:

The first player throws all five dice. After the throw, the player has the following options:

• Set aside one or more of the dice.

• Defer the throw i.e., decide not to select any of the dice and keep all five dice to throw again.

In setting aside one or more dice, the player is required to select a category in the score table to go for. This must be one of the seven categories that has not already been selected (in an earlier turn) for that player. Once a choice has been made by the player (for that turn), it cannot be changed. The categories refer to the six numbers displayed by a die, and a sequence. A number category refers to the number shown by the dice that have been set aside (i.e., once the number category is selected, the aim is to try to throw as many of the remaining dice with the selected number to maximise the score). The sequence category refers to all five dice showing either numbers 1-2-3-4-5 or 2-3-4-5-6. In choosing to defer following the first throw, the player can re-throw all five dice and make the category selection after the second throw (but obviously that means the player will then only have two throws to optimise the score against one of the categories). It is, of course, quite possible (although statistically improbable) that the player throws all five dice with the same number value in one throw or throws an entire sequence in one throw. In either of these cases, if the player can select the appropriate category and set aside all five dice, then the program will end the turn after that throw (moving on to the other player). Similarly, if a player manages to set aside all five dice after their second throw, then the program will end the turn. In other words, for any given turn a player can make up to a maximum of three throws, but if the player manages to set aside all five dice after just one or two throws then the turn ends for that player. It is also possible for a player to defer twice and essentially gamble on their final throw for that turn. However, the player must then select a category for that throw. Towards the final stages of the game, it is possible that the player has only a couple of categories not yet completed in the score table and the turn has resulted in no dice that match any of the remaining categories, but they still must select a category (and in this case would score zero for that category).

After each turn the program should display the updated score table.

After the first player’s turn has finished, the next player takes their turn (in the same way). The game comprises both players taking exactly seven turns. The winner is the player with the highest cumulative score at the end of the game.

Scoring

Scoring is very simple for this dice game. For each of the number categories, a player will score the category number multiplied by the total number of dice set aside to match that category. E.g., if the player selects fives as the category (say after the first throw in which they set aside a couple of dice showing five), and at the end of the turn had three dice showing five, then they would score 15 for that category (and 15 should be inserted into the score table). If a player successfully manages to throw a sequence (1-2-3-4-5 or 2-3-4-5- 6) then they score 20 points for that turn (if they have selected that category of course).
