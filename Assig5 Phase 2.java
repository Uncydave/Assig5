/* ---------------------------------------------------------------------------------------------------------------- 
Nautilus Group
Caleb Allen
Daisy Mayorga
David Harrison
Dustin Whittington
Michael Cline
CST 338
M5: GUI Card Java Program
30 May 2017

PURPOSE
Over several phases, we will be using the classes we wrote from M3 (Card, Hand, and Deck) and adding to those classes
the a GUI framework.  To do this we will use some additional classes and create some of our own.

This is the second phase out of three.  This second phase will let you turn what you did in the first phase into a 
multi-class project.  We will encapsulate the Card Icons in a class GUICard - Once we debug imagery for cards, 
we can move it into its own class, GUICard.  Next, we will create a CardTable class - This JFrame class will embody
the JPanels and Layout(s) needed for our application. This is where all the cards and controls will be placed.
----------------------------------------------------------------------------------------------------------------- */
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Assig5
{
   static final int NUM_CARDS_PER_HAND = 7;
   static final int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 

   public static void main(String[] args)
   {
      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // show everything to the user
      myCardTable.setVisible(true);

      //make play deck and shuffle it
      Deck practiceDeck = new Deck();
      practiceDeck.shuffle();
      
      //create Hands for computer, player, and play area
      Hand computerHand = new Hand();
      Hand humanHand = new Hand();
      Hand playArea = new Hand();

      for (int count = NUM_CARDS_PER_HAND; count > 0; count--)
      {
         computerHand.takeCard(practiceDeck.dealCard());
         humanHand.takeCard(practiceDeck.dealCard());
      }
      
      // and two random cards in the play region (simulating a computer/hum ply)
      playArea.takeCard(practiceDeck.dealCard());
      playArea.takeCard(practiceDeck.dealCard());
      
      // CREATE LABELS ----------------------------------------------------
      JLabel[] computerCards = new JLabel[computerHand.getNumCards()];
      JLabel[] humanCards = new JLabel[humanHand.getNumCards()];
      JLabel[] playedCards = new JLabel[playArea.getNumCards()];
      
      // ADD LABELS TO PANELS -----------------------------------------
      for (int count = 0; count < computerHand.getNumCards(); count++)
      {
         //fill computer hand with card backs
         computerCards[count] = new JLabel(GUICard.getBackCardIcon());
         myCardTable.pnlComputerHand.add(computerCards[count]);
      }
      
      for (int count = 0; count < humanHand.getNumCards(); count++)
      {
         //fill human hand with cards in hand
         humanCards[count] = new JLabel(GUICard.getIcon(humanHand.inspectCard(count)));
         myCardTable.pnlHumanHand.add(humanCards[count]);
      }
      
      //Arrange played cards in a grid with text labels underneath
      myCardTable.pnlPlayArea.setLayout(new GridLayout(2,2));
      
      for (int count = 0; count < playArea.getNumCards(); count++)
      {
         //Fill play area with played cards
         playedCards[count] = new JLabel(GUICard.getIcon(playArea.inspectCard(count)));
         myCardTable.pnlPlayArea.add(playedCards[count]);
      }
      
      //Add text label for played cards
      JLabel computerText = new JLabel("Computer",JLabel.CENTER);
      myCardTable.pnlPlayArea.add(computerText);
      JLabel playerText = new JLabel("You",JLabel.CENTER);
      myCardTable.pnlPlayArea.add(playerText);
      
      //show everything to the player
      myCardTable.setVisible(true);
   }
}

class CardTable extends JFrame
{
   static final int MAX_CARDS_PER_HAND = 56;
   static final int MAX_PLAYERS = 2;

   private int numCardsPerHand;
   private int numPlayers;

   // JPanels for computer's hand, player's hand, and the play area
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      /*
       * We will use three Public JPanels, one for each hand (player-bottom and computer-top)
       * and a middle "playing" JPanel.  The client (below) will generate the human's cards at
       * random and will be visible in the bottom JPanel, while the computer's cards will be
       * chosen (again, by the client) to be all back-of-card images in the top JPanel.  The
       * middle JPanel will display cards that are "played" by the computer and human during
       * the conflict.  Let's assume that each player plays one card per round, so for a
       * 2-person game (computer + human) there will be exactly two cards played in the central
       * region per round of battle.  My client chose a joker for the two central cards, just so
       * we would have something to see in the playing region. 
       */

