package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.GameRepository
import com.example.madlevel4task2.models.Action
import com.example.madlevel4task2.models.Game
import com.example.madlevel4task2.models.GameResult
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        gameRepository = GameRepository(requireContext())
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().toolbar.title = getString(R.string.app_name)
        menu.findItem(R.id.action_delete_history).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Setup the main activity view
    private fun initViews() {

        imageBtnRock.setOnClickListener {
            startGame(Action.ROCK)
        }

        imageBtnPaper.setOnClickListener {
            startGame(Action.PAPER)
        }

        imageBtnScissors.setOnClickListener {
            startGame(Action.SCISSORS)
        }
    }

    /**
     * Starts a game between the computer and player
     */
    private fun startGame(playerAction: Action) {
        // Get a random computer action
        val computerAction = getRandomComputerAction()

        showGameView(playerAction, computerAction)

        // Show the result message
        val result = determineResult(playerAction, computerAction)

        when (result) {
            GameResult.COMPUTER_WON -> txt_result.text = getString(R.string.lose_message)
            GameResult.DRAW -> txt_result.text = getString(R.string.draw_message)
            GameResult.PLAYER_WON -> txt_result.text = getString(R.string.win_message)
        }

        txt_result.visibility = View.VISIBLE

        saveGame(result, computerAction, playerAction)
    }

    // Saves the game into the Room database using a coroutine background thread
    private fun saveGame(result: GameResult, computerAction: Action, playerAction: Action) {

        mainScope.launch {
            val game = Game(
                null, computerAction, playerAction, result, date = Date()
            )

            // Insert the game into the database
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

        // Returns the result of the game (Player wins, draw or computer wins)
        private fun determineResult(playerAction: Action, computerAction: Action): GameResult {

            // if player wins
            if (playerAction == Action.ROCK && computerAction == Action.SCISSORS
                || playerAction == Action.SCISSORS && computerAction == Action.PAPER
                || playerAction == Action.PAPER && computerAction == Action.ROCK
            ) {
                return GameResult.PLAYER_WON
                // else if draw
            } else if (playerAction == Action.ROCK && computerAction == Action.ROCK
                || playerAction == Action.PAPER && computerAction == Action.PAPER
                || playerAction == Action.SCISSORS && computerAction == Action.SCISSORS
            ) {
                return GameResult.DRAW
                // else computer wins
            } else {
                return GameResult.COMPUTER_WON
            }
        }

        // Sets up the view after the player starts a game
        private fun showGameView(playerAction: Action, computerAction: Action) {

            // Set the image view of the computer action
            when (computerAction) {
                Action.ROCK -> ivComputer.setImageResource(R.drawable.rock)
                Action.PAPER -> ivComputer.setImageResource(R.drawable.paper)
                else -> ivComputer.setImageResource(R.drawable.scissors)
            }

            // Set the image view of the player action
            when (playerAction) {
                Action.ROCK -> ivPlayer.setImageResource(R.drawable.rock)
                Action.PAPER -> ivPlayer.setImageResource(R.drawable.paper)
                else -> ivPlayer.setImageResource(R.drawable.scissors)
            }

            // Show all the needed elements on the view that needs to be shown after the user starts a game
            tvComputer.text = getString(R.string.player_computer)
            tvPlayer.text = getString(R.string.player)
            txt_V_S.visibility = View.VISIBLE
            ivComputer.visibility = View.VISIBLE
            ivPlayer.visibility = View.VISIBLE
        }

        // Generates a random action for the computer
        private fun getRandomComputerAction(): Action {
            val actions = Action.values()
            val randomIndex = (actions.indices).random()

            return actions[randomIndex]
        }
    }