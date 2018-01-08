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
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import f.tom.consistentspacingdecoration.ConsistentSpacingDecoration;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RecyclerViewData> data;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recv;
    private GridLayoutManager gridMan;
    private boolean useBadSpacing=false;


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

        initRecyclerView();
        applyDecoration();
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
                editNumOfColumns();
                break;
            case R.id.toggleBad:
                toggleBadSpacing();
                break;
        }

        return false;
    }

    private void toggleBadSpacing(){
        this.useBadSpacing= !useBadSpacing;

        //remove the old from the recyclerview.
        if (this.betterSpacing!=null){
            recv.removeItemDecoration(betterSpacing);
        }

        if ( ! useBadSpacing){
            applyDecoration();
        }
        //reinit the recyclerview
        initRecyclerView();
    }

    private void addItem() {
        data.add(new RecyclerViewData( data.size()-1));
        adapter.notifyItemInserted( data.size()-1);
    }

    private void editNumOfColumns() {
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

        //this is to not create a new one each time. Remove the old one, or you will get double spacing!
        if (betterSpacing!=null) {
            this.recv.removeItemDecoration(betterSpacing);
        }

        //init recyclerview creates a new gridlayoutmanager with the amount of columns
        initRecyclerView();

        //reapply the decoration
        applyDecoration();

    }

    private void applyDecoration() {
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
        adapter = new MyRecyclerViewAdapter(data, useBadSpacing);
        recv.setAdapter(adapter);

        gridMan = new GridLayoutManager(this, columnCount);
        recv.setLayoutManager(gridMan);
        adapter.notifyDataSetChanged();
    }


}
