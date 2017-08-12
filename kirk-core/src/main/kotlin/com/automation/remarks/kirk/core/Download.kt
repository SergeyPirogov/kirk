package com.automation.remarks.kirk.core

import com.automation.remarks.kirk.KElement
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * Created by ArtyomAnohin on 12.08.17.
 */

fun downloadFile(element: KElement) {
    val path: String
    val destination: Path
    try {
        path = element.attr("href")
    } catch (e: IllegalStateException) {
        throw NotImplementedError("Download in beta mode.\n Possible to download file only from href param.")
    }
    val filename = Paths.get(path).fileName.toString()
    if (configuration.outputPath().isBlank())
        destination = Paths.get(System.getProperty("user.dir") +
                File.separator + "build" + File.separator + "download", filename)
    else
        destination = Paths.get(configuration.outputPath(), filename)
    try {

        URL(path).openStream().use({ target ->
            Files.createDirectories(destination.parent)
            Files.copy(target, destination, StandardCopyOption.REPLACE_EXISTING)
        })
    } catch (e: FileNotFoundException) {
        throw FileNotFoundException("Destination file not found!\n" + "href=$path\n" + element.toString())
    }
    println("Download file: $destination")
}
