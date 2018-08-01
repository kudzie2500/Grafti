package zw.co.grafti.grafti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

public class SearchResults extends AppCompatActivity {
    ListView listView;
    Cursor cursor;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        databaseHelper = new DatabaseHelper(SearchResults.this);

        Bundle bundle = getIntent().getExtras();
        String searchVal = bundle.getString("skill");
        String searchCat = bundle.getString("cat");

        listView = (ListView)findViewById(R.id.lstResults);
        cursor = databaseHelper.fetchQuery(searchVal, searchCat);

        String[] from = {databaseHelper.KEY_Name, databaseHelper.KEY_Descr};
        int[] to = {R.id.LiTemName, R.id.LiTemDesc};

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to, 0);
        listView.setAdapter(adapter);

        registerClicks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchResults.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                showResults(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    void registerClicks(){
        try{
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(SearchResults.this, "You clicked on one of the list items", Toast.LENGTH_SHORT).show();

                    cursor.moveToPosition(position);
                    int rowId =cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    final String desc = databaseHelper.getDescription(rowId);
                    final String phone = databaseHelper.getNumber(rowId);

                    AlertDialog.Builder callOption = new AlertDialog.Builder(SearchResults.this);
                    callOption.setTitle(databaseHelper.getName(rowId));
                    callOption.setMessage(desc + "\n phone number \n"+phone);
                    callOption.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callNow(phone);
                        }
                    });
                    callOption.setNegativeButton("View profile", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Dialog with all detail
                        }
                    });

                    AlertDialog dialog = callOption.create();
                    dialog.show();
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }

    public void showResults(String term){
        cursor = databaseHelper.fetchQuery(term, null);
        String[] from = {databaseHelper.KEY_Name, databaseHelper.KEY_Descr};
        int[] to = {R.id.LiTemName, R.id.LiTemDesc};

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to, 0);
        listView.setAdapter(adapter);

        registerClicks();
    }

    void callNow(String number){
        //commands to perform callling
        //TODO callNow function to be done
    }

}
