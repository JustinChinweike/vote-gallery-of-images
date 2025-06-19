package com.example.gallery;

public class Image {

    private final int id;
    private final String filePath;
    private final int userId;
    private final String username; // uploader's username
    private final  int voteCount;

    public Image(int id, String filePath, int userId, String username, int voteCount) {
        this.id = id;
        this.filePath = filePath;
        this.userId = userId;
        this.username = username;
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
