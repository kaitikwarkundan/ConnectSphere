package com.connectsphere.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.connectsphere.model.User;

public class SocialGraph {

    private Map<Integer, User> userMap = new HashMap<>();
    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    public void addUser(User user) {
        userMap.put(user.getId(), user);
        adjacencyList.put(user.getId(), new ArrayList<>());
    }

    public void addFriendship(int id1, int id2) {
        if (!userMap.containsKey(id1) || !userMap.containsKey(id2)) {
            System.out.println("❌ One or both users not found!");
            return;
        }
        adjacencyList.get(id1).add(id2);
        adjacencyList.get(id2).add(id1);
    }

    public void removeFriendship(int id1, int id2) {
        adjacencyList.get(id1).remove(Integer.valueOf(id2));
        adjacencyList.get(id2).remove(Integer.valueOf(id1));
    }

    public List<Integer> getFriends(int userId) {
        return adjacencyList.getOrDefault(userId, new ArrayList<>());
    }

    public User getUser(int userId) {
        return userMap.get(userId);
    }

    public Map<Integer, User> getAllUsers() { return userMap; }
    public Map<Integer, List<Integer>> getAdjacencyList() { return adjacencyList; }
}