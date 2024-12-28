package com.example.pertemuan13.dependeciesinjection

import com.example.pertemuan13.repository.MahasiswaRepository
import com.example.pertemuan13.repository.NetworkMahasiswaRepository
import com.example.pertemuan13.service.MahasiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer : AppContainer{

    private val baseUrl = "http://10.0.2.2/umyTI/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retroit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val mahasiswaService: MahasiswaService by lazy {
        retroit.create(MahasiswaService::class.java)
    }

    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }

}