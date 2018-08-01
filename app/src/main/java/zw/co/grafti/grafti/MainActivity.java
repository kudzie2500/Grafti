package zw.co.grafti.grafti;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText search;
    Spinner categories;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(MainActivity.this);
        //getting value of spinner
        // String.valueOf(idOfSpinner.getSelectedItem());
    }

    public void search_pressed(View view){
        String searchVal, categoriesVal;
        search = (EditText)findViewById(R.id.txtSearch);
        categories = (Spinner)findViewById(R.id.category);
        searchVal = search.getText().toString();
        categoriesVal = String.valueOf(categories.getSelectedItem());

        Cursor cursor = databaseHelper.fetchQuery(searchVal, categoriesVal);
        if(cursor.getCount() > 0){
            Bundle extras = new Bundle();
            extras.putString("skill", searchVal);
            extras.putString("cat", categoriesVal);
            Intent i = new Intent(MainActivity.this, SearchResults.class);
            i.putExtras(extras);
            startActivity(i);
        }else {
            Toast.makeText(MainActivity.this, "No Results found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
    public void login_signup(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choose action");
        builder.setPositiveButton("sign up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent i = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(i);
                }catch (Exception e){e.printStackTrace();}
            }
        });
        builder.setNegativeButton("login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }catch (Exception e){ e.printStackTrace();}
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
