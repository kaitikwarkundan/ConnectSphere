package com.connectsphere.model;

public class Post {
    private int postId;
    private int authorId;
    private String content;
    private String timestamp;

    public Post(int postId, int authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public int getPostId() { return postId; }
    public int getAuthorId() { return authorId; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return "[Post #" + postId + " by User " + authorId + "]: " + content;
    }
}