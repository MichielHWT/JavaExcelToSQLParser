package com.michiel.exceltosqlparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLFileWriter {
    private String SQLString;
    LocalDateTime time = LocalDateTime.now();
    private String fileName = "ingredients_sql_" + time.format(DateTimeFormatter.BASIC_ISO_DATE);
    private String fileDirectory = "D:\\WorkingTalent\\JavaProjectMaaltijdApp\\exceltosqpparserOutput\\" + fileName +".sql";

    SQLFileWriter(String SQLString) throws FileNotFoundException {
        this.SQLString = SQLString;
        createOutputSQLFile();
    }

    public void createOutputSQLFile() throws FileNotFoundException {
        try{
            FileOutputStream fos = new FileOutputStream(fileDirectory);
            byte[] byteArraySQLString = SQLString.getBytes();
            fos.write(byteArraySQLString);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
