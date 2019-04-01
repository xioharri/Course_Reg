package com.example.course_reg;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database myDb;
    EditText editName, editSurname, editMarks, editCourse,editPriority, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;

    /**
     * Constructor from DatabaseHelper.Class is called to create database and table
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new Database(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_Surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editCourse = (EditText) findViewById(R.id.editText_course);
        editPriority = (EditText) findViewById(R.id.editText_priority);
        editTextId = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        AddData();
        viewAll();
        updateData();
        DeleteData();
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data has been Deleted", Toast.LENGTH_SHORT).show();

                        else
                            Toast.makeText(MainActivity.this, "Data could not be  Deleted", Toast.LENGTH_SHORT).show();        }
                }
        );
    }
    public void updateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = (boolean) myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString(),
                                editCourse.getText().toString(),
                                editPriority.getText().toString()
                        );
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data has been Updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data could not be  Updated", Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString(),
                                editCourse.getText().toString(),
                                editPriority.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }
    /**Acquires data and displays error if there is nothing in the table*/
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            //show message
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Surname :" + res.getString(2) + "\n");
                            buffer.append("Marks :" + res.getString(3) + "\n");
                            buffer.append("Course :" + res.getString(4) + "\n");
                            buffer.append("Priority :" + res.getString(5) + "\n\n");
                        }
                        //show all data
                        showMessage("Data", buffer.toString());
                    }

                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}