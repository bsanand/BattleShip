# The Game of BattleShip
This repository contains code for the game of BattleShip.

## Instructions
### Build
1. `./gradlew build`

### Playing the game
1. Specify the configuration of ships for players in files resources/p1.cfg and resources/p2.cfg for player 1 and player 2 respectively. The format of this file is explained in next section.
1. Start the game with `./gradlew -q run`. Follow instructions on screen to play the game.
  1. Strike position is of the form a1, c3, etc., where first character denotes row ID and next character denotes column ID. Valid values of row ID are 'a' to 'j'. Valid values of column ID are 1 to 10.

## Format of file with configuration of ships on the board
1. One line for each ship. A player can play with a maximum of 17 ships.
1. Each line contains the size, location and direction of a ship in following format - <size>,<location>,<HORIZONTAL|VERTICAL>
1. Location is of the form a1, c3, etc., where first character denotes row ID and next character denotes column ID. Valid values of row ID are 'a' to 'j'. Valid values of column ID are 1 to 10.
