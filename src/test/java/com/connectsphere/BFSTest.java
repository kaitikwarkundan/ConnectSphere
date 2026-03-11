package com.connectsphere;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.connectsphere.algorithms.BFSPathFinder;
import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;

public class BFSTest {

    private SocialGraph graph;
    private BFSPathFinder bfs;

    @Before
    public void setUp() {
        graph = new SocialGraph();
        bfs = new BFSPathFinder();

        // Create users
        graph.addUser(new User(1, "Alice", 22));
        graph.addUser(new User(2, "Bob", 24));
        graph.addUser(new User(3, "Charlie", 21));
        graph.addUser(new User(4, "Diana", 23));
        graph.addUser(new User(5, "Eve", 25));

        // Create connections
        graph.addFriendship(1, 2); // Alice - Bob
        graph.addFriendship(2, 3); // Bob - Charlie
        graph.addFriendship(3, 4); // Charlie - Diana
    }

    @Test
    public void testDirectFriendPath() {
        // Alice and Bob are direct friends — path length should be 2
        List<Integer> path = bfs.findShortestPath(graph, 1, 2);
        assertEquals(2, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(2), path.get(1));
    }

    @Test
    public void testIndirectPath() {
        // Alice to Charlie: Alice → Bob → Charlie
        List<Integer> path = bfs.findShortestPath(graph, 1, 3);
        assertEquals(3, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(3), path.get(2));
    }

    @Test
    public void testLongPath() {
        // Alice to Diana: Alice → Bob → Charlie → Diana
        List<Integer> path = bfs.findShortestPath(graph, 1, 4);
        assertEquals(4, path.size());
    }

    @Test
    public void testNoPath() {
        // Eve is not connected to anyone — no path
        List<Integer> path = bfs.findShortestPath(graph, 1, 5);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testSameUser() {
        // Path from Alice to Alice — just herself
        List<Integer> path = bfs.findShortestPath(graph, 1, 1);
        assertEquals(1, path.size());
    }
}