import java.util.ArrayList;
import java.util.Random;

/**
 * Program meant to simulate a game of Mexican Train (dominoes) to see whether
 * or not luck is a factor. Currently just has hand organization implemented.
 * 
 * @author Sternboy
 */
public class MexicanTrain {

	DrawPile pile; // The boneyard to hold the dominoes
	static int numPlayers = 2; // The number of players playing
	static int numDominoes = 15; // The number of dominoes players start with
	final Domino startingDomino; // The central domino to branch from

	// Constructor
	MexicanTrain() {
		this.pile = new DrawPile();
		this.startingDomino = this.pile.getStartingDomino();
	}

	// Main method for running the game
	public static void main(String[] args) {

		MexicanTrain game = new MexicanTrain();

		// Setup Player Hands
		Hand[] players = new Hand[numPlayers];
		for (int i = 1; i <= numPlayers; i++) {
			players[i - 1] = new Hand("Player " + i);
			players[i - 1].setup(game.pile, numDominoes);
			players[i - 1].printHand();
			players[i - 1].organize(game.startingDomino);
		}

	}

}

// Class used to help with the functions of dominoes
class Domino {
	private int num1; // the number on one side
	private int num2; // the number on the other side
	private int points; // the point value of the domino
	private boolean num1IsVisited; // visited boolean for side 1
	private boolean num2IsVisited; // visited boolean for side 2

	// Constructor
	Domino(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
		this.points = num1 + num2;
	}

	// Getter
	boolean num1Visited() {
		return this.num1IsVisited;
	}

	// Setter
	void VisitNum1() {
		this.num1IsVisited = true;
	}

	// Setter
	void UnvisitNum1() {
		this.num1IsVisited = false;
	}

	// Getter
	boolean num2Visited() {
		return this.num2IsVisited;
	}

	// Setter
	void VisitNum2() {
		this.num2IsVisited = true;
	}

	// Setter
	void UnvisitNum2() {
		this.num2IsVisited = false;
	}

	// Getter
	int getNum1() {
		return this.num1;
	}

	// Getter
	int getNum2() {
		return this.num2;
	}

	// Getter
	int getPoints() {
		return this.points;
	}

	// Checks whether one domino can connect to another
	int connectsWith(Domino other) {
		if (this.num1IsVisited && this.num2IsVisited) {
			return -1;
		} else if (this.num1IsVisited && !this.num2IsVisited) {
			if ((other.num1 == this.num2 && !other.num1IsVisited)
					|| (other.num2 == this.num2 && !other.num2IsVisited)) {
				if (other.num1 == this.num2) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return -1;
			}
		} else if (!this.num1IsVisited && this.num2IsVisited) {
			if ((other.num1 == this.num1 && !other.num1IsVisited)
					|| (other.num2 == this.num1 && !other.num2IsVisited)) {
				if (other.num1 == this.num1) {
					return 3;
				} else {
					return 4;
				}
			} else {
				return -1;
			}
		} else if (!this.num1IsVisited && !this.num2IsVisited) {
			if ((other.num1 == this.num1 && !other.num1IsVisited) || (other.num1 == this.num2 && !other.num1IsVisited)
					|| (other.num2 == this.num1 && !other.num2IsVisited)
					|| (other.num2 == this.num2 && !other.num2IsVisited)) {
				if (other.num1 == this.num1) {
					return 3;
				} else if (other.num1 == this.num2) {
					return 1;
				} else if (other.num2 == this.num2) {
					return 2;
				} else {
					return 4;
				}
			} else {
				return -1;
			}
		} else {
			System.out.println("Error with visiting. Aborting");
			return -1;
		}

	}

	// Overridden toString to make printing dominoes easier to read
	@Override
	public String toString() {
		return "" + num1 + "-" + num2;
	}

}

// Train class for the game (WIP)
class Train {
	private Domino currStarter;
	private String name;
	private ArrayList<Domino> dominoes;
	private boolean openToAll;

	// Constructor
	Train(String name, Domino starter) {
		this.name = name;
		this.currStarter = starter;
		this.dominoes = new ArrayList<>();
		this.openToAll = false;
	}

