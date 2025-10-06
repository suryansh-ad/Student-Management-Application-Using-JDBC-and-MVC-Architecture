// File: StudentDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "SQL123";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert student
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (studentID, name, department, marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, student.getStudentID());
            ps.setString(2, student.getName());
            ps.setString(3, student.getDepartment());
            ps.setDouble(4, student.getMarks());
            ps.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all students
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student(rs.getInt("studentID"), rs.getString("name"),
                        rs.getString("department"), rs.getDouble("marks"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update student
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, department=?, marks=? WHERE studentID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setDouble(3, student.getMarks());
            ps.setInt(4, student.getStudentID());
            ps.executeUpdate();
            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete student
    public void deleteStudent(int studentID) {
        String sql = "DELETE FROM students WHERE studentID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentID);
            ps.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
