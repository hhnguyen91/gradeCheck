package com.example.gradecheckhhn.database

import androidx.room.TypeConverter
import java.util.*

class DBTypeConverters {

    //Converts a string from the database back to a UUID
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    //Converts UUID to string to be stored into the database
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}