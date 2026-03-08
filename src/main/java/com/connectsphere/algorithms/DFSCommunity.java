package com.connectsphere.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.connectsphere.graph.SocialGraph;

public class DFSCommunity {

    public List<List<Integer>> findAllCommunities(SocialGraph graph) {
        Set<Integer> visited = new HashSet<>();
        List<List<Integer>> communities = new ArrayList<>();

        for (int userId : graph.getAllUsers().keySet()) {
            if (!visited.contains(userId)) {
                List<Integer> community = new ArrayList<>();
                dfs(graph, userId, visited, community);
                communities.add(community);
            }
        }
        return communities;
    }

    private void dfs(SocialGraph graph, int userId,
                     Set<Integer> visited, List<Integer> community) {
        visited.add(userId);
        community.add(userId);
        for (int neighbor : graph.getFriends(userId)) {
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, visited, community);
            }
        }
    }
}