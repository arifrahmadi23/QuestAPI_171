package com.example.pertemuan13.repository

import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.service.MahasiswaService
import okio.IOException

interface MahasiswaRepository {
    suspend fun getMahasiswa(): List<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(nim: String)

    suspend fun getMahasiswaByNim(nim: String): Mahasiswa
}

class NetworkMahasiswaRepository(
    private val mahasiswaApiService: MahasiswaService
) : MahasiswaRepository{
    override suspend fun getMahasiswa(): List<Mahasiswa> =
        mahasiswaApiService.getAllMahasiswa()


    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        mahasiswaApiService.updateMahasiswa(nim, mahasiswa)
    }

    override suspend fun deleteMahasiswa(nim: String) {
        try{
            val response = mahasiswaApiService.deleteMahasiswa(nim)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Mahasiswa. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getMahasiswaByNim(nim: String): Mahasiswa {
        return mahasiswaApiService.getMahasiswabyNim(nim)
    }
}
