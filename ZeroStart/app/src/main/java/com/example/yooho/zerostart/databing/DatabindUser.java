package com.example.yooho.zerostart.databing;

import android.databinding.ObservableList;

import java.util.Map;

public class DatabindUser {
    private String name;
    private String localtion;
    private int age;
    private ObservableList<String> hobby;

    public ObservableList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ObservableList<String> hobby) {
        this.hobby = hobby;
    }

    private Map<String, Integer> score;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Map<String, Integer> getScore() {
        return score;
    }

    public void setScore(Map<String, Integer> score) {
        this.score = score;
    }


}
