package com.pinkal.gallery.model

/**
 * Created by Pinkal Daliya on 13-Jul-17.
 */
data class AlbumsFolder(var folderName: String,
                        var folderPath: String,
                        var fileType: String,
                        var fileSize: String,
                        var fileList: ArrayList<FileList>,
                        var isSelected: Boolean = false)

data class FileList(var filePath: String)
