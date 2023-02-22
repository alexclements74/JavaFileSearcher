package sample;

import javafx.beans.property.SimpleStringProperty;

//Function that helps output the table data
public class FileOut {
    private final SimpleStringProperty fileType;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty pathName;

    FileOut(String fType, String fName, String pName) {
        this.fileType = new SimpleStringProperty(fType);
        this.fileName = new SimpleStringProperty(fName);
        this.pathName = new SimpleStringProperty(pName);
    }

    public String getFileType() {
        return fileType.get();
    }

    public void setFileType(String fType) {
        fileType.set(fType);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fName) {
        pathName.set(fName);
    }

    public String getPathName() {
        return pathName.get();
    }

    public void setPathName(String pName) {
        fileType.set(pName);
    }



}
