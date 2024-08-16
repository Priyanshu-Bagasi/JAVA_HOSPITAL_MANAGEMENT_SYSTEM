package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient () {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();


        int i = 0;
        try {
            String sql = "insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            i=ps.executeUpdate();

            if(i>0){
                System.out.println("Patient Added Successfully");
            }
            else{
                System.out.println("Failed To Add Patient");
            }
            }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void viewPatients(){
        String sql="select * from patients";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet set=ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+--------------+------------------------------+-----------+-----------+");
            System.out.println("|  Patient Id  |             Name             |    Age    |   Gender  |");
            System.out.println("+--------------+------------------------------+-----------+-----------+");

            while(set.next()){
                int id=set.getInt("id");
                String name=set.getString("name");
                int age=set.getInt("age");
                String gender=set.getString("gender");

                System.out.printf("|%-14s|%-30s|%-11s|%-11s|\n",id,name,age,gender);
                System.out.println("+--------------+------------------------------+-----------+-----------+");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }



    public boolean getPatientById(int id){
        String sql="select * from patients where id=?";
        try{
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet set=ps.executeQuery();

            if(set.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}