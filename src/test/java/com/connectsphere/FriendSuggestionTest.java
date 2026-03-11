package com.connectsphere;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.connectsphere.algorithms.FriendSuggestion;
import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;

public class FriendSuggestionTest {

    private SocialGraph graph;
    private FriendSuggestion suggestion;

    @Before
    public void setUp() {
        graph = new SocialGraph();
        suggestion = new FriendSuggestion();

        graph.addUser(new User(1, "Alice", 22));
        graph.addUser(new User(2, "Bob", 24));
        graph.addUser(new User(3, "Charlie", 21));
        graph.addUser(new User(4, "Diana", 23));

        graph.addFriendship(1, 2); // Alice - Bob
        graph.addFriendship(2, 3); // Bob - Charlie
        graph.addFriendship(2, 4); // Bob - Diana
    }

    @Test
    public void testSuggestFriendsOfFriends() {
        // Alice's friend is Bob. Bob's friends are Charlie and Diana.
        // So Alice should get Charlie and Diana as suggestions
        List<Integer> suggestions = suggestion.suggest(graph, 1);
        assertTrue(suggestions.contains(3));
        assertTrue(suggestions.contains(4));
    }

    @Test
    public void testNoSuggestionForIsolatedUser() {
        graph.addUser(new User(5, "Eve", 25)); // Eve has no friends
        List<Integer> suggestions = suggestion.suggest(graph, 5);
        assertTrue(suggestions.isEmpty());
    }

    @Test
    public void testAlreadyFriendNotSuggested() {
        // Bob is already Alice's friend — should NOT appear in suggestions
        List<Integer> suggestions = suggestion.suggest(graph, 1);
        assertFalse(suggestions.contains(2));
    }
}