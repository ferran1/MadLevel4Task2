package com.example.madlevel4task2.database

import android.content.Context
import com.example.madlevel4task2.models.Game

class GameRepository(context: Context) {

    private val gameDAO: GameDAO

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDAO = database!!.gameDAO()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDAO.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDAO.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDAO.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDAO.deleteAllGames()
    }

}
