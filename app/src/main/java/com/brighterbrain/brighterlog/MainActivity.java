package com.brighterbrain.brighterlog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText editTextFromUser;
    Button buttonToSaveExcel;
    Button buttonToReadExcel;
    Button buttonToSendExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeObjects();
        buttonToSaveExcel.setOnClickListener(this);
        buttonToReadExcel.setOnClickListener(this);
        buttonToSendExcel.setOnClickListener(this);
    }

    private void initializeObjects() {
        editTextFromUser = (EditText) findViewById(R.id.editTextFromUser);
        buttonToSaveExcel = (Button) findViewById(R.id.buttonToSaveExcel);
        buttonToReadExcel = (Button) findViewById(R.id.buttonToReadExcel);
        buttonToSendExcel = (Button) findViewById(R.id.buttonToSendExcel);
    }

    @Override
    public void onClick(View view) {

        String excelNameEditText;
        switch (view.getId()) {
            case R.id.buttonToSaveExcel:
                saveToExcel(view);
                break;

            case R.id.buttonToReadExcel:
                excelNameEditText = editTextFromUser.getText().toString();
                readExcelFile(getApplicationContext(), excelNameEditText + ".xls");
                break;

            case R.id.buttonToSendExcel:
                excelNameEditText = editTextFromUser.getText().toString();
                sendExcelFile(excelNameEditText + ".xls");
                break;
        }
    }

    private void sendExcelFile(String filename) {
        File file = new File(getExternalCacheDir(), filename);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String emailAddress = ""; // TODO: Get email address from User.
        emailIntent.setType("application/excel");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Sending Excel File 1");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Check for Attachment");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
        startActivity(Intent.createChooser(emailIntent, "Send this Email via"));
    }

    private void saveToExcel(View view) {
        String textFromUser = editTextFromUser.getText().toString();
        String timeSheetNameFromUser = textFromUser + ".xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheetOne;
        Row row;

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
            Log.d(TAG, MainActivity.this.getFilesDir().getAbsolutePath());
            hideKeyboard(view);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Error Writing File", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error Saving File", Toast.LENGTH_SHORT).show();

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

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return;
        }

        try {
            // Create Input Stream
            File file = new File(context.getExternalCacheDir(), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem fileSystemObject = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(fileSystemObject);
            HSSFSheet firstSheetInWorkbook = myWorkBook.getSheetAt(0);

            /* We now need something to iterate through the cells.*/
            Iterator rowIter = firstSheetInWorkbook.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.d(TAG, "Cell Value: " + myCell.toString());
                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}
