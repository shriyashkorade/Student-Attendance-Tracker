package com.attendance.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.attendance.model.SubjectBean;
import com.attendance.model.DBConnection;

public class AddSubjectDAO {

    public boolean addSubject(SubjectBean subject) {
        boolean status = false;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO subjects (subject_name, subject_type) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectType());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