	// Method for trying to add a domino to the train
	boolean addToTrain(Domino d) {
		if (d.num1Visited() || d.num2Visited()) {
			System.out.println("That Domino is used already on one side somehow...Please try a different one.");
			return false;
		} else {
			int connects = d.connectsWith(currStarter);
			switch (connects) {
			case 1:
				currStarter.VisitNum1();
				d.VisitNum2();
				dominoes.add(d);
				currStarter = d;
				break;
			case 2:
				currStarter.VisitNum2();
				d.VisitNum2();
				dominoes.add(d);
				currStarter = d;
				break;
			case 3:
				currStarter.VisitNum1();
				d.VisitNum1();
				dominoes.add(d);
				currStarter = d;
				break;
			case 4:
				currStarter.VisitNum2();
				d.VisitNum1();
				dominoes.add(d);
				currStarter = d;
				break;
			case -1:
				System.out.println("Error connecting that domino");
				return false;
			}
			return true;
		}

	}

	// FIXME other methods

}

// Hand class for recording players hands
class Hand {
	private ArrayList<Domino> hand;
	private String name;
	Random rand = new Random();

	// FIXME Finish method for taking a turn 
	void takeTurn() {

	}

	// Totals the points in the players hand
	int countPoints(ArrayList<Domino> list) {
		int sum = 0;
		for (Domino d : list) {
			sum += d.getPoints();
		}
		return sum;
	}

	// Constructor
	Hand(String username) {
		this.hand = new ArrayList<>();
		this.name = username;
	}

	// Adds a domino to the hand
	void addToHand(Domino d) {
		this.hand.add(d);
	}

	// Removes a domino from the hand
	void playFromHand(Domino d) {
		this.hand.remove(d);
	}

	// Prints the players hand
	void printHand() {
		System.out.println("========");
		System.out.println(this.name);
		System.out.println("========");
		for (int i = 0; i < this.hand.size(); i++) {
			System.out.println(this.hand.get(i).toString());
		}
		System.out.println("========");
	}

	// Draws the number of dominoes needed at the start
	void setup(DrawPile p, int d) {
		for (int i = 0; i < d; i++) {
			this.addToHand(p.draw());
		}
	}

	// Organizes the players hand based on the longest chain they can make
	void organize(Domino gameStarter) {
		// Get all potential starting dominoes in their hand
		int startingNum = gameStarter.getNum1();
		ArrayList<Domino> playerStarters = new ArrayList<>();
		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getNum1() == startingNum || this.hand.get(i).getNum2() == startingNum) {
				playerStarters.add(this.hand.get(i));
				// System.out.println("Adding starter: " + this.hand.get(i));
			}
		}

		// Create the list of the longest chain
		ArrayList<Domino> longestChain = new ArrayList<>();
		if (playerStarters.size() == 0) {
			playerStarters.add(this.hand.get(rand.nextInt(this.hand.size())));
			System.out.println("Player does not have a link to starter.\nChaining off: " + playerStarters.get(0));
		}

		// For each starting domino in their hand, get the longest chain
		for (int i = 0; i < playerStarters.size(); i++) {
			int sideAlreadyVisited = 0;
			if (playerStarters.get(i).getNum1() == startingNum) {
				playerStarters.get(i).VisitNum1();
				sideAlreadyVisited = 1;
			} else {
				playerStarters.get(i).VisitNum2();
				sideAlreadyVisited = 2;
			}
			// System.out.println("Attempting starter chain for: " + playerStarters.get(i));
			ArrayList<Domino> chainAttempt = this.organizeRec(playerStarters.get(i), this.hand);

			// Compare by number of dominoes, then by number of points
			if (chainAttempt.size() > longestChain.size()) {
				longestChain = chainAttempt;
			} else if (chainAttempt.size() == longestChain.size()) {
				int chainAttemptPts = this.countPoints(chainAttempt);
				int longestChainPts = this.countPoints(longestChain);
				if (chainAttemptPts > longestChainPts) {
					longestChain = chainAttempt;
				}
			}

			// Free the starting dominoes to be used in other chains
			if (sideAlreadyVisited == 1) {
				playerStarters.get(i).UnvisitNum1();
			} else {
				playerStarters.get(i).UnvisitNum2();
			}

		}

		// Get list of all dominoes in hand not in the longest list
		System.out.println("Longest Chain (" + longestChain.size() + "): " + longestChain);
		ArrayList<Domino> restOfHand = new ArrayList<>();
		for (Domino d : this.hand) {
			if (!longestChain.contains(d)) {
				restOfHand.add(d);
			}
		}
		System.out.println("Rest of Hand (" + restOfHand.size() + "): " + restOfHand + "\n============");

