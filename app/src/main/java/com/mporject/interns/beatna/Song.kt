package com.mporject.interns.beatna

class Song {
    private var title: String? = null


    fun gettitle(): String? {
        return title
    }

    fun settitle(title: String) {
        this.title = title
    }

    fun Song(title: String) {
        this.title = title
    }
}