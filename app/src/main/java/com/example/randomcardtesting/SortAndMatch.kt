package com.example.randomcardtesting

import android.util.Log
import kotlin.math.abs

interface SortAndMatch {
    var player: ArrayList<ArrayList<Int>>
    var tempDeck: ArrayList<ArrayList<Int>>
    var score: Double

    fun sortByTypeAndCardAscending() {
        /**
         * Sort by types and card no. in ascending order - For display purpose
         * Variables inside this function
         * 2D Array : player
         * Int : swapA, swapB
         */
        for (i in 0..(player.size) - 2) {
            for (j in 0..(player.size) - 2) {
                if (player[j][0] > player[j + 1][0] ||
                    (player[j][0] == player[j + 1][0] && player[j][1] > player[j + 1][1])) {
                    var swapA = player[j][0];
                    var swapB = player[j][1]
                    player[j][0] = player[j + 1][0]; player[j][1] = player[j + 1][1]
                    player[j + 1][0] = swapA; player[j + 1][1] = swapB
                }
            }
        }
    }

    fun sortByTypeAndCard() {
        /**
         * Sort by types and card no. in descending order - For Straight Flush & Flush
         * Variables inside this function
         * 2D Array : player
         * Int : swapA, swapB
         */
        for (i in 0..(player.size) - 2) {
            for (j in 0..(player.size) - 2) {
                if (player[j][0] < player[j + 1][0] ||
                    (player[j][0] == player[j + 1][0] && player[j][1] < player[j + 1][1])) {
                    var swapA = player[j][0];
                    var swapB = player[j][1]
                    player[j][0] = player[j + 1][0]; player[j][1] = player[j + 1][1]
                    player[j + 1][0] = swapA; player[j + 1][1] = swapB
                }
            }
        }
    }

