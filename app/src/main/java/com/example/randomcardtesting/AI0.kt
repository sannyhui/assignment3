package com.example.randomcardtesting

class AI0 (override var player: ArrayList<ArrayList<Int>> ) : SortAndMatch {

    override var tempDeck = arrayListOf(ArrayList<Int>())
    override var score: Double = 0.0

    fun compareCard () : Pair <ArrayList<ArrayList<Int>>, Double> {
        // Flow of sorting, and set cards
        tempDeck.clear()

        // Check for Straight Flush
        sortByTypeAndCard()
        checkStraightFlush()

        // Check for 4 of a kind
        if (score == 0.0) {
            sortByCard()
            checkFourKind()
        }

        // Check for Full House
        if (score == 0.0) {
            sortByCard()
            checkFullHouse()
        }

        // Check for Flush
        if (score == 0.0) {
            sortByTypeAndCard()
            checkFlush()
        }

        // Check for Straight
        if (score == 0.0) {
            sortByCardStraight()
            checkStraight()
            sortByCardStraightReverse()
        }

        // Check for 3 of a kind
        if (score == 0.0) {
            sortByCard()
            checkThreeKind()
        }

        // Check for pairs or nothing
        if (score == 0.0) {
            sortByCard()
            checkPairs()
        }

        return Pair(tempDeck, score)
    }
}
