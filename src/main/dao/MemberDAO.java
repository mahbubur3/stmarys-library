package main.dao;

import main.database.DatabaseConnection;
import main.models.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public boolean addMember(Member member) {
        String sql = "INSERT INTO members (member_name, email, membership_type) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, member.getMemberName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getMembershipType());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
            return false;
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();

        String sql = "SELECT * FROM members";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Member member = new Member(
                        resultSet.getInt("member_id"),
                        resultSet.getString("member_name"),
                        resultSet.getString("email"),
                        resultSet.getString("membership_type")
                );

                members.add(member);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving members: " + e.getMessage());
        }

        return members;
    }

    public Member searchMemberById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, memberId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Member(
                        resultSet.getInt("member_id"),
                        resultSet.getString("member_name"),
                        resultSet.getString("email"),
                        resultSet.getString("membership_type")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error searching member by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Member> searchMembersByName(String keyword) {
        List<Member> members = new ArrayList<>();

        String sql = "SELECT * FROM members WHERE member_name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member(
                        resultSet.getInt("member_id"),
                        resultSet.getString("member_name"),
                        resultSet.getString("email"),
                        resultSet.getString("membership_type")
                );

                members.add(member);
            }

        } catch (SQLException e) {
            System.out.println("Error searching members: " + e.getMessage());
        }

        return members;
    }

    public boolean updateMember(Member member) {
        String sql = """
                UPDATE members
                SET member_name = ?, email = ?, membership_type = ?
                WHERE member_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, member.getMemberName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getMembershipType());
            preparedStatement.setInt(4, member.getMemberId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, memberId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting member: " + e.getMessage());
            return false;
        }
    }
}