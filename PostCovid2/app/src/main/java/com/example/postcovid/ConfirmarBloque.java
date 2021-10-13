package com.example.postcovid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.postcovid.ui.login.LoginActivity;

import java.util.List;

import io.paperdb.Paper;

public class ConfirmarBloque extends AppCompatActivity {

    PatternLockView mPatterLockView;
    String patronBase = "patron";
    String patronFinal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_bloque);

        Paper.init(this);
        final String patronGuardado = Paper.book().read(patronBase);
        if (!(patronGuardado != null) && !patronGuardado.equals("null")) {

            setContentView(R.layout.activity_confirmar_bloque);
            mPatterLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatterLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                     patronFinal = PatternLockUtils.patternToString(mPatterLockView, pattern);
                    if (patronFinal.equals(patronGuardado)) {
                        Toast.makeText(ConfirmarBloque.this, "Patron CORRECTO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfirmarBloque.this, LoginActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(ConfirmarBloque.this, "Patron INCORRECTO", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {

                }
            });
        }

    }
}