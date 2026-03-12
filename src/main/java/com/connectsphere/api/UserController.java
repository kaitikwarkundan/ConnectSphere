package com.connectsphere.api;

import com.connectsphere.algorithms.*;
import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;
import com.connectsphere.search.UserSearchTrie;
import com.connectsphere.service.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final SocialGraph graph;
    private final UserSearchTrie trie;
    private final GraphService graphService;
    private final FeedService feedService;
    private int nextId = 7; // starts after sample data

    // Spring automatically injects these beans
    public UserController(SocialGraph graph, UserSearchTrie trie,
                          GraphService graphService, FeedService feedService) {
        this.graph = graph;
        this.trie = trie;
        this.graphService = graphService;
        this.feedService = feedService;
    }

    // ─── USER ENDPOINTS ───

    // GET /api/users — get all users
    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return graph.getAllUsers().values();
    }

    // GET /api/users/1 — get user by ID
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return graph.getUser(id);
    }

    // POST /api/users?name=John&age=25 — add new user
    @PostMapping("/users")
    public Map<String, Object> addUser(@RequestParam String name,
                                        @RequestParam int age) {
        User user = new User(nextId, name, age);
        graph.addUser(user);
        trie.insert(name, nextId);
        nextId++;
        return Map.of(
            "message", "User added successfully!",
            "user", user
        );
    }

    // ─── FRIENDSHIP ENDPOINTS ───

    // POST /api/friends?userId1=1&userId2=2 — add friendship
    @PostMapping("/friends")
    public Map<String, String> addFriendship(@RequestParam int userId1,
                                              @RequestParam int userId2) {
        graph.addFriendship(userId1, userId2);
        return Map.of("message", graph.getUser(userId1).getName()
            + " and " + graph.getUser(userId2).getName()
            + " are now friends!");
    }

    // GET /api/friends/1 — get friends of user
    @GetMapping("/friends/{userId}")
    public List<User> getFriends(@PathVariable int userId) {
        List<User> friends = new ArrayList<>();
        graph.getFriends(userId).forEach(id -> friends.add(graph.getUser(id)));
        return friends;
    }

    // ─── ALGORITHM ENDPOINTS ───

    // GET /api/path?from=1&to=4 — BFS shortest path
    @GetMapping("/path")
    public Map<String, Object> getShortestPath(@RequestParam int from,
                                                @RequestParam int to) {
        List<Integer> path = new BFSPathFinder().findShortestPath(graph, from, to);
        if (path.isEmpty()) {
            return Map.of("message", "No connection found!");
        }
        List<String> names = new ArrayList<>();
        path.forEach(id -> names.add(graph.getUser(id).getName()));
        return Map.of(
            "path", names,
            "degrees", path.size() - 1
        );
    }

    // GET /api/suggestions/1 — friend suggestions
    @GetMapping("/suggestions/{userId}")
    public List<User> getSuggestions(@PathVariable int userId) {
        List<User> suggestions = new ArrayList<>();
        new FriendSuggestion().suggest(graph, userId).stream()
            .limit(5)
            .forEach(id -> suggestions.add(graph.getUser(id)));
        return suggestions;
    }

    // GET /api/search?name=Ali — search by name prefix
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name) {
        List<User> results = new ArrayList<>();
        trie.searchByPrefix(name).forEach(id -> results.add(graph.getUser(id)));
        return results;
    }

    // GET /api/influencers — top 5 most connected users
    @GetMapping("/influencers")
    public List<Map<String, Object>> getInfluencers() {
        List<Map<String, Object>> result = new ArrayList<>();
        new InfluenceScore().getTopInfluencers(graph, 5).forEach(e -> {
            User u = graph.getUser(e.getKey());
            result.add(Map.of(
                "user", u,
                "connections", e.getValue()
            ));
        });
        return result;
    }

    // GET /api/communities — detect communities
    @GetMapping("/communities")
    public List<List<String>> getCommunities() {
        List<List<Integer>> raw = new DFSCommunity().findAllCommunities(graph);
        List<List<String>> result = new ArrayList<>();
        raw.forEach(group -> {
            List<String> names = new ArrayList<>();
            group.forEach(id -> names.add(graph.getUser(id).getName()));
            result.add(names);
        });
        return result;
    }

    // GET /api/stats — network statistics
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        int totalUsers = graph.getAllUsers().size();
        int totalConnections = graph.getAdjacencyList().values()
            .stream().mapToInt(List::size).sum() / 2;
        return Map.of(
            "totalUsers", totalUsers,
            "totalFriendships", totalConnections,
            "avgFriendsPerUser",
                totalUsers == 0 ? 0 :
                String.format("%.1f", (double)(totalConnections * 2) / totalUsers)
        );
    }

    // GET /api/mutual?userId1=1&userId2=3 — mutual friends
    @GetMapping("/mutual")
    public Map<String, Object> getMutualFriends(@RequestParam int userId1,
                                                 @RequestParam int userId2) {
        Set<Integer> f1 = new HashSet<>(graph.getFriends(userId1));
        Set<Integer> f2 = new HashSet<>(graph.getFriends(userId2));
        f1.retainAll(f2);
        List<String> mutualNames = new ArrayList<>();
        f1.forEach(id -> mutualNames.add(graph.getUser(id).getName()));
        return Map.of(
            "user1", graph.getUser(userId1).getName(),
            "user2", graph.getUser(userId2).getName(),
            "mutualFriends", mutualNames
        );
    }
}
