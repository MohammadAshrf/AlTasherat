package com.solutionplus.altasherat.common.domain.models.request

import java.io.File

data class RemoteRequest(
    val requestBody: HashMap<String, Any>,
    val requestBodyFiles: HashMap<String, List<File>>
)