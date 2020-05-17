package com.example.manager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manager.utils.DatabaseHelper;
import com.example.manager.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private SQLiteDatabase myDb;
    private DatabaseHelper databaseHelper;

    public PersonDao(Context context) {
        databaseHelper = new DatabaseHelper(context, "manage.db", null, 1);
        myDb = databaseHelper.getReadableDatabase();
    }

    /**
     * 添加一个居民
     * @param person
     */
    public void addPerson(Person person) {
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL
                ("insert into persons(person_name, phone_number, password, id_card,address) " + "values(?, ?, ?, ?, ?)",
                        new String[] {person.getName(),
                                      person.getPhone_number(),
                                      person.getPassword(),
                                      person.getId_Card(),
                                      person.getAddress()}
                );
    }

    /**
     * 删除一个账户
     * @param id
     */
    public void deletePerson(Integer id)
    {
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from persons where id=?", new String[] {id+""});
    }

    /**
     * 修改居民信息
     * @param person
     */
    public void updatePerson(Person person)
    {
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update persons set person_name=?, phone_number=?, password=?, id_card=?,address=? ",
                new String[] {person.getName(),
                        person.getPhone_number(),
                        person.getPassword(),
                        person.getId_Card(),
                        person.getAddress()}
        );
    }

    /**
     * 查询所有居民信息
     * @return
     */
    public List queryAllPerson() {
        List<Person> personsList = new ArrayList<>(); //居民列表
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from persons", null);
        if (cursor.moveToFirst()) {
            do {
                personsList.add(new Person(
                        cursor.getString(cursor.getColumnIndex("person_name")),
                        cursor.getString(cursor.getColumnIndex("phone_number")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("id_card")),
                        cursor.getString(cursor.getColumnIndex("address"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return personsList;
    }

    /**
     * 登录账号是手机号，因此使用手机号进行查表
     * @param phoneNumber 手机号
     * @return
     */
    public Person queryOnePerson(String phoneNumber){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from persons where phone_number=?", new String[] {phoneNumber});
        Person person=null;
        if (cursor.moveToFirst()) {
            do {
                person=new Person(
                        cursor.getString(cursor.getColumnIndex("person_name")),
                        cursor.getString(cursor.getColumnIndex("id_card")),
                        cursor.getString(cursor.getColumnIndex("address")),
                        cursor.getString(cursor.getColumnIndex("phone_number")),
                        cursor.getString(cursor.getColumnIndex("password")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return person;
    }

    /**
     * 通过手机号查询居民id
     * @param phoneNumber
     * @return
     */
    public  Integer quuryPersonId(String phoneNumber){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select id from persons where phone_number=?", new String[] {phoneNumber});
        Integer id=null;
        if (cursor.moveToFirst()) {
            do {
                        id=cursor.getInt(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;
    }


}
