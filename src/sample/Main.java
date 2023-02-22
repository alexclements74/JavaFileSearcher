/*
    Who: Alex Clements
    What: Program to search for files/directories recursively using JavaFX
    When: August 23rd, 2021
    Why: It was a required project made for Claremont Communications
 */

// Name of package
package sample;

// Importing libraries
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.lang.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

// Main program
public class Main extends Application {
    //Declaring output window
    Stage window;


    //Helps to launch program
    public static void main(String[] args) {
        launch(args);
    }

    //Overrides outside elements
    @Override

    //Setting up the entirety of the program
    public void start(Stage primaryStage) throws Exception {
        //Declaring the stage
        window = primaryStage;
        window.setTitle("File Searcher");

        //Declaring the base grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 0, 10, 25));
        grid.setVgap(10);
        grid.setHgap(10);

        //Search Term Input label
        Label termLabel = new Label("Search Term:");
        GridPane.setConstraints(termLabel, 0, 0);

        //Term input
        TextField termInput = new TextField("");
        GridPane.setConstraints(termInput, 0, 1);

        //Directory Input Label
        Label directoryLabel = new Label("Directory:");
        GridPane.setConstraints(directoryLabel, 0, 2);

        //Directory Input
        TextField directoryInput = new TextField("C:\\");
        GridPane.setConstraints(directoryInput, 0, 3);

        //Button
        Button submitButton = new Button("Submit Search");
        GridPane.setConstraints(submitButton, 0, 4);

        //Setting text
        Text outputText = new Text("");
        GridPane.setConstraints(outputText, 0, 5);

        //Button on action
        submitButton.setOnAction(e -> {
            //Acquiring strings from user input
            String searchTerm = termInput.getText();
            String directoryTerm = directoryInput.getText();
            outputText.setText("Your term is " + searchTerm + " & your directory is " + directoryTerm + ".");
            System.out.println(searchTerm + directoryTerm);

            //Creating link to FileFinder class
            FileFinder ff = new FileFinder();
            System.out.println("List of all files needed: " + directoryTerm);
            System.out.println("----------------------------------");

            //Declaring string that fully calls FileFinder function
            String [][] completeArr = ff.listAllFiles(directoryTerm);

            //Initializing variable to count the amount of files to search through
            int sizeOfArr = 0;

            //Acquiring number of files to search through and assigning to variable
            for (int i=0; i < 100000000; i++){
                if (completeArr[0][i] != null) {
                    sizeOfArr++;
                }
            }

            //Declaring 1st array
            String filteredArr[][] = new String[2][sizeOfArr];

            //Declaring variables that help to sort through array and output results
            int filterLength = 0;
            String fileName = "";
            String pathName = "";

            //If statement to ensure that the search term and array aren't blank
            if (searchTerm.length() > 0 && sizeOfArr > 0) {

                //For loop iterating through every file/directory in the given directory
                for (int i = 0; i < sizeOfArr; i++) {

                    //If a spot in the array exists
                    if(completeArr[1][i]!=null){

                        //Declares file variable to ensure sorting through array goes well
                        File f = new File(completeArr[1][i]);

                        fileName = f.getName();

                        pathName = completeArr[1][i];
                    }

                    //If the search term is in one of the files to search through
                    if (pathName.toLowerCase().contains(searchTerm.toLowerCase()) || fileName.toLowerCase().contains(searchTerm.toLowerCase())) {

                        //If a spot in the array exists
                        if (completeArr[1][i]!=null) {

                            //Taking value in array full of files into individual variables
                            String part1 = completeArr[0][i];
                            String part2 = completeArr[1][i];

                            //Assigning those variables to a new array
                            filteredArr[0][i] = part1;
                            filteredArr[1][i] = part2;

                            //Adding one to the filterLength variable every time
                            filterLength++;
                        }
                    }
                }
            }

            //Declaring an array list
            ArrayList<String> finalList = new ArrayList<>();

            //For loop to assign the final array values to the new array list
            for (int i = 0; i < filteredArr[0].length; i++) {
                for (int j = 0; j < 2; j++) {
                    finalList.add(filteredArr[j][i]);
                }

                //if statement to ensure there is a spot in the array to then assign it to the array list
                if(filteredArr[1][i]!=null){
                    File f = new File(filteredArr[1][i]);

                    finalList.add(f.getName());
                }

            }

            //Removes all null results in the arrayList just in case
            finalList.removeAll(Collections.singleton(null));

            //Creating new table and List to input data
            TableView<FileOut> table = new TableView<>();
            final ObservableList<FileOut> data = FXCollections.observableArrayList();

            //Declaring label of table
            Label resultLabel = new Label("Results: " + filterLength);
            resultLabel.setFont(new Font("Arial", 20));
            GridPane.setConstraints(resultLabel, 0, 6);

            //Ensuring that users cannot edit the table
            table.setEditable(false);

            //Declaring column that displays the type of file/directory
            TableColumn typeCol = new TableColumn("Type");
            typeCol.setCellValueFactory(new PropertyValueFactory<FileOut, String>("fileType"));


            //Declaring column that displays the name of the file/directory
            TableColumn nameCol = new TableColumn("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<FileOut, String>("fileName"));

            //Declaring column that displays the path of the file/directory
            TableColumn pathCol = new TableColumn("Path");
            pathCol.setCellValueFactory(new PropertyValueFactory<FileOut, String>("pathName"));

            //Setting policies for the table
            table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            table.setItems(data);
            table.getColumns().addAll(typeCol, nameCol, pathCol);
            GridPane.setConstraints(table, 0, 7);
            table.prefHeightProperty().bind(primaryStage.heightProperty());
            table.prefWidthProperty().bind(primaryStage.widthProperty());

            //Sending the label & table to the grid output
            grid.getChildren().addAll(resultLabel, table);

            //For list to assign values to the data table
            for (int i = 0; i < finalList.size(); i += 3) {
                data.add(new FileOut(finalList.get(i), finalList.get(i + 2), finalList.get(i + 1)));
            }


        });

        //Sending all other aspects to the grid
        grid.getChildren().addAll(termLabel, termInput, directoryLabel, directoryInput, submitButton, outputText);

        //Declaring scene and showing window
        Scene scene = new Scene(grid, 900, 450);
        window.setScene(scene);
        window.show();
    }

}

//Declaring class
class FileFinder {

    //Setting up intial variables
    int i = 0;
    String usefulArray[][] = new String[2][100000000];

    //Initializing function
    public String[][] listAllFiles(String path) {

        //Declaring file that acquires all the files within a given path
        File root = new File(path);
        File[] list = root.listFiles();

        //if the list exists
        if(list!=null) {
            //for loop to run through each directory
            for(File f: list){

                    //if the file is a directory
                    if(f.isDirectory()){

                        //Declaring file
                        File result = f.getAbsoluteFile();

                        //Running through the function again with the path
                        listAllFiles(f.getAbsolutePath());

                        //Changing the file variable to a string
                        String finResult = result.toString();

                        //Assigning the strings to the overall array
                        usefulArray[0][i] = "Directory";
                        usefulArray[1][i] = finResult;


                    } else{

                        //Declaring file name into a string
                        String finResult2 = f.getAbsolutePath();

                        //Assigning the strings to the overall array
                        usefulArray[0][i] = "File";
                        usefulArray[1][i] = finResult2;

                    }
                    i++;

            }

            //in case of error
        }else{
            System.out.println("There was an error in acquiring the root path. Please try again.");
        }
        //returning array filled with all files and directory in the given path
        return usefulArray;

    }

}
