package com.connectsphere.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.connectsphere.graph.SocialGraph;

public class BFSPathFinder {

    public List<Integer> findShortestPath(SocialGraph graph, int startId, int endId) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parentMap = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == endId) return buildPath(parentMap, startId, endId);

            for (int neighbor : graph.getFriends(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Integer> buildPath(Map<Integer, Integer> parentMap, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        int current = end;
        while (current != start) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        path.addFirst(start);
        return path;
    }
}