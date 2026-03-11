package com.connectsphere.ui;

import java.util.List;
import java.util.Scanner;

import com.connectsphere.algorithms.BFSPathFinder;
import com.connectsphere.algorithms.DFSCommunity;
import com.connectsphere.algorithms.FriendSuggestion;
import com.connectsphere.algorithms.InfluenceScore;
import com.connectsphere.algorithms.UnionFind;
import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;
import com.connectsphere.search.UserSearchTrie;
import com.connectsphere.service.FeedService;
import com.connectsphere.service.GraphService;
import com.connectsphere.service.UserService;

public class CLIApp {

    static SocialGraph graph = new SocialGraph();
    static UserSearchTrie trie = new UserSearchTrie();
    static Scanner scanner = new Scanner(System.in);
    static int nextId = 1;
    static UserService userService;
    static GraphService graphService;
    static FeedService feedService;

    public static void main(String[] args) {
        System.out.println("🌐 Welcome to ConnectSphere!");
        loadSampleData();
        userService = new UserService(graph, trie, nextId);
        graphService = new GraphService(graph);
        feedService = new FeedService(graph);

        while (true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1.  Add User");
            System.out.println("2.  Add Friendship");
            System.out.println("3.  View Friends of a User");
            System.out.println("4.  Find Shortest Path (BFS)");
            System.out.println("5.  Get Friend Suggestions");
            System.out.println("6.  Search Users by Name");
            System.out.println("7.  Top Influencers");
            System.out.println("8.  Detect Communities (Union-Find)");
            System.out.println("9.  Detect Communities (DFS)");
            System.out.println("10. List All Users");
            System.out.println("11. Mutual Friends");
            System.out.println("12. Network Stats");
            System.out.println("13. Create Post");
            System.out.println("14. View My Feed");
            System.out.println("0.  Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1  -> addUser();
                case 2  -> addFriendship();
                case 3  -> viewFriends();
                case 4  -> findPath();
                case 5  -> suggestFriends();
                case 6  -> searchUsers();
                case 7  -> showInfluencers();
                case 8  -> detectCommunitiesUnionFind();
                case 9  -> detectCommunitiesDFS();
                case 10 -> userService.listAllUsers();
                case 11 -> mutualFriends();
                case 12 -> graphService.showNetworkStats();
                case 13 -> createPost();
                case 14 -> viewFeed();
                case 0  -> { System.out.println("👋 Goodbye!"); return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    static void loadSampleData() {
        String[][] users = {
            {"Alice","22"}, {"Bob","24"}, {"Charlie","21"},
            {"Diana","23"}, {"Eve","25"}, {"Frank","26"}
        };
        for (String[] u : users) {
            User user = new User(nextId, u[0], Integer.parseInt(u[1]));
            graph.addUser(user);
            trie.insert(u[0], nextId);
            nextId++;
        }
        graph.addFriendship(1, 2);
        graph.addFriendship(2, 3);
        graph.addFriendship(3, 4);
        graph.addFriendship(1, 5);
        graph.addFriendship(4, 6);
        System.out.println("✅ Loaded 6 sample users!\n");
    }

    static void addUser() {
        System.out.print("Enter name: "); String name = scanner.nextLine();
        System.out.print("Enter age: ");  int age = scanner.nextInt(); scanner.nextLine();
        userService.addUser(name, age);
    }

    static void addFriendship() {
        System.out.print("Enter User ID 1: "); int id1 = scanner.nextInt();
        System.out.print("Enter User ID 2: "); int id2 = scanner.nextInt();
        scanner.nextLine();
        graph.addFriendship(id1, id2);
        System.out.println("✅ Friendship added!");
    }

    static void viewFriends() {
        System.out.print("Enter User ID: "); int id = scanner.nextInt(); scanner.nextLine();
        User user = graph.getUser(id);
        if (user == null) { System.out.println("❌ User not found!"); return; }
        System.out.println("Friends of " + user.getName() + ":");
        List<Integer> friends = graph.getFriends(id);
        if (friends.isEmpty()) System.out.println("  No friends yet.");
        else friends.forEach(f -> System.out.println("  → " + graph.getUser(f).getName()));
    }

    static void findPath() {
        System.out.print("From User ID: "); int from = scanner.nextInt();
        System.out.print("To User ID:   "); int to = scanner.nextInt(); scanner.nextLine();
        List<Integer> path = new BFSPathFinder().findShortestPath(graph, from, to);
        if (path.isEmpty()) { System.out.println("❌ No connection found!"); return; }
        System.out.print("📍 Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(graph.getUser(path.get(i)).getName());
            if (i < path.size() - 1) System.out.print(" → ");
        }
        System.out.println("\n🔗 Degrees of separation: " + (path.size() - 1));
    }

    static void suggestFriends() {
        System.out.print("Enter User ID: "); int id = scanner.nextInt(); scanner.nextLine();
        List<Integer> suggestions = new FriendSuggestion().suggest(graph, id);
        System.out.println("💡 Suggestions for " + graph.getUser(id).getName() + ":");
        if (suggestions.isEmpty()) System.out.println("  No suggestions available.");
        else suggestions.stream().limit(5)
            .forEach(s -> System.out.println("  → " + graph.getUser(s).getName()));
    }

    static void searchUsers() {
        System.out.print("Enter name prefix: "); String prefix = scanner.nextLine();
        List<Integer> results = trie.searchByPrefix(prefix);
        if (results.isEmpty()) System.out.println("❌ No users found.");
        else results.forEach(id -> System.out.println("  → " + graph.getUser(id)));
    }

    static void showInfluencers() {
        System.out.println("🏆 Top Influencers:");
        new InfluenceScore().getTopInfluencers(graph, 5)
            .forEach(e -> System.out.println("  → "
                + graph.getUser(e.getKey()).getName()
                + " | Connections: " + e.getValue()));
    }

    static void detectCommunitiesUnionFind() {
        UnionFind uf = new UnionFind();
        graph.getAllUsers().keySet().forEach(uf::makeSet);
        graph.getAdjacencyList().forEach((u, friends) ->
            friends.forEach(f -> uf.union(u, f)));
        System.out.println("👥 Communities (Union-Find):");
        int i = 1;
        for (List<Integer> group : uf.getCommunities().values()) {
            System.out.print("  Community " + i++ + ": ");
            group.forEach(id -> System.out.print(graph.getUser(id).getName() + " "));
            System.out.println();
        }
    }

    static void detectCommunitiesDFS() {
        List<List<Integer>> communities = new DFSCommunity().findAllCommunities(graph);
        System.out.println("👥 Communities (DFS):");
        int i = 1;
        for (List<Integer> group : communities) {
            System.out.print("  Community " + i++ + ": ");
            group.forEach(id -> System.out.print(graph.getUser(id).getName() + " "));
            System.out.println();
        }
    }

    static void mutualFriends() {
        System.out.print("Enter User ID 1: "); int id1 = scanner.nextInt();
        System.out.print("Enter User ID 2: "); int id2 = scanner.nextInt();
        scanner.nextLine();
        graphService.showMutualFriends(id1, id2);
    }

    static void createPost() {
        System.out.print("Your User ID: "); int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Write your post: "); String content = scanner.nextLine();
        feedService.createPost(id, content);
    }

    static void viewFeed() {
        System.out.print("Your User ID: "); int id = scanner.nextInt();
        scanner.nextLine();
        feedService.showFeedForUser(id);
    }
}