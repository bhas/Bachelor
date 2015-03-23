package game.cards;

public enum Card {
	/*
	 * Ranks:
	 * 0  ->  2
	 * 1  ->  3
	 * 2  ->  4
	 * 3  ->  5
	 * 4  ->  6
	 * 5  ->  7
	 * 6  ->  8
	 * 7  ->  9
	 * 8  ->  10
	 * 9  ->  J
	 * 10 ->  Q
	 * 11 ->  K
	 * 12 ->  A 
	 
	 * Suits:
	 * 0  ->  Diamonds
	 * 1  ->  Clubs
	 * 2  ->  Hearts
	 * 3  ->  Spades
	 */

	D2("2d", 0, 0), D3("3d", 1, 0), D4("4d", 2, 0), D5("5d", 3, 0), D6("6d", 4, 0), D7("7d", 5, 0), D8("8d", 6, 0), D9("9d", 7, 0), D10("10d", 8, 0), DJ("Jd", 9, 0), DQ("Qd", 10, 0), DK("Kd", 11, 0), DA("Ad", 12, 0), 
	C2("2c", 0, 1), C3("3c", 1, 1), C4("4c", 2, 1), C5("5c", 3, 1), C6("6c", 4, 1), C7("7c", 5, 1), C8("8c", 6, 1), C9("9c", 7, 1), C10("10c", 8, 1), CJ("Jc", 9, 1), CQ("Qc", 10, 1), CK("Kc", 11, 1), CA("Ac", 12, 1), 
	H2("2h", 0, 2), H3("3h", 1, 2), H4("4h", 2, 2), H5("5h", 3, 2), H6("6h", 4, 2), H7("7h", 5, 2), H8("8h", 6, 2), H9("9h", 7, 2), H10("10h", 8, 2), HJ("Jh", 9, 2), HQ("Qh", 10, 2), HK("Kh", 11, 2), HA("Ah", 12, 2), 
	S2("2s", 0, 3), S3("3s", 1, 3), S4("4s", 2, 3), S5("5s", 3, 3), S6("6s", 4, 3), S7("7s", 5, 3), S8("8s", 6, 3), S9("9s", 7, 3), S10("10s", 8, 3), SJ("Js", 9, 3), SQ("Qs", 10, 3), SK("Ks", 11, 3), SA("As", 12, 3);

	public static final int NO_OF_RANKS = 13;
	public static final int NO_OF_SUITS = 4;
	public static final int ACE = 12;
	public static final int KING = 11;
	public static final int QUEEN = 10;
	public static final int JACK = 9;
	public static final int TEN = 8;
	public static final int NINE = 7;
	public static final int EIGHT = 6;
	public static final int SEVEN = 5;
	public static final int SIX = 4;
	public static final int FIVE = 3;
	public static final int FOUR = 2;
	public static final int THREE = 1;
	public static final int DEUCE = 0;

	private int rank;
	private int suit;
	private String tag;

	Card(String tag, int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
		this.tag = tag;
	}

	public int getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return tag;
	}
}
