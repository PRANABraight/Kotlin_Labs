package com.example.a9lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.a9lab.ui.theme._9LabTheme

class MainActivity : ComponentActivity() {
    private val viewModel: BlogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _9LabTheme {
                val isLoggedIn by viewModel.isLoggedIn.observeAsState(false)
                val currentUser by viewModel.currentUser.observeAsState()
                val blogPosts by viewModel.blogPosts.observeAsState(emptyList())
                var selectedItem by remember { mutableStateOf(0) }
                var showAddPost by remember { mutableStateOf(false) }

                Surface(color = MaterialTheme.colorScheme.background) {
                    if (!isLoggedIn) {
                        RegistrationScreen { username, email ->
                            viewModel.registerUser(username, email)
                        }
                    } else {
                        Scaffold(
                            topBar = { 
                                TopBar(
                                    currentUser = currentUser,
                                    onAddPost = { showAddPost = true }
                                ) 
                            },
                            bottomBar = { 
                                BottomBar(
                                    selectedItem = selectedItem,
                                    onItemSelected = { selectedItem = it }
                                ) 
                            }
                        ) { paddingValues ->
                            when (selectedItem) {
                                0 -> BlogPostList(
                                    blogPosts = blogPosts,
                                    onUpvote = viewModel::upvotePost,
                                    Modifier.padding(paddingValues)
                                )
                                1 -> SearchScreen(blogPosts)
                                2 -> ProfileScreen(
                                    user = currentUser,
                                    onLogout = { viewModel.logout() },
                                    Modifier.padding(paddingValues)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}