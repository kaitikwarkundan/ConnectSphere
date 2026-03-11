package com.connectsphere.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;

public class GraphService {

    private SocialGraph graph;

    public GraphService(SocialGraph graph) {
        this.graph = graph;
    }

    public void showMutualFriends(int userId1, int userId2) {
        User u1 = graph.getUser(userId1);
        User u2 = graph.getUser(userId2);
        if (u1 == null || u2 == null) { System.out.println("❌ User not found!"); return; }
        Set<Integer> friends1 = new HashSet<>(graph.getFriends(userId1));
        Set<Integer> friends2 = new HashSet<>(graph.getFriends(userId2));
        friends1.retainAll(friends2);
        System.out.println("🤝 Mutual friends between " + u1.getName() + " and " + u2.getName() + ":");
        if (friends1.isEmpty()) System.out.println("  None.");
        else friends1.forEach(id -> System.out.println("  → " + graph.getUser(id).getName()));
    }

    public boolean areConnected(int userId1, int userId2) {
        return graph.getFriends(userId1).contains(userId2);
    }

    public void showNetworkStats() {
        int totalUsers = graph.getAllUsers().size();
        int totalConnections = graph.getAdjacencyList().values()
            .stream().mapToInt(List::size).sum() / 2;
        System.out.println("📊 Network Stats:");
        System.out.println("  Total Users      : " + totalUsers);
        System.out.println("  Total Friendships: " + totalConnections);
        System.out.println("  Avg Friends/User : " +
            (totalUsers == 0 ? 0 :
            String.format("%.1f", (double)(totalConnections * 2) / totalUsers)));
    }
}