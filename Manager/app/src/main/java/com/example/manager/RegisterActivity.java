package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manager.dao.PersonDao;
import com.example.manager.entity.Person;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText idCardEditText;
    private EditText addressEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEdiText;
    private PersonDao personDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initEditText();
        findViewById(R.id.realResgiter_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String idCard = idCardEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String password = passwordEdiText.getText().toString();
                Person person = new Person(name, address, idCard, phoneNumber, password);
                if (name.equals("") || idCard.equals("") || address.equals("") || password.equals("") || phoneNumber.equals("")) {
                    Toast.makeText(RegisterActivity.this, "信息填写不完整，请填写全部信息！", Toast.LENGTH_SHORT).show();
                } else {
                    if (personDao.queryOnePerson(phoneNumber) == null) {
                        personDao.addPerson(person);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ReportedDataActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("phoneNumber", phoneNumber);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "该账号已经被注册，请检查输入是否正确或联系管理员！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        findViewById(R.id.cancle_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
    private void initEditText(){
        nameEditText=findViewById(R.id.name_editText);
        idCardEditText=findViewById(R.id.idCard_editText);
        addressEditText=findViewById(R.id.address_editText);
        phoneNumberEditText=findViewById(R.id.phoneNumber_editText);
        passwordEdiText=findViewById(R.id.password_editText);
        personDao=new PersonDao(this);
    }
}
