package com.example.a9lab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class User(
    val username: String,
    val email: String,
    val karma: Int = 0,
    val joinDate: Long = System.currentTimeMillis()
)

class BlogViewModel : ViewModel() {
    private val _blogPosts = MutableLiveData<List<BlogPost>>()
    val blogPosts: LiveData<List<BlogPost>> get() = _blogPosts

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    init {
        _blogPosts.value = listOf(
            BlogPost(
                title = "Welcome to BlogIt",
                content = "This is the first post on BlogIt!",
                author = "admin",
                subreddit = "announcements",
                upvotes = 42,
                commentCount = 5
            ),
            BlogPost(
                title = "How to use BlogIt",
                content = "Upvote posts you like and comment on posts to join the discussion.",
                author = "Pranab_Rai",
                subreddit = "help",
                upvotes = 21,
                commentCount = 3
            )
        )
    }

    fun addBlogPost(title: String, content: String, subreddit: String) {
        val currentPosts = _blogPosts.value ?: emptyList()
        val newPost = BlogPost(
            title = title,
            content = content,
            subreddit = subreddit,
            author = _currentUser.value?.username ?: "anonymous"
        )
        _blogPosts.value = currentPosts + newPost
    }

    fun registerUser(username: String, email: String) {
        val newUser = User(username = username, email = email)
        _currentUser.value = newUser
        _isLoggedIn.value = true
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    fun upvotePost(post: BlogPost) {
        val currentPosts = _blogPosts.value ?: return
        val updatedPosts = currentPosts.map {
            if (it == post) it.copy(upvotes = it.upvotes + 1) else it
        }
        _blogPosts.value = updatedPosts
    }
}