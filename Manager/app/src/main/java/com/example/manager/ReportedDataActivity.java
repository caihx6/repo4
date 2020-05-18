package com.example.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manager.dao.PersonDao;
import com.example.manager.dao.RecordDao;
import com.example.manager.entity.Person;
import com.example.manager.entity.Record;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.util.Date;

public class ReportedDataActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView accountIdTextView;
    private EditText temperatureEditText;
    private PersonDao personDao;
    private RecordDao recordDao;
    private Person person;
    public static final int DEFAULT_VIEW = 0x22;

    private static final int REQUEST_CODE_SCAN = 0X01;
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
        findViewById(R.id.outDoor_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //DEFAULT_VIEW为用户自定义用于接收权限校验结果
                ActivityCompat.requestPermissions(
                        ReportedDataActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        //DEFINED_CODE
                        DEFAULT_VIEW);            }
        });

    }
   /* public void newViewBtnClick(View view) {
        //DEFAULT_VIEW为用户自定义用于接收权限校验结果
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                //DEFINED_CODE
                DEFAULT_VIEW);
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == DEFAULT_VIEW) {
            //调用DefaultView扫码界面
            ScanUtil.startScan(ReportedDataActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //当扫码页面结束后，处理扫码结果
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        //从onActivityResult返回data中，用 ScanUtil.RESULT作为key值取到HmsScan返回值
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    //Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
                    if(((HmsScan) obj).getOriginalValue()==""){
                        setFalg(1);
                        Toast.makeText(ReportedDataActivity.this, "疫情期间，请在两小时内返回小区！", Toast.LENGTH_LONG).show();
                    }else if(((HmsScan) obj).getOriginalValue()=="")
                    {
                        int falg=getFalg();
                        if(falg==1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ReportedDataActivity.this);
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View v1 = layoutInflater.inflate(R.layout.activity_scan_result, null);
                            builder.create();
                            builder.setView(v1);
                            builder.setTitle("权限验证");
                            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setFalg(0);
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }else{
                            Toast.makeText(ReportedDataActivity.this, "您不是本小区居民，疫情期间请勿走动，如有必要，请联系小区负责人！", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                return;
            }
        }
    }

    private void setFalg(int falg){
        SharedPreferences sp=getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putInt("falg",falg);
        ed.commit();
    }
    private int getFalg(){
        SharedPreferences sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        return sharedPreferences.getInt("falg",0);
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
