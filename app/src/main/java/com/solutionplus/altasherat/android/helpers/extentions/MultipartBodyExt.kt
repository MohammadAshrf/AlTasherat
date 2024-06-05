package com.solutionplus.altasherat.android.helpers.extentions

import android.webkit.MimeTypeMap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

enum class FileMediaType(val mediaType: MediaType) {
    FILE("multipart/form-data".toMediaType()),
    IMAGE("image/*".toMediaType()),
    TEXT("text/plain".toMediaType());

    companion object {
        fun findIsImageOrFile(file: File): FileMediaType {
            val extension = file.path.substringAfterLast(".", "")
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

            // Regular expression to match image MIME types
            val imageRegex = Regex("^image/.*$")

            // Check if the MIME type starts with "image/" or if the extension is commonly used for images. If it does, it's an image. Otherwise, it's a file.
            return if (mimeType != null && mimeType.matches(imageRegex))
                IMAGE
            else
                FILE
        }
    }

}

fun List<File>.getFileMediaListAsPart(fileKey: String): ArrayList<MultipartBody.Part> {
    val parts: ArrayList<MultipartBody.Part> = ArrayList()
    forEachIndexed { index, file ->
        parts.add(get(index).getFileMediaAsPart(fileKey.plus("[".plus(file).plus("]"))))
    }
    return parts
}

fun File.getFileMediaAsPart(fileKey: String): MultipartBody.Part {
    val body: RequestBody = asRequestBody(FileMediaType.findIsImageOrFile(this).mediaType)
    return MultipartBody.Part.createFormData(fileKey, name, body)
}

fun String.toRequestBody(): RequestBody = toRequestBody(FileMediaType.TEXT.mediaType)