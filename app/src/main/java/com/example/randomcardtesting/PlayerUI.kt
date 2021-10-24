package com.example.randomcardtesting

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.randomcardtesting.databinding.PlayerUiBinding

/*
 * Activity to display UI for player to set cards
 * Objects used inside the class
 * Global variables :-
 * Array<Int> : PlayerUI.cardFile
 * IntArray : PlayerUI.playersTotal
 * Class variable :-
 * Array<ImageView> : cardNumber, imageView
 * Array<TextView> : playersTotalResult
 * IntArray : playersPoint, player(1~4)SetCard, player1DrawCard
 * DoubleArray : player(1~4)Score
 * ArrayList<ArrayList<Int>> : player(1~4), player1Result1_(1~2), player(1~4)Result(1~2)
 * Int : swapCard, drawable
 * Double : player(1~4)Score(1~3)
 * Float : alpha
 */

class PlayerUI : AppCompatActivity() {

    /*
     * Singleton variables for project
     */
    companion object {
        val playersTotal = intArrayOf(0,0,0,0)
        val cardFile = arrayOf(
            R.drawable.card_0, R.drawable.card_1, R.drawable.card_2, R.drawable.card_3,
            R.drawable.card_4, R.drawable.card_5, R.drawable.card_6, R.drawable.card_7,
            R.drawable.card_8, R.drawable.card_9, R.drawable.card_10, R.drawable.card_11,
            R.drawable.card_12, R.drawable.card_13, R.drawable.card_14, R.drawable.card_15,
            R.drawable.card_16, R.drawable.card_17, R.drawable.card_18, R.drawable.card_19,
            R.drawable.card_20, R.drawable.card_21, R.drawable.card_22, R.drawable.card_23,
            R.drawable.card_24, R.drawable.card_25, R.drawable.card_26, R.drawable.card_27,
            R.drawable.card_28, R.drawable.card_29, R.drawable.card_30, R.drawable.card_31,
            R.drawable.card_32, R.drawable.card_33, R.drawable.card_34, R.drawable.card_35,
            R.drawable.card_36, R.drawable.card_37, R.drawable.card_38, R.drawable.card_39,
            R.drawable.card_40, R.drawable.card_41, R.drawable.card_42, R.drawable.card_43,
            R.drawable.card_44, R.drawable.card_45, R.drawable.card_46, R.drawable.card_47,
            R.drawable.card_48, R.drawable.card_49, R.drawable.card_50, R.drawable.card_51,
        )
    }

    /*
     * Program starting point
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerMain = PlayerUiBinding.inflate(layoutInflater)
        setContentView(playerMain.root)

        /*
         * Array setup for ImageViews & TextViews in player_ui.xml
         */
        val cardNumber : Array<ImageView> = arrayOf(playerMain.playerCard001, playerMain.playerCard002,
            playerMain.playerCard003, playerMain.playerCard004, playerMain.playerCard005,
            playerMain.playerCard006, playerMain.playerCard007, playerMain.playerCard008,
            playerMain.playerCard009, playerMain.playerCard010, playerMain.playerCard011,
            playerMain.playerCard012, playerMain.playerCard013
        )
        val playersTotalResult : Array<TextView> = arrayOf(playerMain.player1TotalResult,
            playerMain.player2TotalResult, playerMain.player3TotalResult,
            playerMain.player4TotalResult
        )

        /*
         * Declare score point (Int) and card set for display
         */
        var swapCard = -1
        val playersPoint = intArrayOf(0,0,0,0)
        val player1SetCard = IntArray(13); val player2SetCard = IntArray(13)
        val player3SetCard = IntArray(13); val player4SetCard = IntArray(13)
        val player1DrawCard = IntArray(13)

        /*
         * Function to show card or change alpha
         */
        fun setImage(imageView: ImageView, drawable: Int?, alpha: Float?) {
            if (drawable != null) {
                imageView.setImageResource(drawable)
            } else if (alpha != null) {
                imageView.setAlpha(alpha)
            }
        }

        /*
         * Generate Decks for players
         */
        val generateDeck = RandomAndCalculation()
        val player1 = generateDeck.generatePlayerDeck()
        val player2 = generateDeck.generatePlayerDeck()
        val player3 = generateDeck.generatePlayerDeck()
        val player4 = generateDeck.generatePlayerDeck()

        /*
         * Set player 1 AI level with basic sorting
         */
        var player1AILevel = AI0(player1)
        player1AILevel.sortByTypeAndCardAscending()

        /*
         * Show initial cards for player 1 (from player1 -> drawCard1)
         */
        for (i in player1.indices) {
            player1DrawCard[i] = player1[i][0] * 13 + player1[i][1]
            setImage(cardNumber[i], cardFile[player1DrawCard[i]], null)
        }

