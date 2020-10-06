package com.example.madlevel4task2.ui

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.GameRepository
import com.example.madlevel4task2.models.Game
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        getGamesFromDatabase()
        gameRepository = GameRepository(requireContext())
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Change toolbar title when the fragment switches to history fragment
        requireActivity().toolbar.title = "Your Game History"
        menu.findItem(R.id.action_go_to_history).isVisible = false
        menu.findItem(R.id.action_delete_history).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Used to perform actions on the menu buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_history -> {
                deleteGameHistory()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        rvGames.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter

    }

    private fun getGamesFromDatabase() {
        // Get the products from the database using the product repository and add them to the products array list
        mainScope.launch {
            val games = withContext(Dispatchers.IO) { // IO dispatcher because we're using database operations
                gameRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            this@HistoryFragment.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGameHistory(){
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }

        Toast.makeText(context, getString(R.string.successfully_deleted_msg), Toast.LENGTH_SHORT).show()
    }


}