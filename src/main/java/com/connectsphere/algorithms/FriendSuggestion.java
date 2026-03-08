package com.connectsphere.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.connectsphere.graph.SocialGraph;

public class FriendSuggestion {

    public List<Integer> suggest(SocialGraph graph, int userId) {
        Set<Integer> myFriends = new HashSet<>(graph.getFriends(userId));
        Map<Integer, Integer> mutualCount = new HashMap<>();

        for (int friendId : myFriends) {
            for (int fof : graph.getFriends(friendId)) {
                if (fof != userId && !myFriends.contains(fof)) {
                    mutualCount.merge(fof, 1, Integer::sum);
                }
            }
        }

        return mutualCount.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}