package com.example.a9lab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun BlogPostList(blogPosts: List<BlogPost>, onUpvote: (BlogPost) -> Unit = {}, padding: Modifier) {
    LazyColumn {
        items(blogPosts) { post ->
            BlogPostItem(post, onUpvote)
            Divider()
        }
    }
}

@Composable
fun BlogPostItem(post: BlogPost, onUpvote: (BlogPost) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "r/${post.subreddit} • Posted by u/${post.author}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        Text(
            text = post.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onUpvote(post) }) {
                Icon(Icons.Default.ArrowUpward, "Upvote")
            }
            Text(text = "${post.upvotes}")

            Icon(Icons.Default.Comment, "Comments")
            Text(text = "${post.commentCount}")
        }
    }
}