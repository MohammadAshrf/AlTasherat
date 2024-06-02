package com.solutionplus.altasherat.features.language.domain.worker

import android.content.Context
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesUC
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class LanguageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getCountries: GetCountriesUC
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val deferredResult = CompletableDeferred<Result>()

        return@withContext with(deferredResult) {
            try {
                val language = inputData.getString(LANGUAGE) ?: return@withContext Result.failure()

                getCountries.invoke(language).collect {
                    when (it) {
                        is Resource.Loading -> {
                        }

                        is Resource.Success -> {
                            val successMessage = SUCCESS_MESSAGE
                            val outputData = workDataOf(KEY_SUCCESS to successMessage)
                            complete(Result.success(outputData))
                        }

                        is Resource.Failure -> {
                            launch(Dispatchers.Main) {
                                Toast.makeText(
                                    applicationContext,
                                    "Please check your internet connection!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            val errorMessage = ERROR_MESSAGE
                            val outputData = workDataOf(KEY_ERROR to errorMessage)
                            complete(Result.failure(outputData))
                        }
                    }
                }

                await()
            } catch (ex: LeonException) {
                val exceptionMessage = ERROR_EXCEPTION_MESSAGE
                val outputData = workDataOf(KEY_ERROR to exceptionMessage)
                complete(Result.failure(outputData))
                await()
            }
        }
    }

    companion object {
        const val LANGUAGE = "language"
        const val WORK_NAME = "language_worker"
        const val KEY_SUCCESS = "success"
        const val KEY_ERROR = "error"
        const val SUCCESS_MESSAGE = "Country list updated"
        const val ERROR_MESSAGE = "Failed to update country list from remote api"
        const val ERROR_EXCEPTION_MESSAGE =
            "An error occurred while updating country list from remote api"
    }
}