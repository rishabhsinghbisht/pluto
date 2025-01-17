package com.pluto.utilities.share

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.pluto.utilities.DebugLog
import java.io.File
import java.io.FileOutputStream

fun Context.share(message: String, title: String? = null, subject: String? = null) {
    val intent = Intent().apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
        putExtra(Intent.EXTRA_TEXT, message)
        action = Intent.ACTION_SEND
    }
    startActivity(Intent.createChooser(intent, title ?: "Share via..."))
}

fun Context.shareFile(message: String, title: String? = null, fileName: String, contentType: String) {
    val dir = getDirectoryName()
    val file = generateFile(message, dir, contentType)
    val uri = FileProvider.getUriForFile(applicationContext, "pluto___${applicationContext.packageName}.provider", file)
    val intent = Intent().apply {
        type = contentType

        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        putExtra(Intent.EXTRA_TEXT, fileName)
        putExtra(Intent.EXTRA_STREAM, uri)
        action = Intent.ACTION_SEND
    }
    startActivity(Intent.createChooser(intent, title ?: "Share via..."))
}

fun Context.copyToClipboard(data: String, label: String) {
    val clipboard: ClipboardManager? = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(label, data)
    clipboard?.setPrimaryClip(clip)
}

private fun Context.getDirectoryName(): File {
    val dir = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "PlutoShares")
    if (!dir.exists()) {
        if (dir.mkdir()) {
            DebugLog.d("Create Directory", "Directory Created : $dir")
        }
    }
    return dir
}

@Suppress("TooGenericExceptionCaught")
private fun generateFile(content: String, saveFilePath: File, contentType: String): File {
    val dir = File(saveFilePath.absolutePath)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(saveFilePath.absolutePath, "pluto_share.${getFileExtension(contentType)}")
    try {
        val fOut = FileOutputStream(file)
        fOut.write(content.toByteArray())
        fOut.flush()
        fOut.close()
    } catch (e: Exception) {
        DebugLog.e("share", "error while generating file", e)
    }
    return file
}

private fun getFileExtension(contentType: String): String = when (contentType) {
    "text/csv" -> "csv"
    else -> "txt"
}
