package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.models.Action
import com.example.madlevel4task2.models.Game
import com.example.madlevel4task2.models.GameResult
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(

    private val games: List<Game>

) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {

            // Get and set the result text
            val result = when (game.result) {
                GameResult.COMPUTER_WON -> "Computer wins!"
                GameResult.PLAYER_WON -> "You win!"
                else -> "Draw"
            }
            itemView.txt_result.text = result

            // Get and set the computer image
            val computerActionImg = when (game.computerAction) {
                Action.PAPER -> R.drawable.paper
                Action.SCISSORS -> R.drawable.scissors
                else -> R.drawable.rock
            }
            itemView.ivComputer.setImageResource(computerActionImg)

            // Get and set the player action image
            val playerActionImg = when (game.playerAction) {
                Action.PAPER -> R.drawable.paper
                Action.SCISSORS -> R.drawable.scissors
                else -> R.drawable.rock
            }
            itemView.ivPlayer.setImageResource(playerActionImg)

            // Set the date
            itemView.tvDate.text = game.date.toString()
        }
    }

}