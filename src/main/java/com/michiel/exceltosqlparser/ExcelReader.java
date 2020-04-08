package com.michiel.exceltosqlparser;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReader {
    private String fileName;
    HSSFWorkbook workbook;

    ExcelReader(String fileName)throws FileNotFoundException{
        this.fileName = fileName;
        openExcelFile();
    }

    private void openExcelFile() throws FileNotFoundException{
        try {
                FileInputStream fis = new FileInputStream(fileName);
                this.workbook = new HSSFWorkbook(fis);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public HSSFWorkbook getWorkbook(){
        return workbook;
    }



}
