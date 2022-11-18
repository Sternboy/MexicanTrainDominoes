# MexicanTrainDominoes
Little simulation program of the classic dominoes game Mexican Train. Currently just organizes the player hands with plans to simulate the entire game. 

The classic disgruntled-boyfriend-loses-game-and-makes-a-program-to-simulate-it-proving-the-game-is-just-luck situation.
After playing Mexican Train with my girlfriend a bunch of times (and pretty much always losing), it always felt like I was losing due to terrible luck.
Cue making a program to simulate my strategy for the entire game...
Uses the DFS search algorithm recursively to check all combinations of dominoes.
It returns the longest chain of dominoes made from the starting domino.
Since players can also play from other chains, it repeatedly does this process till no more dominoes can be connected.
This leaves the "best" hand setup (in my ~25% winrate opinion), where you have the longest chains you can, and the rest are also sorted into the longest chains.
The thought process is that if you only use the dominoes in your hand, you reduce the likelihood of getting points (since points only go to the people who were not first to empty their hand).
Currently, all the program does is sort the players hands (with a skeleton for the simulation part for future implementation).
That being said, it is still an interesting and useful program for those looking to improve their domino chaining ability

Disclaimer: Not guaranteed to make you a better dominoes player, but you might become good enough to finally beat your girlfriend ;)
