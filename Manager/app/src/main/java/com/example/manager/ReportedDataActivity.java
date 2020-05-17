package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manager.dao.PersonDao;
import com.example.manager.dao.RecordDao;
import com.example.manager.entity.Person;
import com.example.manager.entity.Record;

import java.util.Date;

public class ReportedDataActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView accountIdTextView;
    private EditText temperatureEditText;
    private PersonDao personDao;
    private RecordDao recordDao;
    private Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_data);
        init();
        findViewById(R.id.upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=accountIdTextView.getText().toString();
                Integer personId=personDao.quuryPersonId(id);
                String temperature=temperatureEditText.getText().toString();
                Date date=new Date();
                Record record=new Record(personId,temperature,date);
                if(temperature.equals("")){
                    Toast.makeText(ReportedDataActivity.this, "请填写今日体温！", Toast.LENGTH_SHORT).show();
                }else{
                    recordDao.addPecord(record);
                    Toast.makeText(ReportedDataActivity.this, "今日体温已上传，感谢您的配合！", Toast.LENGTH_SHORT).show();
                    //findViewById(R.id.upload_button).setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void init(){
        nameTextView=findViewById(R.id.name_textView);
        accountIdTextView=findViewById(R.id.id_textView);
        temperatureEditText=findViewById(R.id.temperature_editText);
        personDao=new PersonDao(this);
        recordDao=new RecordDao(this);
        Intent intent=getIntent();
        String phoneNumber=intent.getStringExtra("phoneNumber");
        person=personDao.queryOnePerson(phoneNumber);
        nameTextView.setText(person.getName());
        accountIdTextView.setText(person.getPhone_number());
    }
}
