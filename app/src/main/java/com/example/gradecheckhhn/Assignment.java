package com.example.gradecheckhhn;

import androidx.room.Entity;
import java.util.*

@Entity
data class Assignment (var category: String = "",
                       var pointsEarned: Double = 0.0,
                       var maximumPoints: Double = 0.0)

