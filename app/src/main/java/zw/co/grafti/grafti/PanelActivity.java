package zw.co.grafti.grafti;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

public class PanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper databaseHelper;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(PanelActivity.this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        //sets the initial view a user sees when the activity starts
        PanelWelcome_fragment fragment = new PanelWelcome_fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_app_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //Fragment fragment = null;
        //Class fragmentClass;

       // int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.welcome:{
                PanelWelcome_fragment fragment = new PanelWelcome_fragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.commit();
                break;
            }
            case R.id.changePass:{
                ChangePass_fragment fragment = new ChangePass_fragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.commit();
                break;
            }
            case R.id.edit_info:{
                ModifyDetails_fragment fragment = new ModifyDetails_fragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.commit();
                break;
            }
            case R.id.delete_acc:{
                DeleteAccount_fragment fragment = new DeleteAccount_fragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.commit();
                break;
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /**------------------------------------ methods for change password fragment ----------------------------------*/

    public void checkPass(View view){
        String oldp, nPas, vPas;
        EditText oldPassword, newPassword, verPassword;
        TextView warning;
        boolean flag = true;     // to become false if form has empty fields or errors exist on passwords

        oldPassword = (EditText)findViewById(R.id.txtOldPass);
        newPassword = (EditText)findViewById(R.id.txtNewPass);
        verPassword = (EditText)findViewById(R.id.txtVPass);
        warning     = (TextView)findViewById(R.id.tvPassN);

        oldp = oldPassword.getText().toString();
        nPas = newPassword.getText().toString();
        vPas = verPassword.getText().toString();

        if(oldp.equals("") || nPas.equals("") || vPas.equals("")){
            warning.setText("Text fields are empty");
            warning.setTextColor(Color.RED);
            oldPassword.getText().clear();
            newPassword.getText().clear();
            verPassword.getText().clear();
            flag = false;
        }else if (oldp.equals(nPas) || oldp.equals(vPas)){
            warning.setText("Old Password should not match the new password");
            warning.setTextColor(Color.RED);
            oldPassword.getText().clear();
            newPassword.getText().clear();
            verPassword.getText().clear();
            flag = false;
        }else if (!nPas.equals(vPas)){
            warning.setText("New password and Verification do not match");
            warning.setTextColor(Color.RED);
            oldPassword.getText().clear();
            newPassword.getText().clear();
            verPassword.getText().clear();
            flag = false;
        }else if (nPas.length() < 6 || vPas.length() < 6){
            warning.setText("Password too short, must be longer than 6 characters");
            warning.setTextColor(Color.RED);
            oldPassword.getText().clear();
            newPassword.getText().clear();
            verPassword.getText().clear();
            flag = false;
        }else{
            flag = true;
        }

        if (flag){
            if (id>-1) {
                try {
                    if (databaseHelper.passUpdate(id, nPas)) {
                        Toast.makeText(PanelActivity.this, "Password successfully changed", Toast.LENGTH_SHORT).show();
                        oldPassword.getText().clear();
                        newPassword.getText().clear();
                        verPassword.getText().clear();
                        warning.setText("Password Successfully changed");
                        warning.setTextColor(Color.GREEN);
                    } else {
                        Toast.makeText(PanelActivity.this, "Password change unsuccessful", Toast.LENGTH_SHORT).show();
                        warning.setText("Unable to change password");
                        warning.setTextColor(Color.RED);
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }


    /***************************************** editing account **************************************/

    public void accountEdit(View view){
        EditText phone, mail, cityTwn, locat, descr,charge;
        Spinner cat;
        phone = (EditText)findViewById(R.id.edModPhone);
        mail = (EditText)findViewById(R.id.edModEmail);
        cityTwn = (EditText)findViewById(R.id.edModCityTown);
        locat = (EditText)findViewById(R.id.edModLocation);
        descr = (EditText)findViewById(R.id.edModDesc);
        cat = (Spinner)findViewById(R.id.ModCategory);
        charge = (EditText)findViewById(R.id.edModCharge);

        String szPhone, szMail, szCityTown, szLocat, szDescr, szCat, szCharge;
        szPhone = phone.getText().toString();
        szMail = mail.getText().toString();
        szCityTown = cityTwn.getText().toString();
        szLocat = locat.getText().toString();
        szDescr = descr.getText().toString();
        szCat = String.valueOf(cat.getSelectedItem());
        szCharge = charge.getText().toString();

        if (szCat.equals(""))
            szCat = null;
        if(szCharge.equals(""))
            szCat = null;
        if (szCityTown.equals(""))
            szCityTown = null;
        if (szDescr.equals(""))
            szDescr = null;
        if (szLocat.equals(""))
            szLocat = null;
        if (szMail.equals(""))
            szMail = null;
        if (szPhone.equals(""))
            szPhone = null;

        String[] data = {szPhone, szMail, szCityTown, szLocat, szDescr, szCat, szCharge};
        if(id>-1) {
            if (databaseHelper.accEdit(data, id))
                Toast.makeText(PanelActivity.this, "Account details updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(PanelActivity.this, "Error: Operation unsuccessful", Toast.LENGTH_SHORT).show();
        }

    }

    /****************************************** deleting account ***********************************/

    public void deleteAccnt(View view){
        if (id>-1) {
            if (databaseHelper.deleteAcc(id)) {
                Toast.makeText(PanelActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                PanelActivity.this.finish();
            }
        }
    }
}