      // computer's hand
      pnlComputerHand = new JPanel();
      pnlComputerHand.setBorder(BorderFactory.createTitledBorder("Computer Hand"));
      
      // player's hand
      pnlHumanHand = new JPanel();
      pnlHumanHand.setBorder(BorderFactory.createTitledBorder("Your Hand"));
      
      // play area
      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(BorderFactory.createTitledBorder("Play Area"));
      
      // layout for JPanels
      add(pnlComputerHand,BorderLayout.NORTH);
      add(pnlHumanHand,BorderLayout.SOUTH);
      add(pnlPlayArea,BorderLayout.CENTER);
   }
   
   public int getNumCardsPerHand()
   {
      /*
       * Accessor to get the number of cards per hand
       */
      return this.numCardsPerHand;
   }

   public int getNumPlayers()
   {
      /*
       * Accessor to get the number of players
       */
      return this.numPlayers;
   }
}

class GUICard
{ 
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   // loads all of the card images into a 2D array one time only
   static void loadCardIcons()
   {
      if(!iconsLoaded)
      {
         for(int j = 0; j <= 3; j++)
         {
            for(int k = 0; k <= 13; k++)
            {
               iconCards[k][j] = new ImageIcon("images/" + turnIntIntoCardValue(k) + turnIntIntoCardSuit(j) + ".gif");
            }        
         }
         iconBack = new ImageIcon("images/BK.gif");  //loads the back of card image
         iconsLoaded = true;  //this keeps array from loading more than once
      }
   }

   // accessor for front card image (56 possibilities)
   static public Icon getIcon(Card card)
   {
      loadCardIcons();  // only instantiates one time
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   // accessor for back of card image
   static public Icon getBackCardIcon()
   {
      loadCardIcons();  // only instantiates one time
      return iconBack;
   }

   // turns value into an int
   private static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue(); 
      switch (cardsValue)
      {
         case 'A':
            return 0;
         case '2':
            return 1;
         case '3':
            return 2;
         case '4':
            return 3;
         case '5':
            return 4;
         case '6':
            return 5;
         case '7':
            return 6;
         case '8':
            return 7;
         case '9':
            return 8;
         case 'T':
            return 9;
         case 'J':
            return 10;
         case 'Q':
            return 11;
         case 'K':
            return 12;
         case 'X':
            return 13;
      }
      return 0;
   }

   // turns suit into an int
   private static int suitAsInt(Card card)
   {
      Card.Suit cardsSuit = card.getSuit();
      switch (cardsSuit)
      {
         case clubs:
            return 0;
         case diamonds:
            return 1;
         case hearts:
            return 2;
         case spades:
            return 3;
      }
      return 3;
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      switch (k)
      {
         case 0:
            return "A";
         case 1:
            return "2";
         case 2:
            return "3";
         case 3:
            return "4";
         case 4:
            return "5";
         case 5:
            return "6";
         case 6:
            return "7";
         case 7:
            return "8";
         case 8:
            return "9";
         case 9:
            return "T";
         case 10:
            return "J";
         case 11:
            return "Q";
         case 12:
            return "K";
         case 13:
            return "X";
      }
      return "A";
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      switch (j)
      {
         case 0:
            return "C";
         case 1:
            return "D";
         case 2:
            return "H";
         case 3:
            return "S";
      }
      return "S";
   }
}