//		ArrayList<Domino> secondChain = this.organizeList(restOfHand, startingNum);
//		System.out.println("Second Chain (" + secondChain.size() + "): " + secondChain);

		// Get the chains until there are none remaining
		ArrayList<ArrayList<Domino>> secondaryChains = new ArrayList<>();
		while (restOfHand.size() > 0) {
			ArrayList<Domino> secondChain = this.organizeList(restOfHand, startingNum);
			secondaryChains.add(secondChain);
			if (secondChain.size() > 0) {
				for (Domino d : secondChain) {
					restOfHand.remove(d);
					// System.out.println("Removed: " + d);
				}
				// System.out.println("Rest of Hand Post Removal: " + restOfHand);
			}
		}
		for (ArrayList<Domino> list : secondaryChains) {
			System.out.println("SubList: " + list);
		}

		// Reorder dominoes in hand into order of lists (subLists on left)
		ArrayList<Domino> newHand = new ArrayList<>();
		for (ArrayList<Domino> list : secondaryChains) {
			for (Domino d : list) {
				newHand.add(d);
			}
		}
		for (Domino d : longestChain) {
			newHand.add(d);
		}
		System.out.println("Organized Hand: " + newHand);

	}

	// Organizes a subset of the players hand
	ArrayList<Domino> organizeList(ArrayList<Domino> list, int startingInt) {

		// No work if list is a single domino
		if (list.size() == 1) {
			ArrayList<Domino> returnList = new ArrayList<>();
			returnList.add(list.get(0));
			return returnList;
		}

		if (list.size() == 0) {
			return new ArrayList<>();
		}

		// Get all potential starting dominoes in their hand
		ArrayList<Domino> playerStarters = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNum1() == startingInt || list.get(i).getNum2() == startingInt) {
				playerStarters.add(list.get(i));
				// System.out.println("Adding secondary starter: " + list.get(i));
			}
		}

		// Create the list of the longest chain
		ArrayList<Domino> longestChain = new ArrayList<>();
		if (playerStarters.size() == 0) {
			playerStarters.add(list.get(rand.nextInt(list.size())));
			// System.out.println("No link to secondary starter.\nChaining off: " +
			// playerStarters.get(0));
		}

		// For each starting domino in their hand, get the longest chain
		for (int i = 0; i < playerStarters.size(); i++) {
			int sideAlreadyVisited = 0;
			if (playerStarters.get(i).getNum1() == startingInt) {
				playerStarters.get(i).VisitNum1();
				sideAlreadyVisited = 1;
			} else {
				playerStarters.get(i).VisitNum2();
				sideAlreadyVisited = 2;
			}
			// System.out.println("Attempting secondary starter chain for: " +
			// playerStarters.get(i));
			ArrayList<Domino> chainAttempt = this.organizeRec(playerStarters.get(i), list);

			// Compare by number of dominoes, then by number of points
			if (chainAttempt.size() > longestChain.size()) {
				longestChain = chainAttempt;
			} else if (chainAttempt.size() == longestChain.size()) {
				int chainAttemptPts = this.countPoints(chainAttempt);
				int longestChainPts = this.countPoints(longestChain);
				if (chainAttemptPts > longestChainPts) {
					longestChain = chainAttempt;
				}
			}

			// Free the starting dominoes to be used in other chains
			if (sideAlreadyVisited == 1) {
				playerStarters.get(i).UnvisitNum1();
			} else {
				playerStarters.get(i).UnvisitNum2();
			}

		}

		// Do until all dominoes in hand are in a list
		// System.out.println("Returning chain: " + longestChain);
		return longestChain;

	}

	// Recursive method for organize
	ArrayList<Domino> organizeRec(Domino start, ArrayList<Domino> list) {
		// Visit the current domino and get its list of branches
		int sideAlreadyVisited = 0;
		int starterNum = -1;
		if (start.num1Visited()) {
			starterNum = start.getNum2();
			start.VisitNum2();
			sideAlreadyVisited = 1;
		} else if (start.num2Visited()) {
			starterNum = start.getNum1();
			start.VisitNum1();
			sideAlreadyVisited = 2;
		}
		if (starterNum == -1) {
			throw new RuntimeException("Error with recursive organization");
		}

		ArrayList<Domino> longestList = new ArrayList<>();
		longestList.add(start);

		// Setup possible branches
		ArrayList<Domino> possibleBranches = new ArrayList<>();
		for (Domino d : list) {
			if ((d.getNum1() == starterNum && d.num1Visited() == false)
					|| (d.getNum2() == starterNum && d.num2Visited() == false)) {
				possibleBranches.add(d);
			}
		}

		// Exit if there are no other branches to go down
		if (possibleBranches.size() == 0) {
			if (sideAlreadyVisited == 1) {
				start.UnvisitNum2();
			} else {
				start.UnvisitNum1();
			}
			return longestList;
		} else {

			// Go through the branches and get the longest one / one with most points
			ArrayList<Domino> longestBranch = new ArrayList<>();
			for (Domino b : possibleBranches) {

				int sideVisited = 0;
				if (starterNum == b.getNum1()) {
					b.VisitNum1();
					sideVisited = 1;
				} else {
					b.VisitNum2();
					sideVisited = 2;
				}

				// System.out.println("Attempting sub-branch: " + b);
				ArrayList<Domino> branchAttempt = this.organizeRec(b, list);

				// Compare by number of dominoes, then by number of points
				if (branchAttempt.size() > longestBranch.size()) {
					longestBranch = branchAttempt;
				} else if (branchAttempt.size() == longestBranch.size()) {
					int chainAttemptPts = this.countPoints(branchAttempt);
					int longestChainPts = this.countPoints(longestBranch);
					if (chainAttemptPts > longestChainPts) {
						longestBranch = branchAttempt;
					}
				}

				if (sideVisited == 1) {
					b.UnvisitNum1();
				} else {
					b.UnvisitNum2();
				}

			}

			// Append longest branch to the longestList
			while (longestBranch.size() > 0) {
				longestList.add(longestBranch.remove(0));
			}

		}

		// Unvisit this domino
		if (sideAlreadyVisited == 1) {
			start.UnvisitNum2();
		} else {
			start.UnvisitNum1();
		}

		// Return the longest list
		return longestList;

	}
}

