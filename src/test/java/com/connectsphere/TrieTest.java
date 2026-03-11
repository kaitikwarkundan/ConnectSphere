package com.connectsphere;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.connectsphere.search.UserSearchTrie;

public class TrieTest {

    private UserSearchTrie trie;

    @Before
    public void setUp() {
        trie = new UserSearchTrie();
        trie.insert("Alice", 1);
        trie.insert("Bob", 2);
        trie.insert("Charlie", 3);
        trie.insert("Diana", 4);
        trie.insert("Adam", 5);
    }

    @Test
    public void testExactMatch() {
        List<Integer> result = trie.searchByPrefix("Alice");
        assertEquals(1, result.size());
        assertTrue(result.contains(1));
    }

    @Test
    public void testPrefixMatch() {
        // "A" should match Alice and Adam
        List<Integer> result = trie.searchByPrefix("A");
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(5));
    }

    @Test
    public void testCaseInsensitive() {
        // "alice" lowercase should still find Alice
        List<Integer> result = trie.searchByPrefix("alice");
        assertEquals(1, result.size());
    }

    @Test
    public void testNoMatch() {
        List<Integer> result = trie.searchByPrefix("XYZ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAllUsers() {
        // Empty prefix or single char that matches all
        List<Integer> result = trie.searchByPrefix("b");
        assertEquals(1, result.size());
        assertTrue(result.contains(2));
    }
}