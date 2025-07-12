package com.attendance.DAO;

import java.sql.*;
import java.util.*;
import com.attendance.model.*;
import com.attendance.model.DBConnection;

public class AssignTeacherDAO {

    public List<TeacherBean> getAllTeachers() {
        List<TeacherBean> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM teachers";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TeacherBean t = new TeacherBean();
                t.setTeacherId(rs.getInt("teacher_id"));
                t.setTeacherName(rs.getString("name"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SubjectBean> getUnassignedSubjects() {
        List<SubjectBean> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM subjects WHERE teacher_id IS NULL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectBean s = new SubjectBean();
                s.setSubjectId(rs.getInt("subject_id"));
                s.setSubjectName(rs.getString("subject_name"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean assignSubjectToTeacher(AssignTeacherBean bean) {
        boolean result = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE subjects SET teacher_id = ? WHERE subject_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bean.getTeacherId());
            ps.setInt(2, bean.getSubjectId());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
