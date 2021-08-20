package com.github.showitemlist.model



class Item {

     var name:String?=null

     var description:String? =null

    constructor(name: String?, description: String?) {

        this.name = name?:""
        this.description = description?:""
    }


}