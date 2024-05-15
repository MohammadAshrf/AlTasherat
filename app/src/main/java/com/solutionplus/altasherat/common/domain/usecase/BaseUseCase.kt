package com.solutionplus.altasherat.common.domain.usecase

import com.solutionplus.altasherat.common.data.mapper.ExceptionMapper
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/** crossinline keyword is used in function types to
 *  specify that a lambda passed as a parameter to the function
 *  cannot have non-local returns.**/
abstract class BaseUseCase {
    suspend inline fun <T> execute(
        scope: CoroutineScope,
        crossinline call: suspend () -> T,
        crossinline onResult: (Resource<T>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            onResult.invoke(Resource.Loading())
            try {
                val result = withContext(Dispatchers.IO) {
                    call()
                }
                onResult.invoke(Resource.Success(result))

            } catch (e: Exception) {
                val mappedException = ExceptionMapper.map(e)
                onResult.invoke(Resource.Error(mappedException))
            }
        }
    }
}

