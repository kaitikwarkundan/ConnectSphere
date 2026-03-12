package com.connectsphere;

import com.connectsphere.graph.SocialGraph;
import com.connectsphere.model.User;
import com.connectsphere.search.UserSearchTrie;
import com.connectsphere.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConnectSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectSphereApplication.class, args);
    }

    // Create shared graph — used by all controllers
    @Bean
    public SocialGraph socialGraph() {
        SocialGraph graph = new SocialGraph();
        UserSearchTrie trie = new UserSearchTrie();
        int nextId = 1;

        // Load sample data
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

        return graph;
    }

    // Create shared trie
    @Bean
    public UserSearchTrie userSearchTrie(SocialGraph graph) {
        UserSearchTrie trie = new UserSearchTrie();
        graph.getAllUsers().values().forEach(u ->
            trie.insert(u.getName(), u.getId()));
        return trie;
    }

    // Create services as beans
    @Bean
    public GraphService graphService(SocialGraph graph) {
        return new GraphService(graph);
    }

    @Bean
    public FeedService feedService(SocialGraph graph) {
        return new FeedService(graph);
    }
}