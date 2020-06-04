# Marble Solitaire Board Game

### Add
* Created an abstract class that implements the model
* Created the two new models, European and Triangular, and had all three models extend the newly created abstract class
* Create an enum to represent all possible board elements (MARBLE, EMPTY, NULL)
* Created a method that checks for a valid construction specific to each of the board to allow different rules for how the initial board should look like

### Replace
* Moved the Util class' methods to the abstract class, and had the concrete classes override as needed
* Broke down the check for an illegal move into different methods: check if the move is out of bounds (a universal check) and a more specific ruleset that only applies to a given board
* Moved the generation of the board's array to a different method to allow different rules to where elements should be place
