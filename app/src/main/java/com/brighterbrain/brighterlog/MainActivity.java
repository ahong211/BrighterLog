package com.brighterbrain.brighterlog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getSimpleName();
//    EditText editTextFromUser;
//    Button buttonToSaveExcel;
//    Button buttonToReadExcel;
//    Button buttonToSendExcel;

    PayWeekFragment mPayWeekFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initializeObjects();
//        buttonToSaveExcel.setOnClickListener(this);
//        buttonToReadExcel.setOnClickListener(this);
//        buttonToSendExcel.setOnClickListener(this);


        mPayWeekFragment = new PayWeekFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.fragment_container, mPayWeekFragment).commit();
    }

//    private void initializeObjects() {
//        editTextFromUser = (EditText) findViewById(editTextFromUser);
//        buttonToSaveExcel = (Button) findViewById(buttonToSaveExcel);
//        buttonToReadExcel = (Button) findViewById(buttonToReadExcel);
//        buttonToSendExcel = (Button) findViewById(buttonToSendExcel);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view == buttonToSaveExcel) {
//            saveToExcel();
//        }
//
//        if (view == buttonToReadExcel) {
//            String excelNameEditText = editTextFromUser.getText().toString();
//            readExcelFile(getApplicationContext(), excelNameEditText + ".xls");
//        }
//
//        if (view == buttonToSendExcel) {
//            String excelNameEditText = editTextFromUser.getText().toString();
//            sendExcelFile(excelNameEditText + ".xls");
//        }
//    }
//
//    private void sendExcelFile(String filename) {
//        File file = new File(getExternalCacheDir(), filename);
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setType("application/excel"); // List of all Mime types found from Google Search
//        //emailIntent.setType("application/x-excel");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@address.com"});
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Sending Excel File 1");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Check for Attachment");
//        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
//        startActivity(Intent.createChooser(emailIntent, "Send this Email via"));
//    }
//
//    private void saveToExcel() {
//        String textFromUser = editTextFromUser.getText().toString();
//        String timeSheetNameFromUser = textFromUser + ".xls";
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        Sheet sheetOne;
//        Row row;
//
//        sheetOne = workbook.createSheet("Weekly TimeSheet");
//        row = sheetOne.createRow(0);
//
//        createDayCell(row);
//        createDateCell(row);
//        createRegularHoursCell(row);
//        createPTOCell(row);
//        createHolidayCell(row);
//        createTotalCell(row);
//
//        setColumnWidthForEachCell(sheetOne);
//
//        File file = new File(getExternalCacheDir(), timeSheetNameFromUser);
//        FileOutputStream outputStream = null;
//
//        try {
//            outputStream = new FileOutputStream(file);
//            workbook.write(outputStream);
//            Log.d(TAG, MainActivity.this.getFilesDir().getAbsolutePath());
//            Toast.makeText(this, "Write Successful!", Toast.LENGTH_SHORT).show();
//
//        } catch (IOException e) {
//            Toast.makeText(this, "Error Writing to File", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to save File", Toast.LENGTH_SHORT).show();
//
//        } finally {
//            try {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (Exception ex) {
//                Toast.makeText(this, "Outputstream is not closed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void createDayCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(0);
//        cell.setCellValue("Day");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void createDateCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(1);
//        cell.setCellValue("Date");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void createRegularHoursCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(2);
//        cell.setCellValue("Regular Hours");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void createPTOCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(3);
//        cell.setCellValue("PTO");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void createHolidayCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(4);
//        cell.setCellValue("Holiday");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void createTotalCell(Row row) { //, CellStyle cellStyleForHeaderRow) {
//        Cell cell;
//        cell = row.createCell(5);
//        cell.setCellValue("Total");
//        //cell.setCellStyle(cellStyleForHeaderRow);
//    }
//
//    private void setColumnWidthForEachCell(Sheet sheetOne) {
//        sheetOne.setColumnWidth(0, (15 * 500));
//        sheetOne.setColumnWidth(1, (15 * 500));
//        sheetOne.setColumnWidth(2, (15 * 500));
//        sheetOne.setColumnWidth(3, (15 * 500));
//        sheetOne.setColumnWidth(4, (15 * 500));
//        sheetOne.setColumnWidth(5, (15 * 500));
//    }
//
//    private void readExcelFile(Context context, String filename) {
//
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            Log.e(TAG, "Storage not available or read only");
//            return;
//        }
//
//        try {
//            // Create Input Stream
//            File file = new File(context.getExternalCacheDir(), filename);
//            FileInputStream myInput = new FileInputStream(file);
//
//            // Create a POIFSFileSystem object
//            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//
//            // Create a workbook using the File System
//            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
//
//            // Get the first sheet from workbook
//            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//            /* We now need something to iterate through the cells.*/
//            Iterator rowIter = mySheet.rowIterator();
//
//            while (rowIter.hasNext()) {
//                HSSFRow myRow = (HSSFRow) rowIter.next();
//                Iterator cellIter = myRow.cellIterator();
//                while (cellIter.hasNext()) {
//                    HSSFCell myCell = (HSSFCell) cellIter.next();
//                    Log.d(TAG, "Cell Value: " + myCell.toString());
//                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isExternalStorageReadOnly() {
//        String extStorageState = Environment.getExternalStorageState();
//        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
//    }
//
//    public boolean isExternalStorageAvailable() {
//        String extStorageState = Environment.getExternalStorageState();
//        return Environment.MEDIA_MOUNTED.equals(extStorageState);
//    }
}