class Card
{
   /*
    * CARD UPDATES REQUIRED
    * Values need to be updated to include Joker
    * public static char[] valuRanks //used to rank the cards from low to high
    * static void arraySort(Card[], int arraySize) //use a bubble sort routine to sort an array of cards
    */
   //Enumerator for the card's suit
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   };
   
   //For card ranking purposes
   public static char[] valuRanks = { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X' };
   public static Suit[] suitRanks = { Suit.clubs, Suit.diamonds, Suit.hearts,Suit.spades };
   
   //Private instance variables
   private char value;
   private Suit suit;
   private boolean errorFlag;

   //Default constructor for card overloaded to return A of spades
   public Card()
   {
      this.value = 'A';
      this.suit = Suit.spades;
   }

   //Parameterized constructor for card that accepts value and suit
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   //Parameterized constructor for card that accepts value, suit,
   //and errorFlag
   public Card(char value, Suit suit, boolean errorFlag)
   {
      set(value, suit);
      this.errorFlag = errorFlag;
   }

   //If error flag is false, returns value and suit of card in a single
   //string, otherwise returns invalid
   public String toString()
   {
      if (errorFlag == false)
      {
         return this.value + " of " + this.suit;
      }
      else
      {
         return "[ invalid ]";
      }
   }

   //Mutator to set value and suit for a card
   public boolean set(char value, Suit suit)
   {
      if (isValid(value, suit))
      {
         this.value = value;
         this.suit = suit;
         this.errorFlag = false;
         return true;
      }
      else
      {
         this.errorFlag = true;
         return false;
      }
   }

   //Accessor for card suit
   public Suit getSuit()
   {
      return this.suit;
   }

   public char getValue()
   {
      /*
       * Accessor for Value
       */
      return this.value;
   }

   public boolean getErrorFlag()
   {
      /*
       * Accessor for errorFlag
       */
      return this.errorFlag;
   }

   private boolean isValid(char value, Suit suit)
   {
      /*
       * a private helper method that returns true or false, depending on the
       * legality of the parameters. Note that, although it may be impossible
       * for suit to be illegal (due to its enum-ness), we pass it, anyway, in
       * anticipation of possible changes to the type from enum to, say, char
       * or int, someday. We only need to test value, at this time.
       */
      char[] cardType = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };
      for (int i = 0; i < cardType.length; i++)
      {
         if (value == cardType[i])
         {
            return true;
         }
      }
      return false;
   }

   //method that returns true if all members are equal, false otherwise
   public boolean equals(Card card)
   {
      if (card == this)
      {
         return true;
      }
      else
      {
         return (card.getValue() == this.getValue()) &&
               (card.getSuit().equals(this.getSuit())) &&
               (card.getErrorFlag() == this.getErrorFlag());
      }
   }
   
   // Find the card index
   private static int cardIndex(Card card)
   {
      for(int i = 0; i < valuRanks.length; i++)
      {
         if(card.getValue() == valuRanks[i])
            return i;
      }
      return -1;
   }

   // Use the bubble sort for sorting the array
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card temp;

      for(int i = 0; i < arraySize; i++)
      {
         for(int j = 1; j < arraySize - i; j++)
         {
            if(cardIndex(cardArray[j-1]) > cardIndex(cardArray[j]))
            {
               temp = cardArray[j-1];
               cardArray[j-1] = cardArray[j];
               cardArray[j] = temp;
            }
         }
      }
   }
}

class Hand
{
   /*
    * HAND UPDATES REQUIRED
    * void sort() //sorts the hand by calling the arraySort method for the Card class
    * "myCards.arraySort()"
    */

   //Safeguard to prevent a runaway program from creating a monster array
   public final static int MAX_CARDS = 50;

   //private data members
   private Card[] myCards;
   private int numCards;

   public Hand()
   {
      /*
       * Default Constructor;
       */
      this.numCards = 0;
      this.myCards = new Card[MAX_CARDS];
   }

   public void resetHand()
   {
      /*
       * remove all cards from the hand (in the simplest way).
       */

      //Remove all reference to the old hand.
      this.myCards = new Card[MAX_CARDS];

      //Reset the card counter.
      this.numCards = 0;
   }

   public boolean takeCard(Card card)
   {
      /*
       * adds a card to the next available position in the myCards array. This
       * is an object copy, not a reference copy, since the source of the Card
       * might destroy or change its data after our Hand gets it -- we want
       * our local data to be exactly as it was when we received it.
       */

      //Make sure we're not above our hand size limit.
      if (numCards < MAX_CARDS)
      {
         //Create a copy of the taken card and advance the card counter.
         myCards[numCards++] = new Card(card.getValue(), card.getSuit());
         return true;
      }
      else
      {
         return false;
      }
   }

   public Card playCard()
   {
      /*
       * returns and removes the card in the top occupied position of the
       * array.
       */
      if (numCards > 0)
      {
         //Make a copy of the card in the myCards array.
         Card playedCard = new Card(myCards[numCards - 1].getValue(), myCards[numCards - 1].getSuit());

         //Decrement card counter. Remove the topmost card from the array.
         myCards[--numCards] = null;
         return playedCard;
      }
      else
      {
         //Returns an invalid card to be consistent with inspectCard()
         return new Card('Q', Card.Suit.hearts, true);
      }
   }

   public String toString()
   {
      /*
       * a stringizer that the client can use prior to displaying the entire
       * hand
       */
      String output = "( ";
      for (int i = 0; i < numCards; i++)
      {
         //print the card.
         output += this.myCards[i].toString();

         //add a comma to every card except the last.
         if (i + 1 < numCards)
         {
            output += ", ";
         }
      }
      return output + " )";
   }

