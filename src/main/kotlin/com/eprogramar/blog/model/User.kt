package com.eprogramar.blog.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var name: String = "",
        var email: String = "",
        var password: String = ""
)