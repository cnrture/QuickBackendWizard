package com.github.cnrture.quickbackendwizard.common.file

interface File {
    val name: String
    val absolutePath: String
    val isDirectory: Boolean
    val children: List<File>
    val hasChildren: Boolean
}