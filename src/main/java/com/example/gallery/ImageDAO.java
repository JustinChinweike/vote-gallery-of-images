package com.example.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {

    public static boolean addImage(String filePath, int userId) {
        boolean success = false;
        String sql = "INSERT INTO images (file_path, user_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filePath);
            stmt.setInt(2, userId);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static Image getImageById(int imageId) {
        Image img = null;
        String sql = "SELECT i.image_id, i.file_path, i.user_id, u.username "
                + "FROM images i JOIN users u ON i.user_id = u.user_id "
                + "WHERE i.image_id = ?";
        try (Connection conn = DBUtil.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, imageId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    img = new Image(
                            rs.getInt("image_id"),
                            rs.getString("file_path"),
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            0 // voteCount not needed for single fetch
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static List<Image> getAllImagesWithVotes() {
        List<Image> images = new ArrayList<>();
        
    String sql =
        "SELECT i.image_id, i.file_path, i.user_id, u.username, " +
        "       COUNT(v.vote_id) AS vote_count " +
        "FROM images i " +
        "JOIN users u ON i.user_id = u.user_id " +
        "LEFT JOIN votes v ON i.image_id = v.image_id " +
        "GROUP BY i.image_id, i.file_path, i.user_id, u.username " +
        "ORDER BY i.image_id DESC";
    
try (Connection conn = DBUtil.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Image img = new Image(
                        rs.getInt("image_id"),
                        rs.getString("file_path"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("vote_count")
                );
                images.add(img);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public static List<Image> getTopImages(int N) {
        List<Image> topImages = new ArrayList<>();
        String sql =
    "SELECT i.image_id, i.file_path, i.user_id, u.username, " +
    "       COUNT(v.vote_id) AS vote_count " +
    "FROM images i " +
    "JOIN users u ON i.user_id = u.user_id " +
    "LEFT JOIN votes v ON i.image_id = v.image_id " +
    "GROUP BY i.image_id, i.file_path, i.user_id, u.username " +
    "ORDER BY vote_count DESC " +
    "LIMIT ?";

try (Connection conn = DBUtil.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, N);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Image img = new Image(
                            rs.getInt("image_id"),
                            rs.getString("file_path"),
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getInt("vote_count")
                    );
                    topImages.add(img);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topImages;
    }

    public static boolean deleteImage(int imageId) {
        boolean success = false;
        String sql = "DELETE FROM images WHERE image_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, imageId);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                success = true;
            }
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        return success;
    }
}