        /*
         * Show total scores and set color to RED if total score is negative
         */
        for (i in 0..3) {
            playersTotalResult[i].setTextColor(Color.parseColor("#FF000000"))
            if (playersTotal[i] < 0) playersTotalResult[i].setTextColor(Color.parseColor("#FFFF0000"))
            playersTotalResult[i].text = playersTotal[i].toString()
        }

        /*
         * Function to swap card
         */
        fun swapCards(cardNo: Int) {
            if (swapCard != cardNo) {
                val cardSwapping = player1[cardNo]
                player1[cardNo] = player1[swapCard]
                player1[swapCard] = cardSwapping
                val drawCardSwapping = player1DrawCard[cardNo]
                player1DrawCard[cardNo] = player1DrawCard[swapCard]
                player1DrawCard[swapCard] = drawCardSwapping
            }
        }

        /*
         * Function to change alpha of selected card
         */
        fun cardOnClick (showCardNo : Int) {
            if (swapCard == -1) {
                swapCard = showCardNo
                setImage(cardNumber[showCardNo], null, 0.5f)
            } else {
                swapCards(showCardNo)
                setImage(cardNumber[showCardNo], cardFile[player1DrawCard[showCardNo]], null)
                setImage(cardNumber[swapCard], cardFile[player1DrawCard[swapCard]], null)
                setImage(cardNumber[swapCard], null, 1.0f)
                swapCard = -1
            }
        }

        /*
         * Function to setup OnClick Listener
         */
        fun setupOnClick (imageView: ImageView, cardNo: Int) {
            imageView.setOnClickListener{cardOnClick(cardNo)}
        }

