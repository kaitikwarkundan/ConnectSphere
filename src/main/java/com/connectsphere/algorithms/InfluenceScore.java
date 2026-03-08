package com.connectsphere.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.connectsphere.graph.SocialGraph;

public class InfluenceScore {

    public List<Map.Entry<Integer, Integer>> getTopInfluencers(SocialGraph graph, int topN) {
        Map<Integer, Integer> scores = new HashMap<>();

        for (int userId : graph.getAllUsers().keySet()) {
            scores.put(userId, graph.getFriends(userId).size());
        }

        PriorityQueue<Map.Entry<Integer, Integer>> heap =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            heap.offer(entry);
            if (heap.size() > topN) heap.poll();
        }

        List<Map.Entry<Integer, Integer>> result = new ArrayList<>(heap);
        result.sort((a, b) -> b.getValue() - a.getValue());
        return result;
    }
}