    fun checkStraightFlush() {
        /**
         * Check Straight Flush
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         */

        for (i in 0..player.size - 5) {
            if (player[i][1] == 12) { // Check for A..2..3..4..5
                if ((player[i][0] == player[i + 1][0]) && player[i][1] == 11 ) {
                    if ((player[i + 1][0] == player[i + 2][0]) && player[i + 1][1] == 10) {
                        if ((player[i + 2][0] == player[i + 3][0]) && player[i + 2][1] == 9 ) {
                            if ((player[i + 3][0] == player[i + 4][0]) && player[i + 3][1] == 8) {
                                tempDeck.add(player[i])
                                tempDeck.add(player[i + 1])
                                tempDeck.add(player[i + 2])
                                tempDeck.add(player[i + 3])
                                tempDeck.add(player[i + 4])
                                score = 5.0
                                break
                            }
                        }
                    }
                }
                if (score == 0.0) {
                    for (j in i+1..player.size - 4) {
                        if ((player[j][0] == player[i][0]) && (player[j][1] == 3)) {
                            if ((player[j + 1][0] == player[i][0]) && (player[j + 1][1] == 2)) {
                                if ((player[j + 2][0] == player[i][0]) && (player[j + 2][1] == 1)) {
                                    if ((player[j + 3][0] == player[i][0]) && (player[j + 3][1] == 0)) {
                                        tempDeck.add(player[i])
                                        tempDeck.add(player[j + 3])
                                        tempDeck.add(player[j + 2])
                                        tempDeck.add(player[j + 1])
                                        tempDeck.add(player[j])
                                        score = 5.0
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (score == 0.0) { // Check other Straight Flush combination
                if ((player[i][0] == player[i + 1][0]) && (player[i][1] == player[i + 1][1] + 1)) {
                    if ((player[i + 1][0] == player[i + 2][0]) && (player[i + 1][1] == player[i + 2][1] + 1)) {
                        if ((player[i + 2][0] == player[i + 3][0]) && (player[i + 2][1] == player[i + 3][1] + 1)) {
                            if ((player[i + 3][0] == player[i + 4][0]) && (player[i + 3][1] == player[i + 4][1] + 1)) {
                                tempDeck.add(player[i])
                                tempDeck.add(player[i + 1])
                                tempDeck.add(player[i + 2])
                                tempDeck.add(player[i + 3])
                                tempDeck.add(player[i + 4])
                                score = 5.0
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    fun sortByCard() {
        /**
         * Sort card no. in descending order - For 4 of a kind, Full house, 3 of a kind, 2 or 1 pair(s)
         * Variables inside this function
         * 2D Array : player
         * Int : swapA, swapB
         */
        for (i in 0..(player.size) - 2) {
            for (j in 0..(player.size) - 2) {
                if (player[j][1] < player[j + 1][1]) {
                    val swapA = player[j][0];
                    val swapB = player[j][1]
                    player[j][0] = player[j + 1][0]
                    player[j][1] = player[j + 1][1]
                    player[j + 1][0] = swapA
                    player[j + 1][1] = swapB
                }
            }
        }
    }

    fun checkFourKind(){
        /**
         * Check Four of a kind
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         */
        for (i in 0..player.size - 4) {
            if (player[i][1] == player[i + 1][1]) {
                if (player[i + 1][1] == player[i + 2][1]) {
                    if (player[i + 2][1] == player[i + 3][1]) {
                        tempDeck.add(player[i])
                        tempDeck.add(player[i + 1])
                        tempDeck.add(player[i + 2])
                        tempDeck.add(player[i + 3])
                        if (i == 9) { //Add a temporary card for 4 of a kind
                            tempDeck.add(player[0])
                        } else {
                            tempDeck.add(player[i + 4])
                        }
                        score = 4.0
                        break
                    }
                }
            }
        }
    }

    fun checkFullHouse() {
        /**
         * Check Full House - 3 of a kind
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         */
        for (i in 0..player.size - 3) {
            if (player[i][1] == player[i + 1][1]) {
                if (player[i + 1][1] == player[i + 2][1]) {
                    tempDeck.add(player[i])
                    tempDeck.add(player[i + 1])
                    tempDeck.add(player[i + 2])
                    break
                }
            }
        }
        if (tempDeck.size == 3) { // Check for additional pair for Full House
            for (i in 0..player.size - 2) {
                if (player[i][1] != tempDeck[0][1]) {
                    if (player[i][1] == player[i + 1][1]) {
                        tempDeck.add(player[i])
                        tempDeck.add(player[i + 1])
                        break
                    }
                }
            }
        }
        if (tempDeck.size != 5) { // Clear if pair is not found
            tempDeck.clear()
        } else {
            score = 2.0
        }
    }

    fun checkFlush() {
        /**
         * Check Flush
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         */
        for (i in 0..player.size - 5) {
            if (player[i][0] == player[i + 1][0]) {
                if (player[i + 1][0] == player[i + 2][0]) {
                    if (player[i + 2][0] == player[i + 3][0]) {
                        if (player[i + 3][0] == player[i + 4][0]) {
                            tempDeck.add(player[i])
                            tempDeck.add(player[i + 1])
                            tempDeck.add(player[i + 2])
                            tempDeck.add(player[i + 3])
                            tempDeck.add(player[i + 4])
                            score = 1.5
                            break
                        }
                    }
                }
            }
        }
    }

    fun sortByCardStraight() {
        /**
         * Sort by card no. in descending order - For Straight only. Card no. will be converted to negative when same number is found
         * Variables inside this function
         * 2D Array : player
         * Int : swapA, swapB
         */
        for (i in 0..(player.size) - 2) {
            for (j in 0..(player.size) - 2) {
                if (player[j][1] < player[j + 1][1]) {
                    val swapA = player[j][0];
                    val swapB = player[j][1]
                    player[j][0] = player[j + 1][0]
                    player[j][1] = player[j + 1][1]
                    player[j + 1][0] = swapA
                    player[j + 1][1] = swapB
                } else if (player[j][1] == player[j + 1][1] && player[j][1] > -1) {
                    player[j][1] = player[j][1] * -1
                }
            }
        }
    }

    fun checkStraight() {
        /**
         * Check Straight
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         */
        for (i in 0..player.size - 5) {
            if (player[i][1] == 12 && score == 0.0) {
                if (player[1][i+1] == 11 && player[i + 2][1] == 10) {
                    if (player[i + 3][1] == 9 && player[i + 4][1] == 8) {
                        tempDeck.add(player[i])
                        tempDeck.add(player[i + 1])
                        tempDeck.add(player[i + 2])
                        tempDeck.add(player[i + 3])
                        tempDeck.add(player[i + 4])
                        score = 1.4
                        break
                    }
                }
                for (j in i + 1..player.size - 4) {
                    if (player[j][1] == 3 && player[j + 1][1] == 2) { // Check for A..2..3..4..5
                        if (player[j + 2][1] == 1 && player[j + 3][1] == 0) {
                            tempDeck.add(player[i])
                            tempDeck.add(player[j + 3])
                            tempDeck.add(player[j + 2])
                            tempDeck.add(player[j + 1])
                            tempDeck.add(player[j])
                            score = 1.4
                            break
                        }
                    }
                }
            }
            if (score == 0.0) { // Check other Straight combination
                if (player[i][1] == player[i + 1][1] + 1) {
                    if (player[i + 1][1] == player[i + 2][1] + 1) {
                        if (player[i + 2][1] == player[i + 3][1] + 1) {
                            if (player[i + 3][1] == player[i + 4][1] + 1) {
                                tempDeck.add(player[i])
                                tempDeck.add(player[i + 1])
                                tempDeck.add(player[i + 2])
                                tempDeck.add(player[i + 3])
                                tempDeck.add(player[i + 4])
                                score = 1.4
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    fun sortByCardStraightReverse() {
        /**
         * Reverse negative to positive
         * Variables inside this function
         * 2D Array : player
         */
        for (i in 0..(player.size) - 1) {
            if (player[i][1] < 0) {
                player[i][1] = abs(player[i][1])
            }
        }
    }

    fun checkThreeKind() {
        /**
         * Check for 3 of a kind
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         * Int : drawTwoCards
         */
        for (i in 0..player.size - 3) {
            if (player[i][1] == player[i + 1][1] && player[i + 1][1] == player[i + 2][1]) {
                tempDeck.add(player[i])
                tempDeck.add(player[i + 1])
                tempDeck.add(player[i + 2])
                break
            }
        }
        if (tempDeck.size == 3) { // Add 2 largest cards for 3 of a kind
            var drawTwoCards = 0
            for (i in 0..player.size - 1) { //Updated on 14/10/2021 *Original -3*
                if (player[i][1] != tempDeck[0][1] && drawTwoCards < 2) {
                    tempDeck.add(player[i])
                    drawTwoCards++
                }
            }
            score = 1.3
        }
    }

    fun checkPairs() {
        /**
         * Check for pairs
         * Variables inside this function
         * 2D Array : player, tempDeck
         * Double : score
         * Int : drawThreeCards
         */
        for (i in 0..player.size - 2) {
            if (player[i][1] == player[i + 1][1]) {
                tempDeck.add(player[i])
                tempDeck.add(player[i + 1])
                if (tempDeck.size == 4) {
                    break
                }
            }
        }
        if (tempDeck.size == 4) { // Add largest card to 2 pairs
            score = 1.2
            for (i in 0..player.size - 1) { //Updated on 14/10/2021 *Original -3*
                if (tempDeck[0][1] != player[i][1] && tempDeck[2][1] != player[i][1]) {
                        tempDeck.add(player[i])
                        break
                }
            }
        }
        if (tempDeck.size == 2) { // If only 1 pair is found, add 3 largest cards to the set
            score = 1.1
            var drawThreeCards = 0
            for (i in 0..player.size - 1) {
                if (player[i][1] != tempDeck[1][1]) {
                    tempDeck.add(player[i])
                    drawThreeCards++
                    if (drawThreeCards == 3) {
                        break
                    }
                }
            }
        }
        if (score == 0.0) { // If no set or pair is found, put 5 largest cards as a set
            score = 1.0
            for (i in 0..4) {
                tempDeck.add(player[i])
            }
        }
    }

    fun checkScoreTopSet () : Double {
        /**
         * Define score for set 3 - 3 of a kind, a pair or nothing.
         * Variables inside this function
         * 2D Array : player
         * Double : score
         */
        if (player[0][1] == player[1][1] && player[1][1] == player[2][1]) {
            score = 3.0
        } else if (player[0][1] == player[1][1]) {
            score = 1.1
        } else if ( player[1][1] == player[2][1]) {
            score = 1.1
            val swap = player[0]
            player[0] = player[2]
            player[2] = swap
        } else if (player[2][1] == player[0][1]) {
            score = 1.1
            val swap = player[1]
            player[1] = player[2]
            player[2] = swap
        } else {
            score = 1.0
        }
        return score
    }

    fun checkSetOrder (playerResult1 : ArrayList<ArrayList<Int>>, playerResult2 : ArrayList<ArrayList<Int>>)
        : Pair<ArrayList<ArrayList<Int>>,ArrayList<ArrayList<Int>>> {
        /**
         * Check and make sure set 1 is larger than set 2
         * Variables inside this function
         * 2D Array : playerResult(1 ~ 2), swapArray
         * Boolean : needSwap
         */
        var needSwap = false
        for (i in 0..4) {
            if (playerResult2[1][i] > playerResult1[1][i]) {
                needSwap = true
                break
            } else if (playerResult2[1][i] < playerResult1[1][i]) {
                needSwap = false
                break
            }
        }
        if (needSwap == true) {
            val swapArray = ArrayList<ArrayList<Int>>()
            for (i in 0..4) {
                swapArray.add(playerResult1[i])
            }
            playerResult1.clear()
            for (i in 0..4) {
                playerResult1.add(playerResult2[i])
            }
            playerResult2.clear()
            for (i in 0..4) {
                playerResult2.add(swapArray[i])
            }
        }
    return Pair(playerResult1, playerResult2)
    }
}
