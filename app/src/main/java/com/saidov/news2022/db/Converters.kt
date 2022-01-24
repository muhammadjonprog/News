package com.saidov.news2022.db

import androidx.room.TypeConverter
import com.saidov.news2022.model.Source


class Converters {

    //Convert Source to String
    @TypeConverter
    fun fromSource(source: Source): String{
    return source.name
    }


    //Convert string to Source
    @TypeConverter
    fun toSource(name: String): Source{
    return Source(name, name)
    }
}