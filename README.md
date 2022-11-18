# MexicanTrainDominoes
Program that calculates the "best" way to organize a hand in the dominoes game Mexican Train. 

It uses the DFS search algorithm recursively to check all combinations of dominoes.
The program returns the longest chain of dominoes made from the starting domino, as well as the longest chains made with the remaining dominoes.
This leaves the "best" hand setup (in my ~25% winrate opinion), where you have the longest chains you can, and the rest are also sorted into the longest chains that can be played on the shared train.

Program also contains the skeleton of a larger program meant to simulate the entire game assuming both players used this strategy.

Disclaimer: Not guaranteed to make you a better dominoes player :)
