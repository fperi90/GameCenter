package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private DBHelper helper;
    private Bundle datos;
    private EditText regEditTextUser;
    private EditText regTextPassword;
    private EditText repeatPassword;
    private User user;
    private EditText repeatChangePassword;
    private Button newUserButton;
    private Button changePasswordButton;
    private Button changePaswordHiddenButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        datos = getIntent().getExtras();
        helper = new DBHelper(this);
        /*   helper = (DBHelper) datos.getSerializable("BDHelper");*/

        newUserButton = (Button) findViewById(R.id.regActRegisterButton);
        newUserButton.setVisibility(View.VISIBLE);

        changePasswordButton = (Button) findViewById(R.id.changeRegisterButton);
        changePasswordButton.setVisibility(View.VISIBLE);

        changePaswordHiddenButton =
                (Button) findViewById(R.id.changePasswordButton);
        changePaswordHiddenButton.setVisibility(View.GONE);

        regEditTextUser = (EditText) findViewById(R.id.registerEditTextUser);
        regTextPassword = (EditText) findViewById(R.id.registerEditTextPassword);


        repeatPassword = (EditText) findViewById(R.id.repeatEditTextPassword);
        repeatChangePassword =
                (EditText) findViewById(R.id.repeatChangeTextPassword);
        repeatChangePassword.setVisibility(View.GONE);

        LinearLayout registerLayout = findViewById(R.id.registerMainLayout);
        registerLayout.setAlpha(0f);
        registerLayout.animate().alpha(1f).setDuration(1500).start();
    }

    public void addNewUser(View view) {
        String password = regTextPassword.getText().toString();
        String repeatedPassword = repeatPassword.getText().toString();
        if (!password.equals(repeatedPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden.",
                    Toast.LENGTH_LONG).show();
        } else {
            String username = String.valueOf(regEditTextUser.getText());
            user = new User(username, password);
            helper.addUser(user);
            Toast.makeText(this, "Nuevo usuario creado satisfactoriamente",
                    Toast.LENGTH_LONG).show();
            toLoginActivity();
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


    public void changePassword(View view) {
        String username = regEditTextUser.getText().toString();
        String password = regTextPassword.getText().toString();
        String newPassword = repeatPassword.getText().toString();
        String repeatedNewPassword = repeatChangePassword.getText().toString();
        if (newPassword.equals(repeatedNewPassword)) {
            if (helper.checkPassword(username, password)) {
                helper.updateUser(username, password, newPassword);
                Toast.makeText(this, "Contraseña cambiada con exito",
                        Toast.LENGTH_LONG).show();
                toLoginActivity();
            }else{
                Toast.makeText(this, "Usuario y contraseña antigua no " +
                                "coinciden",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Las contraseñas nuevas no coinciden.",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void notifyChanges(View view) {
        newUserButton.setVisibility(View.GONE);
        changePasswordButton.setVisibility(View.GONE);
        repeatChangePassword.setVisibility(View.VISIBLE);
        regTextPassword.setHint("Introduce la antigua contraseña");
        repeatPassword.setHint("Introduce nueva contraseña");
        repeatChangePassword.setHint("Vuelve a introducir la contraseña");
        repeatChangePassword.setVisibility(View.VISIBLE);
        changePaswordHiddenButton.setVisibility(View.VISIBLE);
    }
}
