package com.example.madlevel4task2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game_table")
data class Game (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "computer_action")
    val computerAction: Action,

    @ColumnInfo(name = "player_action")
    val playerAction: Action,

    @ColumnInfo(name = "result")
    var result: GameResult,

    @ColumnInfo(name = "date")
    var date: Date,
)