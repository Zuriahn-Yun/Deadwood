# Deadwood

1. File Setup
Before you compile, make sure your folders look exactly like this:

Main Folder: Put all your .java files here.

xml Folder: Create a folder named xml inside your main folder and put board.xml and cards.xml inside it.

2. Compile the Game
Open your Terminal, navigate to your project folder, and type:

Bash
javac *.java

3. Run the Game
In the same terminal window, type:

Bash
java Deadwood

INFO (Game Controls):
Numbers: Use numbers (1, 2, 3) to pick actions from the menus.

How to Win: The game ends after 3 or 4 days (depending on player amount). The winner is the person with the most points based on:
Money + Credits + (5 × Your Rank)