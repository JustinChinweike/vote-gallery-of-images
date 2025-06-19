package com.example.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class VoteDAO {

    public static boolean addVote(int userId, int imageId) {
        boolean success = false;
        String sql = "INSERT INTO votes (user_id, image_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, imageId);
            stmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
// e.g., duplicate vote (unique constraint violation) will throw an
             ex.printStackTrace();   
        }
        return success;
    }

    public static Set<Integer> getVotedImagesByUser(int userId) {
        Set<Integer> votedIds = new HashSet<>();

        String sql = "SELECT image_id FROM votes WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    votedIds.add(rs.getInt("image_id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return votedIds;
    }
}
