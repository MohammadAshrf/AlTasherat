package com.solutionplus.altasherat.common.domain.interactor

import com.solutionplus.altasherat.common.data.mapper.ExceptionMapper
import com.solutionplus.altasherat.common.data.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseUseCase<out Model, in Params> {
    protected abstract suspend fun execute(params: Params?): Model
    operator fun invoke(
        scope: CoroutineScope,
        params: Params? = null,
        onResult: (Resource<Model>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            onResult.invoke(Resource.loading())
            try {
                withContext(Dispatchers.IO) {
                    val result = execute(params)
                    onResult.invoke(Resource.success(result))
                }
                withContext(Dispatchers.Main) {
                    onResult.invoke(Resource.loading(false))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.IO) {
                    val mappedException = ExceptionMapper.map(e)
                    onResult.invoke(Resource.failure(mappedException))
                }

                withContext(Dispatchers.Main) {
                    onResult.invoke(Resource.loading(false))
                }
            } catch (e: HttpException){
                withContext(Dispatchers.IO) {
                    val mappedException = ExceptionMapper.map(e)
                    onResult.invoke(Resource.failure(mappedException))
                }

                withContext(Dispatchers.Main) {
                    onResult.invoke(Resource.loading(false))
                }
            }
        }
    }

    operator fun invoke(params: Params? = null): Flow<Resource<Model>> = channelFlow {
        send(Resource.loading())
        try {
            val result = execute(params)
            send(Resource.success(result))
            send(Resource.loading(false))
        } catch (e: HttpException) {
            val mappedException = ExceptionMapper.map(e)
            send(Resource.failure(mappedException))
            send(Resource.loading(false))
        } catch (e: Exception) {
            val mappedException = ExceptionMapper.map(e)
            send(Resource.failure(mappedException))
            send(Resource.loading(false))
        }
    }.flowOn(Dispatchers.IO)

}

