package zw.co.grafti.grafti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void saveAccount(View view){
        EditText name = (EditText)findViewById(R.id.txtSname);
        EditText phone = (EditText)findViewById(R.id.txtSphone);
        EditText mail = (EditText)findViewById(R.id.txtSemail);
        EditText location = (EditText)findViewById(R.id.txtSlocation);
        EditText cityTown = (EditText)findViewById(R.id.txtScityTown);
        EditText descr = (EditText)findViewById(R.id.txtSdescription);
        Spinner cat = (Spinner) findViewById(R.id.Scategory);
        EditText charge = (EditText)findViewById(R.id.txtScharge);
        EditText pass = (EditText)findViewById(R.id.txtSpassword);
        EditText vpass = (EditText)findViewById(R.id.txtSvpassword);

        String szName = name.getText().toString();
        String szPhone = phone.getText().toString();
        String szMail = mail.getText().toString();
        String szLoccation = location.getText().toString();
        String szCityTown = cityTown.getText().toString();
        String szDescr = descr.getText().toString();
        String szCat = String.valueOf(cat.getSelectedItem());
        Double dbCharge = Double.valueOf(charge.getText().toString());
        String szPass = pass.getText().toString();
        String szVPass = vpass.getText().toString();

        boolean flag = true;
        if(szName.equals(null) || szPhone.equals(null) || szLoccation.equals(null) || szDescr.equals(null) ||
                szPass.equals(null) || szVPass.equals(null) || dbCharge == null){
            Toast.makeText(SignupActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
            flag = false;
        }else  if (dbCharge < 0){
            Toast.makeText(SignupActivity.this, "Cannot charge customers negative price", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if (!szPass.equals(szVPass)){
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if (szCityTown.equals("")){
            Toast.makeText(SignupActivity.this, "Please type in the city or town where you operate", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if (flag){
            String[] data = {szName, szPhone, szMail, szLoccation, szDescr, szCat, String.valueOf(dbCharge), szPass};
            if(helper.newAccount(data)) {
                Toast.makeText(SignupActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                SignupActivity.this.finish();
            }
        }
    }
}
