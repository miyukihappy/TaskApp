package jp.techacademy.miyuki.manabe.taskapp;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject implements Serializable {

    String name;

    // id をプライマリーキーとして設定
    @PrimaryKey
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.getId() + " : " + this.getName();
    }
}
