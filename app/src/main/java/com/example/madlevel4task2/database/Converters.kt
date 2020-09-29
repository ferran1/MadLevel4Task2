package com.example.madlevel4task2.database

import java.util.*
import androidx.room.TypeConverter
import com.example.madlevel4task2.models.GameResult
import com.example.madlevel4task2.models.Action

class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }


    @TypeConverter
    fun fromIndex(value: Int?): Action? {
        return value?.let {
            when (it) {
                1 -> Action.ROCK
                2 -> Action.PAPER
                else -> Action.SCISSORS
            }
        }
    }

    @TypeConverter
    fun actionToInt(action: Action?): Int? {
        return action?.let {
            when (it) {
                Action.ROCK -> 1
                Action.PAPER -> 2
                else -> 0
            }
        }
    }

    @TypeConverter
    fun fromResultInt(value: Int?): GameResult? {
        return value?.let {
            when (it) {
                1 -> GameResult.PLAYER_WON
                -1 -> GameResult.COMPUTER_WON
                else -> GameResult.DRAW
            }
        }
    }

    @TypeConverter
    fun resultToResultInt(action: GameResult?): Int? {
        return action?.let {
            when (it) {
                GameResult.PLAYER_WON -> 1
                GameResult.COMPUTER_WON -> -1
                else -> 0
            }
        }
    }
}