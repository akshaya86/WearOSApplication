package com.example.domain.base

import com.example.domain.model.Result

interface IBaseUseCase<T : Any, R: Any> {
  suspend operator fun invoke(param: T): Result<R>
}