   public int getNumCards()
   {
      /*
       * Accessor for numCards
       */
      return this.numCards;
   }

   public Card inspectCard(int k)
   {
      /*
       * Accessor for an individual card. Returns a card with errorFlag = true
       * if k is bad.
       * 
       * Valid k: 0 <= k < numCards
       */

      // Returns card if k is valid
      if (0 <= k && k < numCards)
      {
         return new Card(myCards[k].getValue(),myCards[k].getSuit(),myCards[k].getErrorFlag());
      }
      else
      {
         // Returns invalid card if k is bad
         return new Card('Q', Card.Suit.hearts, true);
      }
   }
   
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
}

class Deck
{
   /*
    * DECK UPDATES REQUIRED
    * Jokers need to be added to MasterPack
    * boolean addCard(Card card) //make sure that there are not too many instances of the card in the deck if you add it.  Return false if there will be too many.  It should put the card on the top of the deck.
    * boolean removeCard(Card card) // you are looking to remove a specific card from the deck.  Put the current top card into its place.  Be sure the card you need is actually still in the deck, if not return false.
    * void sort() //cards.arraySort()
    * getNumcards() //returns topCard
    */

   //Sets maximum number of cards to be played which is 6 decks (6 * 56 = 336)
   public final static int MAX_CARDS = 336;

   //This is a private static Card array containing exactly 56 card
   //references, which point to all the standard cards
   //Avoids repeatedly declaring the same 56 cards as game play continues
   private static Card[] masterPack;

   /*
    * Changes to true as soon as masterPack is created for the first time.
    * Future deck objects will not re-create master pack once set to true.
    */
   private static boolean masterPackCreated = false;

   //private data members
   private Card[] cards;
   private int topCard;
   private int numPacks = 1;

   public Deck(int numPacks)
   {
      //Over loaded deck method so a user can request the number of decks to
      //use.
      init(numPacks);
   }

   public Deck()
   {
      //Calls init to build a deck
      numPacks = 1;
      init(numPacks);
   }

   public void init(int numPacks)
   {
      //Ensures the deck is not more than the maximum size.
      if (numPacks > 6)
      {
         numPacks = 6;
      }
      //Initializes the pointer and cards array based on the requested number
      //of packs.
      topCard = numPacks * 56;
      cards = new Card[topCard];
      //Calls allocateMasterPack to make sure the master pack has been
      //created.
      allocateMasterPack();
      //Uses arraycopy to copy the number of requested packs from masterPack
      //into cards.
      for (int count = 0; numPacks > count; count++)
      {
         System.arraycopy(masterPack, 0, cards, 56 * count, 56);
      }
   }

   public void shuffle()
   {
      int randomNumber;
      Card copy;
      Random generator = new Random();
      //Applies the Fisher-Yates shuffle algorithm to the cards array.
      for (int deckCount = topCard - 1; deckCount > 0; deckCount--)
      {
         randomNumber = generator.nextInt(deckCount + 1);
         copy = cards[randomNumber];
         cards[randomNumber] = cards[deckCount];
         cards[deckCount] = copy;
      }
   }

   public Card dealCard()
   {
      if(topCard < 0 || topCard > numPacks * 56)
      {
         return null;
      }
      else
      {
         //Removes the top card from the deck before reducing top card.
         Card card = cards[topCard - 1];
         cards[topCard - 1] = null;
         topCard--;
         //The top card is returned 
         return card;
      }
   }

   //Accessor to return array index of top card
   //This value also tells us how many total cards are currently in the array
   public int getTopCard()
   {
      return this.topCard;
   }

   //Method to test that the index of the card is legal
   public Card inspectCard(int k)
   {

      if (k >= 0 && k <= topCard)
      {
         return new Card(cards[k].getValue(),cards[k].getSuit(),cards[k].getErrorFlag());
      }
      else
      {
         return new Card('Q', Card.Suit.hearts, true);
      }
   }

