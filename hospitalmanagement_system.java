/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */import java.sql.*;
 import java.util.Scanner;
 
public class hospitalmanagement_system {
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
   static final String USER = "root";
   static final String PASSWORD = "";
   
   public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)){
            System.out.println("✅ Connected to Hospital Database!");
            int choice;

            do {
                System.out.println("\n===== MAIN MENU =====");
                System.out.println("1. Patient Management");
                System.out.println("2. Doctor Management");
                System.out.println("3. Booking Management");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 :patientMenu(conn, sc);break;
                    case 2 : doctorMenu(conn, sc);break;
                    case 3 : bookingMenu(conn, sc);break;
                    case 4 : System.out.println("👋 Exiting...");break;
                    default : System.out.println("❌ Invalid choice. Try again.");break;
                    
                }
            } while (choice != 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    private static void patientMenu(Connection conn, Scanner sc) throws SQLException {
        int choice;
        do {
            
                System.out.println("\n===== PATIENT CRUD MENU =====");
                System.out.println("1. Add Patient");
                System.out.println("2. View All patients");
                System.out.println("3. Update patient");
                System.out.println("4. Delete patient");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                
                switch(choice)  {
                    case 1 : addPatient(conn, sc);break;
                    case 2 : viewPatients(conn);break;
                    case 3 : updatePatient(conn, sc);break;
                    case 4 : deletePatient(conn, sc);break;
                    case 5 : System.out.println("👋 Exiting...");break;
                    default : System.out.println("❌ Invalid choice. Try again.");break;
                }
            } while (choice != 5);
                
   }
   private static void addPatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter phone no.: ");
        long phone_no = sc.nextLong();
        sc.nextLine();
        System.out.print("Enter date.(YYYY-MM-DD): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO patients (name, age, gender, phone_no,date) VALUES (?, ?, ?, ?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, gender);
        stmt.setLong(4, phone_no);
        stmt.setString(5,date );
        stmt.executeUpdate();
        System.out.println("✅ Patient added successfully!");
        }
         
    // READ
    private static void viewPatients(Connection conn) throws SQLException {
        String sql = "SELECT * FROM patients";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- Patient Records ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Name: %s | Age: %d | Gender: %s | PHONE:%d|DATE: %s%n",
                    rs.getInt("id"),
                    rs.getString("NAME"), 
                    rs.getInt("AGE"),
                    rs.getString("GENDER"),
                    rs.getLong("PHONE_NO"),
                    rs.getDate("DATE"));
        }
    }
        // UPDATE
    private static void updatePatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Phone number: ");
        Long phone_no=sc.nextLong();
        

        String sql = "UPDATE patients SET PHONE_NO = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, phone_no);
        stmt.setInt(2, id);
        int rows = stmt.executeUpdate();

        if (rows > 0) System.out.println("✅ Patient updated successfully!");
        else System.out.println("⚠️ Patient not found!");
        
    }
// DELETE
    private static void deletePatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM patients WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();

        if (rows > 0) System.out.println("🗑️ Patient deleted successfully!");
        else System.out.println("⚠️ Patient not found!");
    }


private static void doctorMenu(Connection conn, Scanner sc) throws SQLException {
        int choice;
        do {
            System.out.println("\n===== DOCTOR CRUD MENU =====");
            System.out.println("6. Add Doctor");
            System.out.println("7. View All Doctors");
            System.out.println("8. Update Doctor");
            System.out.println("9. Delete Doctor");
            System.out.println("10. Back");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 6 :addDoctor(conn, sc);break;
                case 7: viewDoctors(conn);break;
                case 8 : updateDoctor(conn, sc);break;
                case 9: deleteDoctor(conn, sc);break;
                case 10: System.out.println("⬅ Returning to Main Menu...");break;
                default : System.out.println("❌ Invalid choice.");break;
            }
        } while (choice != 10);
    }
