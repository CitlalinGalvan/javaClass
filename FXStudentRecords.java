/* FXStudentRecords.java
    Citlalin Galvan, 0806721
    CIS084 Java Programming
    Develop Environment: Visual Studio Code
    12/12/2019 

Develop and test a program that provides a menu that gives the user the option of:
  1) entering a student's name and scores which then computes the average score and grade, or 
  2) displaying from a text file, the list of all student names, scores, averages and grades
  3) exit the program

*/

// import Application for GUI
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

// import file classes
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;

public class FXStudentRecords extends Application{
    
    // List of Controls
    private static Label lblTitle;
    private static Label lblDirections;
    private static Label lblTXtFile; // The txt file created or used
    private static Label lblStudentName;
    private static Label lblScores;
    private static Button btnNewStudent;
    private static Button btnReadRecords_txt;
    private static Button btnClear;
    private static Button btnExit;
    private static TextField txtFile;
    private static TextField txtStudentName;
    private static TextField txtScore1;
    private static TextField txtScore2;
    private static TextField txtScore3;
    private static TextField txtScore4;
    private static TextField txtScore5;
    private static TextArea txtDisplayRecords;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        createControls(root);

        Scene scene = new Scene (root, 500, 800);    // Size of GUI (X,Y)
        stage.setTitle("Citlalin's Student Records");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    } // END of Start

    public static void main(String[] args) {
        launch(args);
    }

    /**********************************************************************
            Create the action of when Buttons are clicked
    **********************************************************************/
    private static void btnNewStudentClick() {
        String student_name;
        int[] score = new int[5];
        String letter_grade;
        double average_score;

        try {
            score[0] = Integer.valueOf(txtScore1.getText());
            score[1] = Integer.valueOf(txtScore2.getText());
            score[2] = Integer.valueOf(txtScore3.getText());
            score[3] = Integer.valueOf(txtScore4.getText());
            score[4] = Integer.valueOf(txtScore5.getText());

            for (int n = 0; n <= score.length;) {
                if (score[n] < 0)
                    throw new IllegalArgumentException("Scores cannot be negatve.");
            }
        } catch (NumberFormatException e) {
            txtDisplayRecords.setText("Scores have to be numeric.");
            return;
        }
        catch (IllegalArgumentException e) {
            txtDisplayRecords.setText(e.getMessage());
            return;
        }

        student_name = txtStudentName.getText();
        average_score = (score[0] + score[1] + score[2] + score[3] + score[4]) / score.length;

        // Get letter Grade
        if (average_score >= 90) { letter_grade = "A";}
        else if (average_score >= 80 && average_score < 90) { letter_grade = "B";}
        else if (average_score >= 70 && average_score < 80) { letter_grade = "C";}
        else if (average_score >= 60 && average_score < 70) { letter_grade = "D";}
        else {letter_grade = "F";}

        update_student_file(
            student_name, score[0], score[1], score[2], score[3], score[4], 
            average_score, letter_grade
        );
    } // END of btnNewStudentClick()

    private static void update_student_file(String sName, int s1, int s2, int s3, int s4, int s5, double avg_S, String lttr_G) {
        try {
            String fileName = txtFile.getText();
            File student_file = new File (fileName);
            
            if (student_file.exists() != true) {
                createNewFile(); // create a new file first
            }

            String new_Student = (
                sName + ", Scores=" + s1 + ", " + s2 + ", " + s3 + ", " + s4 + ", " + s5 +
                ", Average=" + avg_S + ", Grade=" + lttr_G + "\n");

            FileWriter write_student_record = new FileWriter(txtFile.getText(), true);
            write_student_record.write(new_Student);
            write_student_record.close();

            txtDisplayRecords.setText(
                "/***************************************** /\n" 
                + "Updating Student Records\n" +
                "/***************************************** /\n" +
                new_Student + "\n");
        } catch (IOException e) {
            txtDisplayRecords.setText("file not found");
            return;
        }
    } // END of update_Student_file()

    static void createNewFile() {
        try {
            String fileName = txtFile.getText();
            File newFile = new File(fileName);
            newFile.createNewFile();
            String title = "Student Records\n\n";

            FileWriter new_student_records_file = new FileWriter(fileName);
            new_student_records_file.write(title);
            new_student_records_file.close();
        } catch (IOException e) {
            txtDisplayRecords.setText("file not found");
            return;
        }
    } // END of createNewFile()

    private static void btnReadRecords_txtClick() {
        String read_rec = "";
        try {
            File student_records = new File(txtFile.getText());
            Scanner read_records = new Scanner(student_records);
            
            while (read_records.hasNextLine()) {
              String student_line = read_records.nextLine();
              read_rec = read_rec + student_line + "\n";
            }
            read_records.close();
            txtDisplayRecords.setText(read_rec);
          } catch (FileNotFoundException e) {
            txtDisplayRecords.setText("An error occurred.");
          }
    } // END of btnReadRecords_txtClick()

    private static void btnClearClick() { 
        txtDisplayRecords.setText("");    // The new output will be nothing
    } // end of btnClearClick

    private static void btnExitClick() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();      // close window
    } // end of btnExitClick

    /**********************************************************************
                Organize the GUI's Interphase
    **********************************************************************/
    private static void createControls(Pane root) {
        Font titleFont = Font.font("Ariel", FontWeight.BOLD, 36); // title
        Font smallFont  = Font.font("Ariel", FontWeight.NORMAL, 15); // Labels
        Font btnFont  = Font.font("Ariel", FontWeight.BOLD, 15); // Labels
        Font labelFont  = Font.font("Ariel", FontWeight.NORMAL, 20); // Labels

        lblTitle = new Label("Student Records");
            lblTitle.setFont(titleFont);          // large-bold font
            lblTitle.setLayoutX(50);
            lblTitle.setLayoutY(4);
            root.getChildren().add(lblTitle);

        lblDirections = new Label(
            "1. Add a File name with .txt to Create OR Read\n" +
            "2. Click Add New Student or Read Records");
            lblDirections.setFont(smallFont);
            lblDirections.setLayoutX(20);
            lblDirections.setLayoutY(50);
            root.getChildren().add(lblDirections);

        lblTXtFile = new Label("Text File");
            lblTXtFile.setFont(labelFont);
            lblTXtFile.setLayoutX(30);
            lblTXtFile.setLayoutY(100);
            root.getChildren().add(lblTXtFile);

        txtFile = new TextField();
            txtFile.setFont(smallFont);
            txtFile.setLayoutX(120);
            txtFile.setLayoutY(100);
            txtFile.setMaxSize(190,30);        // make TextFields the same size
            txtFile.setMinSize(190,30);
            root.getChildren().add(txtFile);

        lblStudentName = new Label("Student Name");
            lblStudentName.setFont(labelFont);
            lblStudentName.setLayoutX(30);
            lblStudentName.setLayoutY(170);
            root.getChildren().add(lblStudentName);

        txtStudentName = new TextField();
            txtStudentName.setFont(smallFont);
            txtStudentName.setLayoutX(180);
            txtStudentName.setLayoutY(170);
            txtStudentName.setMaxSize(135,30);        // make TextFields the same size
            txtStudentName.setMinSize(135,30);
            root.getChildren().add(txtStudentName);

        lblScores = new Label("Scores");
            lblScores.setFont(labelFont);
            lblScores.setLayoutX(30);
            lblScores.setLayoutY(210);
            root.getChildren().add(lblScores);
        
        txtScore1 = new TextField();
            txtScore1.setFont(smallFont);
            txtScore1.setLayoutX(100);
            txtScore1.setLayoutY(210);
            txtScore1.setMaxSize(50,30);        // make TextFields the same size
            txtScore1.setMinSize(50,30);
            root.getChildren().add(txtScore1);

        txtScore2 = new TextField();
            txtScore2.setFont(smallFont);
            txtScore2.setLayoutX(160);
            txtScore2.setLayoutY(210);
            txtScore2.setMaxSize(50,30);        // make TextFields the same size
            txtScore2.setMinSize(50,30);
            root.getChildren().add(txtScore2);

        txtScore3 = new TextField();
            txtScore3.setFont(smallFont);
            txtScore3.setLayoutX(220);
            txtScore3.setLayoutY(210);
            txtScore3.setMaxSize(50,30);        // make TextFields the same size
            txtScore3.setMinSize(50,30);
            root.getChildren().add(txtScore3);

        txtScore4 = new TextField();
            txtScore4.setFont(smallFont);
            txtScore4.setLayoutX(280);
            txtScore4.setLayoutY(210);
            txtScore4.setMaxSize(50,30);        // make TextFields the same size
            txtScore4.setMinSize(50,30);
            root.getChildren().add(txtScore4);

        txtScore5 = new TextField();
            txtScore5.setFont(smallFont);
            txtScore5.setLayoutX(340);
            txtScore5.setLayoutY(210);
            txtScore5.setMaxSize(50,30);        // make TextFields the same size
            txtScore5.setMinSize(50,30);
            root.getChildren().add(txtScore5);

        txtDisplayRecords = new TextArea();
            txtDisplayRecords.setFont(smallFont);
            txtDisplayRecords.setLayoutX(30);
            txtDisplayRecords.setLayoutY(300);
            txtDisplayRecords.setMaxSize(350,460);
            txtDisplayRecords.setMinSize(350,460);
            root.getChildren().add(txtDisplayRecords);

        btnNewStudent = new Button("Add\nNew\nStudent");
            btnNewStudent.setFont(btnFont);
            btnNewStudent.setLayoutX(400);
            btnNewStudent.setLayoutY(160);
            btnNewStudent.setMaxSize(80,80);      // make the buttons the same size
            btnNewStudent.setMinSize(80,80);
            // provide a link to the event handler for the New Student button
            btnNewStudent.setOnAction( e -> btnNewStudentClick() );
            btnNewStudent.setOnKeyPressed (e -> btnNewStudentClick() );
            root.getChildren().add(btnNewStudent);

        btnReadRecords_txt = new Button("Read\nRecords\nFile");
            btnReadRecords_txt.setFont(btnFont);
            btnReadRecords_txt.setLayoutX(400);
            btnReadRecords_txt.setLayoutY(300);
            btnReadRecords_txt.setMaxSize(80,80);      // make the buttons the same size
            btnReadRecords_txt.setMinSize(80,80);
            // provide a link to the event handler for the Read Records button
            btnReadRecords_txt.setOnAction( e -> btnReadRecords_txtClick() );
            btnReadRecords_txt.setOnKeyPressed (e -> btnReadRecords_txtClick() );
            root.getChildren().add(btnReadRecords_txt);

        btnClear = new Button("Clear");
            btnClear.setFont(btnFont);
            btnClear.setLayoutX(400);
            btnClear.setLayoutY(400);
            btnClear.setMaxSize(80,80);      // make the buttons the same size
            btnClear.setMinSize(80,80);
            // provide a link to the event handler for the Clear button
            btnClear.setOnAction( e -> btnClearClick() );
            btnClear.setOnKeyPressed (e -> btnClearClick() );
            root.getChildren().add(btnClear);

        btnExit = new Button("Exit");
            btnExit.setFont(btnFont);
            btnExit.setLayoutX(400);
            btnExit.setLayoutY(670);
            btnExit.setMaxSize(80,80);      // make the buttons the same size
            btnExit.setMinSize(80,80);
            // provide a link to the event handler for the Exit button
            btnExit.setOnAction( e -> btnExitClick() );
            btnExit.setOnKeyPressed (e -> btnExitClick() );
            root.getChildren().add(btnExit);  
    } // END of createcontrols()
} // END OF FXStudentRecords


