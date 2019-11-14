package CrazyEights;
import java.util.ArrayList;
import java.util.Collections;

// This class represents the player's hand, and does 
// all actions related to the player's hand
public class Hand 
{
    private ArrayList<Card> hand;   // The cards in the hand.
    private int playerNum;

    /**
     * Create a hand that is initially empty.
     */
    public Hand(int playerNum) 
    {
        hand = new ArrayList<Card>();
        this.playerNum = playerNum;
    }

    /**
     * Remove all cards from the hand, leaving it empty.
     */
    public void clear() 
    {
        hand.clear();
    }

    /**
     * Add a card to the hand.  It is added at the end of the current hand.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void addCard(Card c)
    {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        hand.add(c);
    }

    /**
     * Remove a card from the hand, if present.
     * @param c the card to be removed.  If c is null or if the card is not in 
     * the hand, then nothing is done.
     */
    public void removeCard(Card c) 
    {
        hand.remove(c);
    }

    /**
     * Remove the card in a specified position from the hand.
     * @param position the position of the card that is to be removed, where
     * positions are starting from zero.
     * @throws IllegalArgumentException if the position does not exist in
     * the hand, that is if the position is less than 0 or greater than
     * or equal to the number of cards in the hand.
     */
    public void removeCard(int position) 
    {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        hand.remove(position);
    }

    /**
     * Returns the number of cards in the hand.
     */
    public int getCardCount() 
    {
        return hand.size();
    }

    /**
     * Gets the card in a specified position in the hand.  (Note that this card
     * is not removed from the hand!)
     * @param position the position of the card that is to be returned
     * @throws IllegalArgumentException if position does not exist in the hand
     */
    public Card getCard(int position) 
    {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return hand.get(position);
    }

    /**
     * Sorts the cards in the hand so that cards of the same suit are
     * grouped together, and within a suit the cards are sorted by value.
     * Note that aces are considered to have the lowest value, 1. --- sorting is similar to "selection sort"
     */
    public void sortBySuit() 
    {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if ( c1.getSuit() < c.getSuit() ||
                        (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
        //Collections.sort(list, c);
    }

    /**
     * Sorts the cards in the hand so that cards of the same value are
     * grouped together.  Cards with the same value are sorted by suit.
     * Note that aces are considered to have the lowest value, 1.
     */
    public void sortByValue() 
    {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) 
            {
                Card c1 = hand.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) 
                {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }
    
    /**
	 * refactoring: 
	 * removed the location part of the method and moved it into its own method
	 * 
	 * <p>
	 * Level of cohesion: 
	 *  Procedural Cohesion
	 *
	 * @Pre:  There is a card to compare to on the discard pile
	 * @post: there is either a card to play, or not
	*/
    public Card playCard(Card c)
    {
    	int cardIndex = findCardByRank(c);
    	if(cardIndex >= 0)
    	{
    		Card c1 = hand.get(cardIndex);
        	removeCard(cardIndex);
        	return c1;
    	}	
    	else
    	{
    		cardIndex = findCardBySuit(c);
    		if(cardIndex >= 0)
    		{
    			Card c1 = hand.get(cardIndex);
    			removeCard(cardIndex);
    			return c1;
    		}
    		return null;
    	}
    }
    
    /**
	 * refactoring: 
	 * Changed the method name in order to allow for better understanding
	 * removed the location part of the method and moved it into its own method
	 * <p>
	 * Level of cohesion: 
	 *  Procedural Cohesion
	 *
	 * @Pre:  The previous card was the same rank as the previous top card
	 * @post: The card returned also has the same rank
	*/
    public Card playSameRankCard(Card c)
    {
    	int cardIndex = findCardByRank(c);
    	if(cardIndex >= 0)
    	{
    		Card c1 = hand.get(cardIndex);
        	removeCard(cardIndex);
        	return c1;
    	}
    	
    	return null;
    }
    
    public int getPlayerNum()
    {
    	return playerNum;
    }
    
    private int findCardByRank(Card c)
    {
    	for(int i = 0; i < hand.size(); i++)
    	{
    		if(hand.get(i).getValue() == c.getValue() || hand.get(i).getValue() == 8)
    		{
    			return i;	
    		}
    	}
    	
    	return -1;
    }
    
    private int findCardBySuit(Card c)
    {
    	for(int i = 0; i < hand.size(); i++)
    	{
    		if(hand.get(i).getSuit() == (c.getSuit()))
    		{
    			return i;
    		}
    	}
    	return -1;
    }
}//end of class Hand
