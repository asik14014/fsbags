package com.fourseasonsweb.fsbags.data

class Product {
    private var id: Int = 0
    private var name: String = ""
    private var description: String = ""
    private var image: String = ""

    fun getId() : Int {
        return id
    }

    fun setId(value: Int) {
        id = value
    }

    fun getName() : String {
        return name
    }

    fun setName(value: String) {
        name = value
    }

    fun getDescription() : String {
        return description
    }

    fun setDescription(value: String) {
        description = value
    }
}