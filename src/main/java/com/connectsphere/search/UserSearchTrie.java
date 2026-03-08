package com.connectsphere.search;

import java.util.ArrayList;
import java.util.List;

public class UserSearchTrie {
    private TrieNode root = new TrieNode();

    public void insert(String name, int userId) {
        TrieNode current = root;
        for (char c : name.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
        current.userId = userId;
    }

    public List<Integer> searchByPrefix(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(c)) return new ArrayList<>();
            current = current.children.get(c);
        }
        List<Integer> results = new ArrayList<>();
        collectAll(current, results);
        return results;
    }

    private void collectAll(TrieNode node, List<Integer> results) {
        if (node.isEndOfWord) results.add(node.userId);
        for (TrieNode child : node.children.values()) {
            collectAll(child, results);
        }
    }
}