package f.tom.consistentspacingdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import f.tom.consistentspacingdecoration.ConsistentSpacingDecoration;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RecyclerViewData> data;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recv;
    private GridLayoutManager gridMan;
    private boolean useConsistentSpacing = true;

    //the better way
    private ConsistentSpacingDecoration betterSpacing;

    private int columnCount;
    private boolean useHeader = false;
    private boolean headerNoPadding = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recv = findViewById(R.id.mainRecyclerView);

        this.columnCount = 2;

        //craete some dummy data
        data = new ArrayList<>(10);
        for (int i = 0; i < 11; i++) {
            data.add(new RecyclerViewData(i));
        }

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addView:
                addItem();
                break;
            case R.id.editViews:
                showEditNumOfColumnsDialog();
                break;
        }
        return false;
    }

    public void update(){

        //remove the old from the recyclerview.
        if (this.betterSpacing!=null){
            recv.removeItemDecoration(betterSpacing);
        }

        if (useConsistentSpacing){
            addConsistentDecoration();
        }

        //reinit the recyclerview adapter with the new settings
        initRecyclerView();
    }

    public void toggleBadSpacing(View v){
        this.useConsistentSpacing = !useConsistentSpacing;
        update();
    }

    private void addItem() {
        data.add(new RecyclerViewData( data.size()-1));
        adapter.notifyItemInserted( data.size()-1);
    }

    private void showEditNumOfColumnsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick number of columns");

        final ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for (int i = 1; i <11 ; i++) {
            adap.add( i + " Columns" );
        }

        builder.setAdapter(adap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                changeNumOfColumns(i +1);
            }
        });
        builder.show();
    }
    private void changeNumOfColumns(int newNum){
        this.columnCount= newNum;
        update();
    }

    private void addConsistentDecoration() {
        //This shows how to use this libary

        //get size from dimensions. In this case, 16dp
        int paddingPX = getResources().getDimensionPixelSize(R.dimen.recyclerViewSpacing);
        //create the decoration
        this.betterSpacing = new ConsistentSpacingDecoration(
                paddingPX, //the vertical margin between items in px
                paddingPX,  //horizontal
                columnCount, // the amount of columns your gridlayoutmanager uses. 1 if using linear
                useHeader,  // if you are using a header that spans all columns
                headerNoPadding // if you want that header to have no padding at all. Doesnt have any effect if useheader==false
        );
        // add it to your recyclerview, and that's it!
        recv.addItemDecoration(betterSpacing);
    }

    private void initRecyclerView() {
        adapter = new MyRecyclerViewAdapter(data, useConsistentSpacing);
        recv.setAdapter(adapter);

        gridMan = new GridLayoutManager(this, columnCount);
        recv.setLayoutManager(gridMan);
        adapter.notifyDataSetChanged();
    }


}
