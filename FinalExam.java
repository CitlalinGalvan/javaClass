/* FinalExam.java
    Citlalin Galvan, 0806721
    CIS084 Java Programming
    Develop Environment: Visual Studio Code
    12/12/2019 

Develop and test a program that provides a menu that gives the user the option of:
  1) entering a student's name and scores which then computes the average score and grade, or 
  2) displaying from a text file, the list of all student names, scores, averages and grades
  3) exit the program

*/

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;

public class FinalExam {
    static Scanner stdin = new Scanner (System.in);
    public static void main (String[] args){

        int menu_choice;

        // Ask user to choose from menu in while loop
        do {
            System.out.print(
                "\n1- Input a Student Record" +
                "\n2- Display the Student records" +
                "\n3- Exit the program\n");
            
            menu_choice = stdin.nextInt(); 
            
            if (menu_choice == 1){
                newStudent();
            }
            else if (menu_choice == 2){
                displayRecords();
            }
            else if (menu_choice == 3) {
                continue;
            }
            else {
                System.out.print("Invalid option");
            }

        } while (menu_choice != 3);// end while loop
    } // end main

    static void newStudent() {
        String student_name;
        String student_last_name;
        int[] grade = new int[5];
        String letter_grade;

        System.out.print("Student Name: ");
        student_name = stdin.next();
        student_last_name = stdin.next();
        System.out.print("Enter 5 scores, seperated by spaces, then press ENTER: ");
        grade[0] = stdin.nextInt();
        grade[1] = stdin.nextInt();
        grade[2] = stdin.nextInt();
        grade[3] = stdin.nextInt();
        grade[4] = stdin.nextInt();

        double averageGrade = averageScore(grade[0], grade[1], grade[2], grade[3], grade[4]);
        
        if (averageGrade >= 90) { letter_grade = "A";}
        else if (averageGrade >= 80 && averageGrade < 90) { letter_grade = "B";}
        else if (averageGrade >= 70 && averageGrade < 80) { letter_grade = "C";}
        else if (averageGrade >= 60 && averageGrade < 70) { letter_grade = "D";}
        else {letter_grade = "F";}

        update_Student_file(
            student_name, grade[0], grade[1], grade[2], grade[3], grade[4], 
            averageGrade, letter_grade);
    } // END of newStudent

    static double averageScore(int g1, int g2, int g3, int g4, int g5) {
        double average_score = (g1 + g2 + g3 + g4 + g5) / 5;

        return average_score;
    } // END of averageScore

    static void update_Student_file(String sName, int g1, int g2, int g3, int g4, int g5, double avg_G, String lttr_G) {
        try {
            File student_file = new File ("Student Records.txt");
            
            if (student_file.exists() != true) {
                createNewFile(); // create a new file first
            }

            String new_Student = (
                sName + ", Scores=" + g1 + ", " + g2 + ", " + g3 + ", " + g4 + ", " + g5 +
                ", Average=" + avg_G + ", Grade=" + lttr_G + "\n");

            FileWriter write_student_record = new FileWriter("Student Records.txt", true);
            write_student_record.write(new_Student);
            write_student_record.close();

            System.out.printf(new_Student + "\n");
        } catch (IOException e) {
            System.out.print("file not found");
        }
    } // END of update_Student_file

    static void createNewFile() {
        try {
            File newFile = new File("Student Records.txt");
            newFile.createNewFile();
            String title = "Student Records\n\n";

            FileWriter new_student_records_file = new FileWriter("Student Records.txt");
            new_student_records_file.write(title);
            new_student_records_file.close();
        } catch (IOException e) {
            System.out.print("file not found");
        }

    } // END of createNewFile

    static void displayRecords() {
        try {
            File student_records = new File("Student Records.txt");
            Scanner read_records = new Scanner(student_records);
            while (read_records.hasNextLine()) {
              String student_line = read_records.nextLine();
              System.out.println(student_line);
            }
            read_records.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            //e.printStackTrace();
          }
    } // END of displayRecords
    //stdin.close();
} // end class