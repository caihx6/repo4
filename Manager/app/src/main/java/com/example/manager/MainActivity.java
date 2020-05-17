package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manager.dao.PersonDao;
import com.example.manager.entity.Person;

public class MainActivity extends AppCompatActivity {
    private String id;
    private String password;
    private EditText idEditText;
    private EditText passwordEditText;
    private PersonDao personDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        readAccount();
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person=personDao.queryOnePerson(id);
                if(password.equals(person.getPassword())) {
                    Intent intent=new Intent(MainActivity.this,ReportedDataActivity.class);
                    intent.putExtra("phoneNumber",id);
                    Toast.makeText(MainActivity.this, id + " 登陆成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, id + " 账号或密码错误，请检查后重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
        CheckBox checkBox=findViewById(R.id.saveAccount);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SharedPreferences sp=getSharedPreferences("info",MODE_PRIVATE);
                    SharedPreferences.Editor ed=sp.edit();
                    id=idEditText.getText().toString();
                    password=passwordEditText.getText().toString();
                    ed.putString("id",id);
                    ed.putString("password",password);
                    ed.commit();
                }
            }
        });
    }
    private void init(){
        idEditText = (EditText) findViewById(R.id.id);
        passwordEditText = (EditText) findViewById(R.id.password);
        personDao=new PersonDao(this);
    }

    public void readAccount(){
        SharedPreferences sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        if(!sharedPreferences.contains("id")) {
            id = idEditText.getText().toString();
            password = passwordEditText.getText().toString();
        }else{
            id=sharedPreferences.getString("id","");
            password=sharedPreferences.getString("password","");
        }
        idEditText.setText(id);
        passwordEditText.setText(password);
    }

}
