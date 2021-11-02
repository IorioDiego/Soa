package com.example.covid;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

public interface IUserRegister {
    interface View {
        void registrarse(String environment, String name, String lastname, String dni, String email, String pssw, String com, String group);

        Context getContexto();

        Object getSystemService();
    }

    interface Presenter {
        void registrarse(String environment, String name, String lastname, String dni, String email, String pssw, String com, String group);

        // void registrarEvento(String env, String event, String dec,String token);

    }

    interface Model {

    }

}
