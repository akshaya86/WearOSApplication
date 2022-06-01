package com.example.data.repository

import com.example.data.coroutine.CoroutineContextProvider
import com.example.data.network.base.DomainMapper
import com.example.domain.model.Result
import com.example.domain.model.Success
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> : KoinComponent {
  private val contextProvider: CoroutineContextProvider by inject()
  
  /**
   * Use this if you need to cache data after fetching it from the db,
   * or retrieve something from cache
   */
  protected suspend fun fetchData(
      dbDataProvider: suspend () -> R
  ): Result<T> {
    return withContext(contextProvider.io) {
      val dbResult = dbDataProvider()
      Success(dbResult.mapToDomainModel())
    }
  }
}