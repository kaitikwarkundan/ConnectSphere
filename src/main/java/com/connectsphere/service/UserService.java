package com.connectsphere.service;

import java.util.ArrayList;
import java.util.List;

import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;
import com.connectsphere.search.UserSearchTrie;

public class UserService {

    private SocialGraph graph;
    private UserSearchTrie trie;
    private int nextId;

    public UserService(SocialGraph graph, UserSearchTrie trie, int startId) {
        this.graph = graph;
        this.trie = trie;
        this.nextId = startId;
    }

    public User addUser(String name, int age) {
        User user = new User(nextId, name, age);
        graph.addUser(user);
        trie.insert(name, nextId);
        System.out.println("✅ Added: " + user);
        nextId++;
        return user;
    }

    public void removeUser(int userId) {
        User user = graph.getUser(userId);
        if (user == null) { System.out.println("❌ User not found!"); return; }
        List<Integer> friends = new ArrayList<>(graph.getFriends(userId));
        for (int friendId : friends) graph.removeFriendship(userId, friendId);
        graph.getAllUsers().remove(userId);
        graph.getAdjacencyList().remove(userId);
        System.out.println("✅ Removed: " + user.getName());
    }

    public void listAllUsers() {
        System.out.println("📋 All Users:");
        graph.getAllUsers().values().forEach(u ->
            System.out.println("  ID: " + u.getId()
                + " | Name: " + u.getName()
                + " | Age: " + u.getAge()
                + " | Friends: " + graph.getFriends(u.getId()).size()));
    }

    public int getNextId() { return nextId; }
}