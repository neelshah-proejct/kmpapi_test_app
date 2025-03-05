package org.test.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform