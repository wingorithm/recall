package wingoritm.mobile.recall

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform