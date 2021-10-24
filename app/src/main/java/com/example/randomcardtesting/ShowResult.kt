package com.example.randomcardtesting

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.randomcardtesting.databinding.ShowResultBinding

/*
 * Activity to show players score, total point and cards
 * Objects used inside this activity
 * Global variables :-
 * Array<Int> : PlayerUI.cardFile
 * IntArray : PlayerUI.playersTotal
 * Function variables :-
 * Array<ImageView> : player(1~4)Card
 * Array<TextView> : playerTotalResult, playerScoreResult
 * IntArray : player(1~4)SetCard, playersPoint (Intent from PlayerUI)
 */

class ShowResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val showHand = ShowResultBinding.inflate(layoutInflater)
        setContentView(showHand.root)

        /*
         * Get arrays from PlayerUI
         */
        val extras = intent.extras ?: return
        val player1SetCard = extras.getIntArray("player1SetCard")
        val player2SetCard = extras.getIntArray("player2SetCard")
        val player3SetCard = extras.getIntArray("player3SetCard")
        val player4SetCard = extras.getIntArray("player4SetCard")
        val playersPoint = extras.getIntArray("playersPoint")

        /*
         * To put card image to binding object
         */
        fun setImage(imageView: ImageView, drawable: Int?) {
            imageView.setImageResource(drawable!!)
        }

        /*
         * Setup array for ImageViews & TextViews in show_result.xml
         */
        val player1Card : Array<ImageView> = arrayOf(showHand.p1Set001, showHand.p1Set002,
            showHand.p1Set003, showHand.p1Set004, showHand.p1Set005, showHand.p1Set006,
            showHand.p1Set007, showHand.p1Set008, showHand.p1Set009, showHand.p1Set010,
            showHand.p1Set011, showHand.p1Set012, showHand.p1Set013
        )
        val player2Card : Array<ImageView> = arrayOf(showHand.p2Set001, showHand.p2Set002,
            showHand.p2Set003, showHand.p2Set004, showHand.p2Set005, showHand.p2Set006,
            showHand.p2Set007, showHand.p2Set008, showHand.p2Set009, showHand.p2Set010,
            showHand.p2Set011, showHand.p2Set012, showHand.p2Set013
        )
        val player3Card : Array<ImageView> = arrayOf(showHand.p3Set001, showHand.p3Set002,
            showHand.p3Set003, showHand.p3Set004, showHand.p3Set005, showHand.p3Set006,
            showHand.p3Set007, showHand.p3Set008, showHand.p3Set009, showHand.p3Set010,
            showHand.p3Set011, showHand.p3Set012, showHand.p3Set013
        )
        val player4Card : Array<ImageView> = arrayOf(showHand.p4Set001, showHand.p4Set002,
            showHand.p4Set003, showHand.p4Set004, showHand.p4Set005, showHand.p4Set006,
            showHand.p4Set007, showHand.p4Set008, showHand.p4Set009, showHand.p4Set010,
            showHand.p4Set011, showHand.p4Set012, showHand.p4Set013
        )
        val playersTotalResult : Array<TextView> = arrayOf(showHand.player1Total,
            showHand.player2Total, showHand.player3Total, showHand.player4Total
        )
        val playersScoreResult : Array<TextView> = arrayOf(showHand.player1Score,
            showHand.player2Score, showHand.player3Score, showHand.player4Score
        )

        /*
         * Show total scores and set color to RED if total score is negative
         */
        for (i in 0..3) {
            playersTotalResult[i].setTextColor(Color.parseColor("#FF000000"))
            if (PlayerUI.playersTotal[i] < 0) playersTotalResult[i].setTextColor(Color.parseColor("#FFFF0000"))
            playersTotalResult[i].text = PlayerUI.playersTotal[i].toString()

            playersScoreResult[i].setTextColor(Color.parseColor("#FF000000"))
            if (playersPoint!![i] < 0) playersScoreResult[i].setTextColor(Color.parseColor("#FFFF0000"))
            playersScoreResult[i].text = playersPoint[i].toString()
        }
        for (i in 0..12) {
            setImage (player1Card[i], PlayerUI.cardFile[player1SetCard!![i]])
            setImage (player2Card[i], PlayerUI.cardFile[player2SetCard!![i]])
            setImage (player3Card[i], PlayerUI.cardFile[player3SetCard!![i]])
            setImage (player4Card[i], PlayerUI.cardFile[player4SetCard!![i]])
        }

        /*
         * Onclick listener for button "Back"
         */
        showHand.backToMain.setOnClickListener {
            val goBack = Intent(this,PlayerUI::class.java)
            startActivity(goBack)
        }
    }
}