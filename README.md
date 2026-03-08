# 🌐 ConnectSphere — Social Network Graph Engine

A Java-based social networking backend that models real-world 
connections using Graph Data Structures and Algorithms.

Built as a portfolio project to demonstrate DSA implementation.

---

## 🚀 Features

- 👥 Add users and create friendships
- 🔍 BFS Shortest Path — find connection between any two users
- 💡 Friend Suggestions — ranked by mutual friends count
- 🔎 Trie-based Name Search — instant prefix search
- 🏆 Influence Score — find most connected users using Min-Heap
- 🏘️ Community Detection — using Union-Find and DFS
- 📰 Post Feed — simulated using Queue

---

## 🧠 Data Structures & Algorithms Used

| DSA | Where Used |
|-----|-----------|
| Graph (Adjacency List) | Core social network |
| HashMap | O(1) user lookup |
| BFS + Queue | Shortest path between users |
| DFS + Stack | Community detection |
| Trie | Prefix-based name search |
| Min-Heap / Priority Queue | Top influencer ranking |
| Union-Find | Community clustering |
| Sorting (Streams) | Friend suggestion ranking |

---

## 🛠️ Tech Stack

- Java 17
- Maven
- Gson (JSON storage)

---

## ⚙️ How to Run

**Prerequisites:** Java 17+, Maven
```bash
# Clone the repo
git clone https://github.com/kaitikwarkundan/ConnectSphere.git

# Go into the folder
cd ConnectSphere

# Run the project
mvn exec:java
```

---

## 📁 Project Structure
```
src/main/java/com/connectsphere/
├── model/          → User, Post
├── graph/          → SocialGraph (Adjacency List)
├── algorithms/     → BFS, DFS, FriendSuggestion, 
│                     InfluenceScore, UnionFind
├── search/         → Trie-based UserSearch
├── service/        → UserService, GraphService, FeedService
├── storage/        → JSON Save/Load
└── ui/             → CLIApp (Main entry point)
```

---

## 👨‍💻 Author

**Kundan Kaitikwar**  
6th Semester CS Student  
GitHub: [@kaitikwarkundan](https://github.com/kaitikwarkundan)