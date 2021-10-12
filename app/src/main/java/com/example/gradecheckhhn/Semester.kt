package com.example.gradecheckhhn

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Semester (@PrimaryKey val id: UUID = UUID.randomUUID(),
                     var season: String = "",
                     var year: String = "")
