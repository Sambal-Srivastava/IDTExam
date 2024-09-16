package com.app.idtexam.data.model

import com.google.gson.annotations.SerializedName

object MockResponseDto {

    data class MockResponse(
        @SerializedName("data") var data: List<MockData>? = null
    )

    data class MockData(
        @SerializedName("state") var state: String? = null,
        @SerializedName("population") var population: Long? = 0,
        @SerializedName("counties") var counties: Int? = 0
    )
}
