package com.example.amphibiansapp.data

import com.example.amphibiansapp.model.Amphibian
import com.example.amphibiansapp.network.AmphibiansApiService



interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}




class NetworkAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
) : com.example.amphibiansapp.data.AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}