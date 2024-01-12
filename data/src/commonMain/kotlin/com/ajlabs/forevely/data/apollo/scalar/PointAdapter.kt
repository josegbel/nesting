package com.ajlabs.forevely.data.apollo.scalar

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.AnyAdapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter

class Point(val latitude: Double, val longitude: Double)

val pointAdapter = object : Adapter<Point> {
    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): Point {
        val map = AnyAdapter.fromJson(reader, customScalarAdapters) as Map<String, Double>
        return Point(map["latitude"] as Double, map["longitude"] as Double)
    }

    override fun toJson(writer: JsonWriter, customScalarAdapters: CustomScalarAdapters, value: Point) {
        val map = mapOf(
            "latitude" to value.latitude,
            "longitude" to value.longitude
        )
        AnyAdapter.toJson(writer, customScalarAdapters, map)
    }
}
