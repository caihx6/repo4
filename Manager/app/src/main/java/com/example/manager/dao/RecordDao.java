package com.example.manager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manager.utils.DatabaseHelper;
import com.example.manager.dto.PersonAndRecord;
import com.example.manager.entity.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private SQLiteDatabase myDb;
    private DatabaseHelper databaseHelper;

    public RecordDao(Context context) {
        databaseHelper = new DatabaseHelper(context, "manage.db", null, 1);
        myDb = databaseHelper.getReadableDatabase();
    }

    /**
     * 添加一条个人健康信息上报
     * @param record
     */
    public void addPecord(Record record) {
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL
                ("insert into records(person_id, temperature, date) " + "values(?, ?, ?)",
                        new String[] {record.getPersonId()+"",
                                record.getTemperature(),
                                record.getDate()+""}
                );
    }

    /**
     * 删除一条记录
     * @param id
     */
    public void deleteRecord(Integer id)
    {
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from records where id=?", new String[] {id+""});
    }


    /**
     * 查询所有健康记录，persons和records联合查询
     * @return
     */
    public List  qureyAllRecords()  {
        List<PersonAndRecord> personAndRecordsList = new ArrayList<>(); //记录列表
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Cursor cursor = sqLiteDatabase.rawQuery("select persons.person_name,persons.phone_number,persons.id_card,persons.address,records.*" +
                " from records,persons where records.person_id=persons.id", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    personAndRecordsList.add(new PersonAndRecord(
                            cursor.getString(cursor.getColumnIndex("person_name")),
                            cursor.getString(cursor.getColumnIndex("phone_number")),
                            cursor.getString(cursor.getColumnIndex("id_card")),
                            cursor.getString(cursor.getColumnIndex("address")),
                            new Record(cursor.getInt(cursor.getColumnIndex("person_id")),
                                       cursor.getString(cursor.getColumnIndex("temperature")),
                                       simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("date"))))
                                     ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return personAndRecordsList;
    }

    /**
     * 查询个人的健康上报记录
     * @param personId
     * @return
     */
    public List queryOnePersonRecord(Integer personId) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        List<PersonAndRecord> personAndRecordsList=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Cursor cursor = sqLiteDatabase.rawQuery("select persons.person_name,persons.phone_number,persons.id_card,persons.address,records.*" +
                " from records,persons where records.person_id=persons.id and records.person_id=?", new String[]{personId+""});
        if (cursor.moveToFirst()) {
            do {
                try {
                    personAndRecordsList.add(new PersonAndRecord(
                            cursor.getString(cursor.getColumnIndex("person_name")),
                            cursor.getString(cursor.getColumnIndex("phone_number")),
                            cursor.getString(cursor.getColumnIndex("id_card")),
                            cursor.getString(cursor.getColumnIndex("address")),
                            new Record(cursor.getInt(cursor.getColumnIndex("person_id")),
                                    cursor.getString(cursor.getColumnIndex("temperature")),
                                    simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("date"))))
                    ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return personAndRecordsList;
    }
}
