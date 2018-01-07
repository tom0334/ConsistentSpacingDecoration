package f.tom.consistentspacingdemo;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

import f.tom.consistentspacingdecoration.ConsistentSpacingDecoration;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RecyclerViewData> data;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recv;
    private GridLayoutManager gridMan;


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
        for (int i = 0; i < 25; i++) {
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
        adapter = new MyRecyclerViewAdapter(data);
        recv.setAdapter(adapter);

        gridMan = new GridLayoutManager(this, columnCount);
        recv.setLayoutManager(gridMan);
        adapter.notifyDataSetChanged();
    }


}
