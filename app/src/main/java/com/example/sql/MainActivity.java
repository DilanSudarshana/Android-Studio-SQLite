package com.example.sql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtname, edtsname, edtmark, edtid;
    Button addData;
    Button viewAll;
    Button update;
    Button delete;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        edtname = (EditText) findViewById(R.id.edtname);
        edtsname = (EditText) findViewById(R.id.edtsname);
        edtmark = (EditText) findViewById(R.id.edtmark);
        edtid = (EditText) findViewById(R.id.edtid);

        addData = (Button) findViewById(R.id.btnAdd);
        viewAll = (Button) findViewById(R.id.btnView);
        update = (Button) findViewById(R.id.btnUpdate);
        delete = (Button) findViewById(R.id.btnDelete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    //Data insert
    public void addData() {

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(edtname.getText().toString(),
                        edtsname.getText().toString(),
                        edtmark.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data inserted successsfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
                edtname.setText("");
                edtsname.setText("");
                edtmark.setText("");
                edtid.setText("");
            }
        });
    }

    //Data Show
    public void viewAll() {
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = myDb.getAllData();
                if (result.getCount() == 0) {
                    showMessage("Error", "No data found");
                    return;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (result.moveToNext()) {
                        buffer.append("Id :" + result.getString(0) + "\n");
                        buffer.append("Name :" + result.getString(1) + "\n");
                        buffer.append("SurName :" + result.getString(2) + "\n");
                        buffer.append("Mark :" + result.getString(3) + "\n\n");
                        showMessage("Data", buffer.toString());
                    }
                }
            }
        });
    }

    //Data add to stringbuffer
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    //Data update
    public void updateData() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(edtid.getText().toString(),
                        edtsname.getText().toString(),
                        edtsname.getText().toString(),
                        edtmark.getText().toString());
                        edtname.setText("");
                        edtsname.setText("");
                        edtmark.setText("");
                        edtid.setText("");
                if (isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void deleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRaws=myDb.deleteData(edtid.getText().toString());
                if(deletedRaws>0){
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}