   private static void allocateMasterPack()
   {
      //Check if master pack has already been created by previous deck
      //objects
      if (!masterPackCreated)
      {
         //initialize card array
         masterPack = new Card[56];
         //Create all 56 Card objects for master pack
         masterPack[0] = new Card('A', Card.Suit.clubs);
         masterPack[1] = new Card('A', Card.Suit.diamonds);
         masterPack[2] = new Card('A', Card.Suit.hearts);
         masterPack[3] = new Card('A', Card.Suit.spades);
         masterPack[4] = new Card('2', Card.Suit.clubs);
         masterPack[5] = new Card('2', Card.Suit.diamonds);
         masterPack[6] = new Card('2', Card.Suit.hearts);
         masterPack[7] = new Card('2', Card.Suit.spades);
         masterPack[8] = new Card('3', Card.Suit.clubs);
         masterPack[9] = new Card('3', Card.Suit.diamonds);
         masterPack[10] = new Card('3', Card.Suit.hearts);
         masterPack[11] = new Card('3', Card.Suit.spades);
         masterPack[12] = new Card('4', Card.Suit.clubs);
         masterPack[13] = new Card('4', Card.Suit.diamonds);
         masterPack[14] = new Card('4', Card.Suit.hearts);
         masterPack[15] = new Card('4', Card.Suit.spades);
         masterPack[16] = new Card('5', Card.Suit.clubs);
         masterPack[17] = new Card('5', Card.Suit.diamonds);
         masterPack[18] = new Card('5', Card.Suit.hearts);
         masterPack[19] = new Card('5', Card.Suit.spades);
         masterPack[20] = new Card('6', Card.Suit.clubs);
         masterPack[21] = new Card('6', Card.Suit.diamonds);
         masterPack[22] = new Card('6', Card.Suit.hearts);
         masterPack[23] = new Card('6', Card.Suit.spades);
         masterPack[24] = new Card('7', Card.Suit.clubs);
         masterPack[25] = new Card('7', Card.Suit.diamonds);
         masterPack[26] = new Card('7', Card.Suit.hearts);
         masterPack[27] = new Card('7', Card.Suit.spades);
         masterPack[28] = new Card('8', Card.Suit.clubs);
         masterPack[29] = new Card('8', Card.Suit.diamonds);
         masterPack[30] = new Card('8', Card.Suit.hearts);
         masterPack[31] = new Card('8', Card.Suit.spades);
         masterPack[32] = new Card('9', Card.Suit.clubs);
         masterPack[33] = new Card('9', Card.Suit.diamonds);
         masterPack[34] = new Card('9', Card.Suit.hearts);
         masterPack[35] = new Card('9', Card.Suit.spades);
         masterPack[36] = new Card('T', Card.Suit.clubs);
         masterPack[37] = new Card('T', Card.Suit.diamonds);
         masterPack[38] = new Card('T', Card.Suit.hearts);
         masterPack[39] = new Card('T', Card.Suit.spades);
         masterPack[40] = new Card('J', Card.Suit.clubs);
         masterPack[41] = new Card('J', Card.Suit.diamonds);
         masterPack[42] = new Card('J', Card.Suit.hearts);
         masterPack[43] = new Card('J', Card.Suit.spades);
         masterPack[44] = new Card('Q', Card.Suit.clubs);
         masterPack[45] = new Card('Q', Card.Suit.diamonds);
         masterPack[46] = new Card('Q', Card.Suit.hearts);
         masterPack[47] = new Card('Q', Card.Suit.spades);
         masterPack[48] = new Card('K', Card.Suit.clubs);
         masterPack[49] = new Card('K', Card.Suit.diamonds);
         masterPack[50] = new Card('K', Card.Suit.hearts);
         masterPack[51] = new Card('K', Card.Suit.spades);
         masterPack[52] = new Card('X', Card.Suit.clubs);
         masterPack[53] = new Card('X', Card.Suit.diamonds);
         masterPack[54] = new Card('X', Card.Suit.hearts);
         masterPack[55] = new Card('X', Card.Suit.spades);
         /*
          * Set masterPackCreated to true now that master pack has been
          * created once
          */
         masterPackCreated = true;
      }
   }
   
   // boolean addCard(Card card)
   public boolean addCard(Card card)
   {
      int numberOfInstances = 0;
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
            numberOfInstances++;
      }
      
      if(numberOfInstances >= numPacks)
      {
         return false;
      }
      cards[topCard].set(card.getValue(), card.getSuit());
      topCard++;
      return true;
   }
   
   // boolean removeCard(Card card) 
   public boolean removeCard(Card card)
   {
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
         {
            cards[i].set(cards[topCard].getValue(), cards[topCard].getSuit());
            cards[topCard] = null;
            topCard--;
            return true;
         }
      }
      return false;
   }

   public void sort()
   {
      Card.arraySort(cards, topCard);
   }
   
   public int getNumCards()
   {
      return topCard;
   }  
}