// Class for handling the boneyard
class DrawPile {

	Random rand = new Random();
	private ArrayList<Domino> pile;

	// Constructor
	DrawPile() {
		this.pile = new ArrayList<>();
		this.resetPile();
	}

	// Resets the pile
	void resetPile() {
		this.pile.clear();
		for (int i = 0; i < 13; i++) {
			for (int j = i; j < 13; j++) {
				pile.add(new Domino(i, j));
			}
		}
		this.shuffle();

	}

	// Shuffles the dominoes
	void shuffle() {
		ArrayList<Domino> shuffledPile = new ArrayList<>();
		while (this.pile.size() > 0) {
			shuffledPile.add(this.pile.remove(rand.nextInt(pile.size())));
		}
		this.pile = shuffledPile;
	}

	// Prints the dominoes in the pile
	void printPile() {
		System.out.println("Draw Pile");
		System.out.println("========");
		for (int i = 0; i < this.pile.size(); i++) {
			System.out.println(this.pile.get(i).toString());
		}
		System.out.println("========");
	}

	// Removes a domino from the pile
	Domino draw() {
		return pile.remove(rand.nextInt(pile.size()));
	}

	// Picks a domino to start
	Domino getStartingDomino() {
		int chosenNum = rand.nextInt(13);
		for (int i = 0; i < this.pile.size(); i++) {
			Domino currentDomino = this.pile.get(i);
			if (currentDomino.getNum1() == chosenNum && currentDomino.getNum2() == chosenNum) {
				System.out.println(currentDomino);
				return pile.remove(i);
			}
		}
		return null; // never happens
	}

}
