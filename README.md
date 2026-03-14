# 🌐 ConnectSphere — Social Network Graph Engine
🌐 **Live API:** https://web-production-6f55a.up.railway.app

A Java-based social networking backend that models real-world connections
using Graph Data Structures and Algorithms, exposed via a Spring Boot REST API.

> Built as a portfolio project by a Computer Science student to demonstrate
> real-world DSA implementation.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Tests](https://img.shields.io/badge/Tests-13%20passing-brightgreen)

---

## 🚀 Features

- 👥 Add users and create friendships
- 🔍 **BFS Shortest Path** — find connection between any two users
- 💡 **Friend Suggestions** — ranked by mutual friends count
- 🔎 **Trie-based Name Search** — instant prefix search autocomplete
- 🏆 **Influence Score** — find most connected users using Min-Heap
- 🏘️ **Community Detection** — using Union-Find and DFS
- 📰 **Post Feed** — posts from friends simulated using Queue
- 📊 **Network Stats** — total users, friendships, average connections
- 🤝 **Mutual Friends** — find common friends between two users
- 🌐 **REST API** — 10 endpoints powered by Spring Boot

---

## 🧠 Data Structures & Algorithms Used

| DSA | Where Used | Why |
|-----|-----------|-----|
| Graph (Adjacency List) | Core social network | Models friendships naturally |
| HashMap | User lookup | O(1) access time |
| BFS + Queue | Shortest path | Finds minimum hops between users |
| DFS + Stack | Community detection | Explores deep connections |
| Trie | Name prefix search | O(L) search time |
| Min-Heap / Priority Queue | Top influencer ranking | Efficient top-N retrieval |
| Union-Find | Community clustering | Groups connected components |
| Sorting (Streams) | Friend suggestion ranking | Ranks by mutual friend count |

---

## 🌐 REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users?name=John&age=25` | Add new user |
| POST | `/api/friends?userId1=1&userId2=2` | Add friendship |
| GET | `/api/friends/{userId}` | Get friends of user |
| GET | `/api/path?from=1&to=4` | BFS shortest path |
| GET | `/api/suggestions/{userId}` | Friend suggestions |
| GET | `/api/search?name=Al` | Search users by name |
| GET | `/api/influencers` | Top 5 influential users |
| GET | `/api/communities` | Detect all communities |
| GET | `/api/stats` | Network statistics |
| GET | `/api/mutual?userId1=1&userId2=3` | Mutual friends |

---

## ⚙️ How to Run Locally

**Prerequisites:** Java 17+, Maven 3.9+
```bash
# Clone the repo
git clone https://github.com/kaitikwarkundan/ConnectSphere.git

# Go into the folder
cd ConnectSphere

# Run Spring Boot API
mvn spring-boot:run
```

API will be live at: `http://localhost:8080`

### Example API Calls:
```bash
# Get all users
curl http://localhost:8080/api/users

# Find shortest path from user 1 to user 4
curl http://localhost:8080/api/path?from=1&to=4

# Get friend suggestions for user 1
curl http://localhost:8080/api/suggestions/1

# Search users by name prefix
curl http://localhost:8080/api/search?name=Al
```

---

## 🧪 Running Tests
```bash
mvn test
```
```
Tests run: 13, Failures: 0, Errors: 0 ✅
```

---

## 📁 Project Structure
```
src/main/java/com/connectsphere/
├── model/                  → User, Post
├── graph/                  → SocialGraph (Adjacency List)
├── algorithms/             → BFS, DFS, FriendSuggestion,
│                             InfluenceScore, UnionFind
├── search/                 → Trie-based UserSearch
├── service/                → UserService, GraphService, FeedService
├── storage/                → JSON Save/Load
├── api/                    → Spring Boot REST Controllers
│   └── UserController.java → 12 REST endpoints
└── ui/                     → CLIApp (Terminal interface)
```

---

## 💡 Sample API Response

**GET** `/api/path?from=1&to=4`
```json
{
  "path": ["Alice", "Bob", "Charlie", "Diana"],
  "degrees": 3
}
```

**GET** `/api/stats`
```json
{
  "totalUsers": 6,
  "totalFriendships": 5,
  "avgFriendsPerUser": "1.7"
}
```

**GET** `/api/communities`
```json
[
  ["Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"]
]
```

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Core language |
| Spring Boot | 3.2.0 | REST API framework |
| Maven | 3.9 | Build & dependency management |
| Gson | 2.10.1 | JSON serialization |
| JUnit | 4.13.2 | Unit testing |

---

## 🗺️ Roadmap

- [x] Core Graph Engine
- [x] BFS / DFS Algorithms
- [x] Trie Search
- [x] Union-Find Community Detection
- [x] Spring Boot REST API
- [x] JUnit Test Suite
- [ ] Deploy on Railway (live URL)
- [ ] HTML/JS Frontend with graph visualization
- [ ] Dijkstra weighted connections
- [ ] MySQL database integration

---

## 👨‍💻 Author

**Kundan Kaitikwar**
6th Semester Computer Science Student

[![GitHub](https://img.shields.io/badge/GitHub-kaitikwarkundan-black)](https://github.com/kaitikwarkundan)