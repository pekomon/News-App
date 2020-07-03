package com.example.pekomon.newsapp.data.typeconverters

import androidx.room.TypeConverter
import com.example.pekomon.newsapp.data.model.Source

class SourceConverter {

    // Id is not needed so just let it got
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    // Id is not needed here neither so just generate something for it
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}