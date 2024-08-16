package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

//    public void addDoctor () {
//        System.out.println("Enter Doctor Name: ");
//        String name = scanner.next();
//        System.out.println("Enter Specialization: ");
//        String specialization = scanner.next();
//
//
//        int i = 0;
//        try {
//            String sql = "insert into doctors(name,specialization) values(?,?)";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, name);
//            ps.setString(2, specialization);
//
//            i=ps.executeUpdate();
//
//            if(i>0){
//                System.out.println("Doctor Added Successfully");
//            }
//            else{
//                System.out.println("Failed To Add Doctor");
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }



    public void viewDoctors(){
        String sql="select * from doctors";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet set=ps.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+--------------+------------------------------+---------------------+");
            System.out.println("|  Doctor Id   |             Name             |    Specialization   |");
            System.out.println("+--------------+------------------------------+---------------------+");

            while(set.next()){
                int id=set.getInt("id");
                String name=set.getString("name");
                String specialization=set.getString("specialization");

                System.out.printf("|%-14s|%-30s|%-21s|\n",id,name,specialization);
                System.out.println("+--------------+------------------------------+---------------------+");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }



    public boolean getDoctorById(int id){
        String sql="select * from doctors where id=?";
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