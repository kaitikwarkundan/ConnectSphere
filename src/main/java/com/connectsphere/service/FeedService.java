package com.connectsphere.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.Post;

public class FeedService {

    private Queue<Post> globalFeed = new LinkedList<>();
    private int nextPostId = 1;
    private SocialGraph graph;

    public FeedService(SocialGraph graph) {
        this.graph = graph;
    }

    public void createPost(int authorId, String content) {
        if (graph.getUser(authorId) == null) { System.out.println("❌ User not found!"); return; }
        Post post = new Post(nextPostId++, authorId, content);
        globalFeed.add(post);
        System.out.println("✅ Post created by "
            + graph.getUser(authorId).getName() + ": " + content);
    }

    public void showFeedForUser(int userId) {
        if (graph.getUser(userId) == null) { System.out.println("❌ User not found!"); return; }
        Set<Integer> myFriends = new HashSet<>(graph.getFriends(userId));
        myFriends.add(userId);
        System.out.println("📰 Feed for " + graph.getUser(userId).getName() + ":");
        boolean hasPosts = false;
        for (Post post : globalFeed) {
            if (myFriends.contains(post.getAuthorId())) {
                System.out.println("  " + post);
                hasPosts = true;
            }
        }
        if (!hasPosts) System.out.println("  No posts yet from friends.");
    }

    public void showAllPosts() {
        System.out.println("📰 All Posts (" + globalFeed.size() + " total):");
        if (globalFeed.isEmpty()) System.out.println("  No posts yet.");
        else globalFeed.forEach(p -> System.out.println("  " + p));
    }
}