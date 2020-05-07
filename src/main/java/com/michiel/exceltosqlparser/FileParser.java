package com.michiel.exceltosqlparser;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class FileParser {
    private HSSFWorkbook workbook;
    //private SQLFileWriter writer = new SQLFileWriter();
    private String SQLString;

    public FileParser() throws FileNotFoundException {
        ExcelReader read = new ExcelReader("D:\\WorkingTalent\\JavaProjectMaaltijdApp\\nevo_online_2019.xls");
        this.workbook = read.getWorkbook();
        fillSQLString();
        new SQLFileWriter(SQLString);
    }

    private void fillSQLString(){
        /*
        Only use the columns:
        D) Product_omschrijving (Nederlands)
        E) Product_description (English)
        F) synoniem/synoym
        G) Meeteenheid
        H) Hoeveelheid
        L) ENERCJ_kJ
        M) ENERCC_kcal
        N) PROT_g

        And add "id" column

        Skip first row since this has the names of the headers
        */
        StringBuilder SQLStringValues = new StringBuilder();
        SQLStringValues.append("VALUES\n");
        int[] columnNumbers = {3, 4, 5, 6, 7, 11, 12, 13};
        int IDCounter = 0;
        String cellString = "";
        double cellValue = 0.0;
        Sheet sheet1 = workbook.getSheet("NevoOnline2019 Nutrientgehaltes");
        for (Row row : sheet1) {
            if (IDCounter == 0){ //Skip first row
                ++IDCounter;
                continue;
            }
            SQLStringValues.append("\t(");
            //SQLStringValues.append(IDCounter).append(", "); //Add ID to Values
            for (int columnNumber : columnNumbers) {
                Cell cell = row.getCell(columnNumber);
                try{
                    cellValue = cell.getNumericCellValue();
                    SQLStringValues.append(cellValue);
                }catch(Exception e){
                    try{
                        cellString = "\"" + cell.getStringCellValue() + "\""; //String in literal quotationmarks
                        cellString = removeForbiddenCharacters(cellString);
                    }catch(Exception ex){
                        cellString = "\"\""; //If the cell has no value give empty String as value
                    }
                    finally {
                        System.out.println(cellString);
                        SQLStringValues.append(cellString);
                    }
                }finally {
                    if(columnNumber != 13) {
                        SQLStringValues.append(", ");
                    }
                }
            }
            SQLStringValues.append("), \n");
            ++IDCounter;
        }
        SQLStringValues.deleteCharAt(SQLStringValues.length() - 1).deleteCharAt(SQLStringValues.length() - 1).deleteCharAt(SQLStringValues.length() - 1); //Remove ", \n"
        //String SQLStringFirstLine = "INSERT INTO Ingredients (id, naam, name, naam2, measure_unit, amount, energy_kj, energy_kcal, protein_g)\n";
        String SQLStringFirstLine = "INSERT INTO Ingredients (Naam, Name, Naam2, Meeteenheid, Hoeveelheid, Energie_kJ, Energie_kcal, Eiwit_g)\n";
        SQLString = SQLStringFirstLine + SQLStringValues.toString() + ";";
    }

    public String removeForbiddenCharacters(String cellString){
        String newCellString = "";
        newCellString = cellString
                .replace("'", "")
                .replace("-", "")
                .replace("/", " ")
                .replace(";", "")
                .replace("+", "plus")
                .replace("%", "procent")
                .replace("&", "n")
                .replace("<", "")
                .replace(">", "")
                .replace("(", "")
                .replace(")", "")
                .replace(",", " ");
        return newCellString;
    }



}
