package com.example.randomcardtesting

class RandomAndCalculation {

    val deck = ArrayList<Int>()
    init {
        for (i in 0..51) {
            deck.add(i)
        }
    }

    fun generatePlayerDeck () : ArrayList<ArrayList<Int>> {
        /**
         * Draw 13 cards for player
         * Variables inside this function
         * 2D Array : player
         * IntArray : deck
         * Int : ranNum, ranCard
         */
        //lateinit var player: ArrayList<ArrayList<Int>>
        var player = arrayListOf(arrayListOf(0))
        player.clear()

        for (i in 0..12) {
            val ranNum = (0..deck.size - 1).random()
            val ranCard = deck[ranNum]
            deck.removeAt(ranNum)
            when (ranCard) {
                in 0..12 -> player.add(arrayListOf(0, ranCard))
                in 13..25 -> player.add(arrayListOf(1, ranCard - 13))
                in 26..38 -> player.add(arrayListOf(2, ranCard - 26))
                in 39..51 -> player.add(arrayListOf(3, ranCard - 39))
            }
        }
        return player
    }

    fun compareScore (player1Card : ArrayList<ArrayList<Int>>, player2Card : ArrayList<ArrayList<Int>>,
        player1Score : DoubleArray, player2Score : DoubleArray) : Pair<Int, Int> {
        /**
         * Compare score between players
         * Variables inside this function
         * 2D Array : player1Card, player2Card
         * DoubleArray : player1Score, player2Score
         * Int : player1Point, player2Point, player1Win
         * Boolen : naturalsRoyalties
         */
        var player1Point = 0; var player2Point = 0
        var naturalsRoyalties = false

        // Check for Clean Dragon for player 1
        // Score[0] & Score[1] equal to 5.0, Card[0] is "A", Card[5] is "9"
        if (player1Score[0] == 5.0 && player1Score[1] == 5.0 &&
            player1Card[0][1] == 12 && player1Card[5][1] == 7) {
            // Check Card[10],[11],[12] are cards "4","3","2"
            if (player1Card[10][1] == 2 && player1Card[11][1] == 1 && player1Card[12][1] == 0) {
                // Check Card[10],[11],[12] are Flush too
                if (player1Card[10][0] == player1Card[11][0]  &&
                    player1Card[11][0] == player1Card[12][0]) {
                    // Check for Card[0], Card[5], Card[10] are same type of card.
                    if (player1Card[0][0] == player1Card[5][0] && player1Card[5][0] == player1Card[10][0]) {
                        player1Point += 169; player2Point -= 169
                        naturalsRoyalties = true
                    }
                }
            }
        // Check for full Straight & 3 Straights for player 1
        } else if (player1Score[0] == 1.4 && player1Score[1] == 1.4) {
            if (player1Card[10][1] == player1Card[11][1] -1 &&
                player1Card[11][1] == player1Card[12][1] -1) {
                // Pattern : card[0] = A, card[5] = 9, card[10] = 4 for full Straight
                if (player1Card[0][1] == 12 && player1Card[5][1] == 7 && player1Card[10][1] == 2) {
                    player1Point += 13; player2Point -= 13
                } else {
                    // 3 Straights
                    player1Point += 3; player2Point -= 3
                }
                naturalsRoyalties = true
            }
        // Check for 3 Flush for player 1
        } else if (player1Score[0] == 1.5 && player1Score[1] == 1.5) {
            if (player1Card[10][0] == player1Card[11][0] &&
                player1Card[11][0] == player1Card[12][0]) {
                player1Point += 3; player2Point -= 3
                naturalsRoyalties = true
            }
        // Check for 6 pairs for player 1
        } else if (player1Score[0] == 1.2 && player1Score[1] == 1.2 && player1Score[2] == 1.1) {
            if (player1Card[4][1] == player1Card[9][1] || player1Card[4][1] == player1Card[12][1] ||
                player1Card[9][1] == player1Card[12][1]) {
                player1Point += 3; player2Point -= 3
                naturalsRoyalties = true
            }
        // Check for Full house + 3 pairs for player 1
        } else if (player1Score[0] == 2.0 && player1Score[1] == 1.2 && player1Score[2] == 1.1) {
            if (player1Card[9][1] == player1Card[12][1]) {
                player1Point += 3; player2Point -= 3
                naturalsRoyalties = true
            }
        }

        // Check for Clean Dragon for player 2
        // Score[0] & Score[1] equal to 5.0, Card[0] is "A", Card[5] is "9"
        if (player2Score[0] == 5.0 && player2Score[1] == 5.0 &&
            player2Card[0][1] == 12 && player2Card[5][1] == 7) {
            // Check Card[10],[11],[12] are cards "4","3","2"
            if (player2Card[10][1] == 2 && player2Card[11][1] == 1 && player2Card[12][1] == 0) {
                // Check Card[10],[11],[12] are Flush too
                if (player2Card[10][0] == player2Card[11][0] &&
                    player2Card[11][0] == player2Card[12][0]
                ) {
                    // Check for Card[0], Card[5], Card[10] are same type of card.
                    if (player2Card[0][0] == player2Card[5][0] && player2Card[5][0] == player2Card[10][0]) {
                        player2Point += 169; player1Point -= 169
                        naturalsRoyalties = true
                    }
                }
            }
            // Check for full Straight & 3 Straights for player 2
        } else if (player2Score[0] == 1.4 && player2Score[1] == 1.4) {
            if (player2Card[10][1] == player2Card[11][1] -1 &&
                player2Card[11][1] == player2Card[12][1] -1) {
                if (player2Card[0][1] == 12 && player2Card[5][1] == 7 && player2Card[10][1] == 2) {
                    player2Point += 13;player1Point -= 13
                } else {
                    player2Point += 3; player1Point -= 3
                }
                naturalsRoyalties = true
            }
        // Check for 3 Flush for player 2
        } else if (player2Score[0] == 1.5 && player2Score[1] == 1.5) {
            if (player2Card[10][0] == player2Card[11][0] &&
                player2Card[11][0] == player2Card[12][0]) {
                player2Point += 3; player1Point -= 3
                naturalsRoyalties = true
            }
        // Check for 6 pairs for player 1
        } else if (player2Score[0] == 1.2 && player2Score[1] == 1.2 && player2Score[2] == 1.1) {
            if (player2Card[4][1] == player2Card[9][1] || player2Card[4][1] == player2Card[12][1] ||
                player2Card[9][1] == player2Card[12][1]) {
                player2Point += 3; player1Point -= 3
                naturalsRoyalties = true
            }
        // Check for Full house + 3 pairs for player 1
        } else if (player2Score[0] == 2.0 && player2Score[1] == 1.2 && player2Score[2] == 1.1) {
            if (player2Card[9][1] == player2Card[12][1]) {
                player2Point += 3; player1Point -= 3
                naturalsRoyalties = true
            }
        }

        // Compare score and cards between 2 players
        if (naturalsRoyalties == false) {
            for (i in 0..2) {
                var player1Win = 0
                if (player1Score[i] > player2Score[i]) {
                    player1Win = 1
                } else if (player2Score[i] > player1Score[i]) {
                    player1Win = -1
                } else {
                    if (i < 2) {
                        for (j in (0 + i * 5)..(4 + i * 5)) {
                            if (player1Card[j][1] > player2Card[j][1]) {
                                player1Win = 1
                                break
                            } else if (player2Card[j][1] > player1Card[j][1]) {
                                player1Win = -1
                                break
                            }
                        }
                    } else {
                        for (j in 10..12) {
                            if (player1Card[j][1] > player2Card[j][1]) {
                                player1Win = 1
                                break
                            } else if (player2Card[j][1] > player1Card[j][1]) {
                                player1Win = -1
                                break
                            }
                        }
                    }
                }
                if (player1Win == 1) {
                    if (i == 0 && player1Score[0] == 2.0) {
                        player1Point++; player2Point--
                    } else if (i == 1 && player1Score[1] > 3) {
                        player1Point += player1Score[i].toInt() * 2; player2Point -= player1Score[i].toInt() * 2
                    } else if (i == 2 && player1Score[2] == 1.3) {
                        player1Point += 3; player2Point -= 3
                    } else {
                        player1Point += player1Score[i].toInt(); player2Point -= player1Score[i].toInt()
                    }
                } else if (player1Win == -1) {
                    if (i == 0 && player2Score[i] == 2.0) {
                        player1Point--; player2Point++
                    } else if (i == 1 && player2Score[1] > 3) {
                        player2Point += player2Score[i].toInt() * 2; player1Point -= player2Score[i].toInt() * 2
                    } else if (i == 2 && player2Score[2] == 1.3) {
                        player2Point += 3; player1Point -= 3
                    } else {
                        player2Point += player2Score[i].toInt(); player1Point -= player2Score[i].toInt()
                    }
                }

            }
        }
        return Pair(player1Point, player2Point)
    }
}