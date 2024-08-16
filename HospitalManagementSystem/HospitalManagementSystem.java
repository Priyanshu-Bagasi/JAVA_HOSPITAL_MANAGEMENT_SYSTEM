package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Priyanshu@9958891099";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner scanner=new Scanner(System.in);

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println();
                System.out.println("Enter Your Choice:");
                int choice=scanner.nextInt();

                switch(choice){
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;

                    case 2:
                        //view patients
                        patient.viewPatients();
                        System.out.println();
                        break;

                    case 3:
                        //view doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        //book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;

                    case 5:
                        System.out.println("THANK YOU!!  FOR USING HOSPITAL MANAGEMENT SYSTEM");
                        return;

                    default:
                        System.out.println("enter a valid choice");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id");
        int patientId=scanner.nextInt();
        System.out.println("Enter Doctor Id");
        int doctorId=scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate=scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
                String appointmentQuery="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                int i=0;
                try{
                    PreparedStatement ps=connection.prepareStatement(appointmentQuery);
                    ps.setInt(1,patientId);
                    ps.setInt(2,doctorId);
                    ps.setString(3,appointmentDate);

                    i=ps.executeUpdate();
                    if(i>0){
                        System.out.println("Appointment Booked");
                    }
                    else{
                        System.out.println("Failed To Book Appointment");
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Doctor not available on this date");
            }
        }
        else{
            System.out.println("Either patient or doctor doesn't exist");
        }
    }


    public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection){
        String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
        try{
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,doctorId);
            ps.setString(2,appointmentDate);

            ResultSet set=ps.executeQuery();

            while(set.next()){
                int count=set.getInt(1);
                if(count==0){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        return false;
    }
}