        /*
         * Function to set cards for player 2~4, compare score and pass variables to showResult Activity.
         */
        fun startMatch() {

            /*
             * Player 2 ~ 4 AI level & sorting
             */
            var player2AILevel = AI0(player2)
            var player3AILevel = AI0(player3)
            var player4AILevel = AI0(player4)

            /*
             * Declare necessary arrays for player 1
             */
            val player1Score = DoubleArray(3)
            val player2Score = DoubleArray(3)
            val player3Score = DoubleArray(3)
            val player4Score = DoubleArray(3)
            var player1Result1_1 = arrayListOf(arrayListOf(0))
            var player1Result1_2 = arrayListOf(arrayListOf(0))
            player1Result1_1.clear()
            player1Result1_2.clear()

            /*
             * Put first 5 cards (set 1) for player 1 to calculate point
             */
            for (i in 0 until 5) {
                   player1Result1_1.add(player1[i])
            }
            player1AILevel = AI0(player1Result1_1)
            val (player1Result1, player1Score1) = player1AILevel.compareCard() // player 1
            player1Score[0] = player1Score1
            player1.removeAll(player1Result1)

            /*
             * Put next 5 cards (set 2) for player 1 to calculate point
             */
            for (i in 0 until 5) {
                player1Result1_2.add(player1[i])
            }
            player1AILevel = AI0(player1Result1_2)
            val (player1Result2, player1Score2) = player1AILevel.compareCard() // player 1
            player1Score[1] = player1Score2
            player1.removeAll(player1Result2)

            /*
             * Put last 3 cards (set 3) for player 1 to calculate double point
             */
            player1AILevel = AI0(player1)
            player1AILevel.sortByCard()
            //val player1Score3 = player1AILevel.checkScoreTopSet()
            player1Score[2] = player1AILevel.checkScoreTopSet()

            /*
             * Check and for set in case set 3 is larger than set 2 or set 2 is larger than set 1 (player 1 only)
             * Probably it is not neccessary to do this step. Do checking and score -3 if order is wrong.
             */
            var breakRule = false
            if (player1Score[2] > player1Score[1] || player1Score[1] > player1Score[0]) {
                breakRule = true
            }
            if (player1Score[0] == player1Score[1]) {
                for (i in 0..4) {
                    if (player1Result2[1][i] > player1Result1[1][i]) {
                        breakRule = true
                        break
                    } else if (player1Result2[1][i] < player1Result1[1][i]) {
                        breakRule = false
                        break
                    }
                }
            }
            if (player1Score[2] == player1Score[1]) {
                for (i in 0..2) {
                    if (player1[1][i] > player1Result2[1][i]) {
                        breakRule = true
                        break
                    } else if (player1[1][i] < player1Result2[1][i]) {
                        breakRule = false
                        break
                    }
                }
            }
            if (breakRule == true) {
                for (i in 0..2) {
                    player1Score[i]=0.0
                }
            }

            /*
             * Put 13 cards to get set 1 of player 2 and calculate double point
             */
            val (player2Result1, player2Score1) = player2AILevel.compareCard() // player 2
            player2Score[0] = player2Score1
            player2.removeAll(player2Result1)

            /*
             * Put 8 cards to get set 2 of player 2 and calculate double point
             */
            player2AILevel = AI0(player2)
            val (player2Result2, player2Score2) = player2AILevel.compareCard()
            player2Score[1] = player2Score2
            player2.removeAll(player2Result2)

            /*
             * Put 3 cards to calculate double point of player 2 set 3
             */
            player2AILevel = AI0(player2)
            player2AILevel.sortByCard()
            player2Score[2] = player2AILevel.checkScoreTopSet()

            /*
             * Put 13 cards to get set 1 of player 3 and calculate double point
             */
            val (player3Result1, player3Score1) = player3AILevel.compareCard() // player 3
            player3Score[0] = player3Score1
            player3.removeAll(player3Result1)

            /*
             * Put 8 cards to get set 2 of player 3 and calculate double point
             */
            player3AILevel = AI0(player3)
            val (player3Result2, player3Score2) = player3AILevel.compareCard()
            player3Score[1] = player3Score2
            player3.removeAll(player3Result2)

            /*
             * Put 3 cards to calculate double point of player 3 set 3
             */
            player3AILevel = AI0(player3)
            player3AILevel.sortByCard()
            player3Score[2] = player3AILevel.checkScoreTopSet()

            /*
             * Put 13 cards to get set 1 of player 4 and calculate double point
             */
            val (player4Result1, player4Score1) = player4AILevel.compareCard() // player 4
            player4Score[0] = player4Score1
            player4.removeAll(player4Result1)

            /*
             * Put 8 cards to get set 2 of player 4 and calculate double point
             */
            player4AILevel = AI0(player4)
            val (player4Result2, player4Score2) = player4AILevel.compareCard()
            player4Score[1] = player4Score2
            player4.removeAll(player4Result2)

            /*
             * Put 3 cards to calculate double point of player 4 set 3
             */
            player4AILevel = AI0(player4)
            player4AILevel.sortByCard()
            player4Score[2] = player4AILevel.checkScoreTopSet()

            /*
             * Check player 2 ~ 4 order of Set 1 & 2 if points (double) are same
             */
            if (player2Score[0] == player2Score[1]) {
                val (player2Result1, player2Result2) = player2AILevel.checkSetOrder(
                    player2Result1, player2Result2
                )
            }
            if (player3Score[0] == player3Score[1]) {
                val (player3Result1, player3Result2) = player3AILevel.checkSetOrder(
                    player3Result1, player3Result2
                )
            }
            if (player4Score[0] == player4Score[1]) {
                val (player4Result1, player4Result2) = player4AILevel.checkSetOrder(
                    player4Result1, player4Result2
                )
            }

            /*
             * Calculate points between players and re-form player 1 ~ 4
             */
            player1.addAll(0, player1Result1); player1.addAll(5, player1Result2)
            player2.addAll(0, player2Result1); player2.addAll(5, player2Result2)
            player3.addAll(0, player3Result1); player3.addAll(5, player3Result2)
            player4.addAll(0, player4Result1); player4.addAll(5, player4Result2)

            val (player1_2, player2_1) = generateDeck.compareScore(
                player1, player2, player1Score, player2Score
            )
            val (player1_3, player3_1) = generateDeck.compareScore(
                player1, player3, player1Score, player3Score
            )
            val (player1_4, player4_1) = generateDeck.compareScore(
                player1, player4, player1Score, player4Score
            )
            val (player2_3, player3_2) = generateDeck.compareScore(
                player2, player3, player2Score, player3Score
            )
            val (player2_4, player4_2) = generateDeck.compareScore(
                player2, player4, player2Score, player4Score
            )
            val (player3_4, player4_3) = generateDeck.compareScore(
                player3, player4, player3Score, player4Score
            )
            playersPoint[0] += player1_2 + player1_3 + player1_4
            playersPoint[1] += player2_1 + player2_3 + player2_4
            playersPoint[2] += player3_1 + player3_2 + player3_4
            playersPoint[3] += player4_1 + player4_2 + player4_3
            for (i in 0..3) {
                playersTotal[i] += playersPoint[i]
            }
        }

        /*
         * Setup OnClickListeners for card swapping
         */
        for (i in 0..12) setupOnClick(cardNumber[i],i)

        /*
         * Setup OnClickListener to start comparison & intent variable to ShowResult Activity
         */
        playerMain.cardSet.setOnClickListener {
            startMatch()
            for (i in 0 until 13) {
                player1SetCard[i] = player1[i][0] * 13 + player1[i][1]
                player2SetCard[i] = player2[i][0] * 13 + player2[i][1]
                player3SetCard[i] = player3[i][0] * 13 + player3[i][1]
                player4SetCard[i] = player4[i][0] * 13 + player4[i][1]
            }
            val toShowResult = Intent(this, ShowResult::class.java)
            toShowResult.putExtra("player1SetCard",player1SetCard)
            toShowResult.putExtra("player2SetCard",player2SetCard)
            toShowResult.putExtra("player3SetCard",player3SetCard)
            toShowResult.putExtra("player4SetCard",player4SetCard)
            toShowResult.putExtra("playersPoint",playersPoint)
            startActivity(toShowResult)
        }
    }
}
