package com.brighterbrain.brighterlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import jxl.Workbook;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextFromUser;
    Button buttonToSaveExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeObjects();
        buttonToSaveExcel.setOnClickListener(this);
    }

    private void initializeObjects() {
        editTextFromUser = (EditText) findViewById(R.id.editTextFromUser);
        buttonToSaveExcel = (Button) findViewById(R.id.buttonToSaveExcel);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonToSaveExcel) {
            saveToExcel();
        }
    }

    private void saveToExcel() {
        String textFromUser = editTextFromUser.getText().toString();
        String timeSheetNameFromUser = textFromUser + ".xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheetOne;
        Row row;

        /*
         * .createCellStyle() is being problematic
         */

        //CellStyle cellStyleForHeaderRow = workbook.createCellStyle();
        //cellStyleForHeaderRow.setFillForegroundColor(HSSFColor.LIME.index);
        //cellStyleForHeaderRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        sheetOne = workbook.createSheet("Weekly TimeSheet");
        row = sheetOne.createRow(0);

        createDayCell(row);
        createDateCell(row);
        createRegularHoursCell(row);
        createPTOCell(row);
        createHolidayCell(row);
        createTotalCell(row);

        setColumnWidthForEachCell(sheetOne);

        File file = new File(getExternalCacheDir(), timeSheetNameFromUser);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            Toast.makeText(this, "Write Successful!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error Writing to File", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save File", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Outputstream is not closed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createDayCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue("Day");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void createDateCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(1);
        cell.setCellValue("Date");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void createRegularHoursCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(2);
        cell.setCellValue("Regular Hours");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void createPTOCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(3);
        cell.setCellValue("PTO");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void createHolidayCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(4);
        cell.setCellValue("Holiday");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void createTotalCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
        Cell cell;
        cell = row.createCell(5);
        cell.setCellValue("Total");
        //cell.setCellStyle(cellStyleForHeaderRow);
    }

    private void setColumnWidthForEachCell(Sheet sheetOne) {
        sheetOne.setColumnWidth(0, (15 * 500));
        sheetOne.setColumnWidth(1, (15 * 500));
        sheetOne.setColumnWidth(2, (15 * 500));
        sheetOne.setColumnWidth(3, (15 * 500));
        sheetOne.setColumnWidth(4, (15 * 500));
        sheetOne.setColumnWidth(5, (15 * 500));
    }
}
