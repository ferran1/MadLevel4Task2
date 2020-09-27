package com.example.madlevel4task2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.models.Game
import com.example.madlevel4task2.models.GameResult

@Dao
interface GameDAO {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(result) FROM game_table WHERE result = :gameResult")
    suspend fun countResult(gameResult: GameResult): Int

}