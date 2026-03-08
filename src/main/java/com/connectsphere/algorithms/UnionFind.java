package com.connectsphere.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFind {
    private Map<Integer, Integer> parent = new HashMap<>();

    public void makeSet(int userId) {
        parent.put(userId, userId);
    }

    public int find(int userId) {
        if (parent.get(userId) != userId) {
            parent.put(userId, find(parent.get(userId)));
        }
        return parent.get(userId);
    }

    public void union(int id1, int id2) {
        int root1 = find(id1);
        int root2 = find(id2);
        if (root1 != root2) parent.put(root1, root2);
    }

    public Map<Integer, List<Integer>> getCommunities() {
        Map<Integer, List<Integer>> communities = new HashMap<>();
        for (int userId : parent.keySet()) {
            int root = find(userId);
            communities.computeIfAbsent(root, k -> new ArrayList<>()).add(userId);
        }
        return communities;
    }
}