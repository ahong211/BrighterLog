package com.brighterbrain.brighterlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Workbook workbook = new Workbook() {
            @Override
            public Sheet[] getSheets() {
                return new Sheet[0];
            }

            @Override
            public String[] getSheetNames() {
                return new String[0];
            }

            @Override
            public Sheet getSheet(int i) throws IndexOutOfBoundsException {
                return null;
            }

            @Override
            public Sheet getSheet(String s) {
                return null;
            }

            @Override
            public int getNumberOfSheets() {
                return 0;
            }

            @Override
            public Cell findCellByName(String s) {
                return null;
            }

            @Override
            public Cell getCell(String s) {
                return null;
            }

            @Override
            public Range[] findByName(String s) {
                return new Range[0];
            }

            @Override
            public String[] getRangeNames() {
                return new String[0];
            }

            @Override
            public boolean isProtected() {
                return false;
            }

            @Override
            protected void parse() throws BiffException, PasswordException {

            }

            @Override
            public void close() {

            }
        };
    }
}
