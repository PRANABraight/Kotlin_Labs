package com.example.a9lab

data class BlogPost(
    val title: String,
    val content: String,
    val author: String = "anonymous",
    val subreddit: String = "all",
    val upvotes: Int = 0,
    val commentCount: Int = 0,
    val timePosted: Long = System.currentTimeMillis()
)
