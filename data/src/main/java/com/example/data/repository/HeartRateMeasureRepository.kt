package com.example.data.repository

import android.os.Environment
import com.example.data.database.dao.HeartDataDao
import com.example.data.datamodels.base.toHeartRateData
import com.example.data.datamodels.base.toHeartRateEntity
import com.example.data.utils.DataConstant.Companion.FOLDER_NAME
import com.example.data.utils.DataConstant.Companion.FOLDER_PATH
import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.util.*

class HeartRateMeasureRepository(private val heartDataDao: HeartDataDao) :
    IHeartRateHistoryRepository, KoinComponent {


    override suspend fun deleteHeartRatedata() {
        heartDataDao.deleteHeartRateData()
    }

    override fun getAllHeartListData(): Flow<List<HeartRateData>> {
        return heartDataDao.retrieveAllHeartRateData().map {
            it.map { data -> data.toHeartRateData() }
        }
    }

    override suspend fun insertHeartRateData(heartRateData: HeartRateData) {
        heartDataDao.insertHeartRateData(heartRateData.toHeartRateEntity())
    }

    override suspend fun insertAllHeartRateData(heartRateData: List<HeartRateData>) {
        heartDataDao.insertAllHeartRateData(heartRateData.map { it.toHeartRateEntity() }) }

    override fun createCSVDataFormat(heartRateData: List<HeartRateData>): Boolean{
        return try {
            val filename = "${FOLDER_NAME + "_" + heartRateData.last().time}.csv"
            val root = Environment.getExternalStorageDirectory()
            val path = File(root.absolutePath + "" + FOLDER_PATH)
            path.mkdirs()

            val file = File(path, filename)
            val filePath = FileWriter(file.absoluteFile)

            val bufferWritter = BufferedWriter(filePath)
            heartRateData.forEach {
                bufferWritter.write(it.convertToCSV())
                bufferWritter.newLine()
            }
           bufferWritter.close()
           true

       }catch (e:Exception){
           e.printStackTrace()
           false
       }
    }

    companion object {
        fun HeartRateData.convertToCSV(): String {
            return "${this.heartRateBpm},${this.time},${this.name}"
        }
    }



}