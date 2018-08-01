package zw.co.grafti.grafti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usr, pass;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(LoginActivity.this);
    }

    public void login(View view){
        usr = (EditText)findViewById(R.id.txtMailorPhone);
        pass = (EditText)findViewById(R.id.txtLpass);
        String user = usr.getText().toString().trim();
        String pasw = pass.getText().toString().trim();

        int result = databaseHelper.authenticcate(user, pasw);
        if (result>-1){
            Bundle data = new Bundle();
            data.putInt("id", result);
            Intent intent = new Intent(LoginActivity.this, PanelActivity.class);
            intent.putExtras(data);
            startActivity(intent);
            LoginActivity.this.finish();
        }else
            Toast.makeText(LoginActivity.this, "Incorrect password or email/phone" + usr.getText().toString(), Toast.LENGTH_SHORT).show();

    }


    //TODO get id and pass it to the panel activity
}