private static void addDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Enter contact No.: ");
        Long contact = sc.nextLong();
        sc.nextLine();

        String sql = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, specialization);
        stmt.setLong(3, contact);
        stmt.executeUpdate();
        System.out.println("✅ Doctor added successfully!");
    }

    private static void viewDoctors(Connection conn) throws SQLException {
        String sql = "SELECT * FROM doctors";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- Doctor Records ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Name: %s | Specialization: %s | Contact: %s%n ",
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("SPECIALIZATION"),
                    rs.getLong("CONTACT"));
    }
    }

    private static void updateDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new contact number: ");
        Long contact = sc.nextLong();

        String sql = "UPDATE doctors SET contact = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, contact);
        stmt.setInt(2, id);
        int rows = stmt.executeUpdate();

        if (rows > 0) System.out.println("✅ Doctor updated successfully!");
        else System.out.println("⚠️ Doctor not found!");
    }

    private static void deleteDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Doctor ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM doctors WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();

        if (rows > 0) System.out.println("🗑️ Doctor deleted successfully!");
        else System.out.println("⚠️ Doctor not found!");
    }
    
    private static void bookingMenu(Connection conn, Scanner sc) throws SQLException {
    int choice;
    do {
        System.out.println("\n===== BOOKING MANAGEMENT MENU =====");
        System.out.println("11. Add Booking");
        System.out.println("12. View All Bookings");
        System.out.println("13. Update Booking");
        System.out.println("14. Delete Booking");
        System.out.println("15. Add/Update Payment");
        System.out.println("16. Back");
        System.out.print("Enter your choice: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 11: addBooking(conn, sc); break;
            case 12: viewBookings(conn); break;
            case 13: updateBooking(conn, sc); break;
            case 14: deleteBooking(conn, sc); break;
            case 15: updatepayment(conn, sc); break;
            case 16: System.out.println("⬅ Returning to Main Menu..."); break;
            default: System.out.println("❌ Invalid choice."); break;
        }
    } while (choice != 16);
}
private static void addBooking(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter Patient ID: ");
    int patient_id = sc.nextInt();
    sc.nextLine();
    
    System.out.print("Enter Patient Name: ");
    String patient_name = sc.nextLine();
    
    System.out.print("Enter Doctor ID: ");
    int doctor_id = sc.nextInt();
    sc.nextLine();
    
    System.out.print("Enter Doctor Name: ");
    String doctor_name = sc.nextLine();
    
    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
    String appointment_date = sc.nextLine();

    System.out.print("Enter Fees: ");
    double fees = sc.nextDouble();
    sc.nextLine();
    
    
    System.out.print("Enter Payment Amount: ");
    double payment = sc.nextDouble();
    sc.nextLine();

    double due = fees - payment;

    String sql = "INSERT INTO bookings (patient_id,patient_name, doctor_id,doctor_name, appointment_date, fees, payment_amount, due_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, patient_id);
    stmt.setString(2, patient_name);
    stmt.setInt(3, doctor_id);
    stmt.setString(4, doctor_name);
    stmt.setString(5, appointment_date);
    stmt.setDouble(6, fees);
    stmt.setDouble(7, payment);
    stmt.setDouble(8, due);

    stmt.executeUpdate();
    System.out.println("✅ Booking added successfully!");
}

private static void viewBookings(Connection conn) throws SQLException {
//    String sql = "SELECT booking_id,patient_name,doctor_name,appointment_date,fees from bookings";
      String sql = "SELECT b.booking_id, p.name AS patient_name, d.name AS doctor_name, b.appointment_date, b.fees,b.payment_amount,b.due_amount FROM bookings b JOIN patients p ON b.patient_id = p.id JOIN doctors d ON b.doctor_id = d.id";   
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);

    System.out.println("\n--- Booking Records ---");
    while (rs.next()) {
        System.out.printf("booking ID: %d | Patient name: %s | Doctor name : %s  | Date: %s | Fees: %.2f%n|Paid: %.2f | Due: %.2f%n",
                rs.getInt("booking_id"),
//                rs.getInt("patient_id"),
                rs.getString("patient_name"),
//                rs.getInt("doctor_id"),
                rs.getString("doctor_name"),
                rs.getDate("appointment_date"),
                rs.getDouble("fees"),
                rs.getDouble("payment_amount"),
                rs.getDouble("due_amount"));
    }
}
private static void updateBooking(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter Booking ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
    String newDate = sc.nextLine();

    System.out.print("Enter new Fees: ");
    double newFees = sc.nextDouble();
    sc.nextLine();
    
    String sqlGet = "SELECT appointment_date ,fees FROM bookings WHERE booking_id = ?";
    PreparedStatement getStmt = conn.prepareStatement(sqlGet);
    getStmt.setInt(1, id);
    ResultSet rs = getStmt.executeQuery();

        String sqlUpdate = "UPDATE bookings SET appointment_date = ?, fees = ? WHERE booking_id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
        updateStmt.setString(1, newDate);
        updateStmt.setDouble(2, newFees);
        updateStmt.setInt(3, id);

        int rows = updateStmt.executeUpdate();
     if (rows > 0) {
            System.out.println("✅ Booking updated successfully!");
    } else {
        System.out.println("⚠️ Booking ID not found!");
    }
}
private static void updatepayment(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter Booking ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter Payment Amount: ");
    double payment = sc.nextDouble();

    // ✅ Fixed: include fees column in the query
    String sqlGet = "SELECT fees, payment_amount FROM bookings WHERE booking_id = ?";
    PreparedStatement getStmt = conn.prepareStatement(sqlGet);
    getStmt.setInt(1, id);
    ResultSet rs = getStmt.executeQuery();

    if (rs.next()) {
        double totalFees = rs.getDouble("fees");
        double currentPayment = rs.getDouble("payment_amount");

        double newPayment = currentPayment + payment;
        double due = totalFees - newPayment;

        String sqlUpdate = "UPDATE bookings SET payment_amount = ?, due_amount = ? WHERE booking_id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
        updateStmt.setDouble(1, newPayment);
        updateStmt.setDouble(2, due);
        updateStmt.setInt(3, id);

        int rows = updateStmt.executeUpdate();

        if (rows > 0) {
            System.out.println("✅ Payment updated successfully!");
            System.out.printf("Total Fees: %.2f | Total Paid: %.2f | Due: %.2f%n",
                    totalFees, newPayment, due);
        } else {
            System.out.println("⚠️ Booking not found!");
        }
    } else {
        System.out.println("⚠️ Booking ID not found!");
    }
}

private static void deleteBooking(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter Booking ID to delete: ");
    int id = sc.nextInt();

    String sql = "DELETE FROM bookings WHERE booking_id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    int rows = stmt.executeUpdate();

    if (rows > 0) System.out.println("🗑️ Booking deleted successfully!");
    else System.out.println("⚠️ Booking not found!");
}

    
}

     
