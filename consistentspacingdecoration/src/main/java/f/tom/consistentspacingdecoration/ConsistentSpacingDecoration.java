package f.tom.consistentspacingdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Tom on 20-5-2017.
 *
 * This class is basically an itemOffsetDecoration for GridlayoutManagers. It makes sure the spacing between items in consistent in all dimensions,
 * meaning that the padding between items is equal to the padding on the edges of the grid.
 *
 * It works for both horizontal and recyclerviews, any amount of columns and supports headers.
 */

public class ConsistentSpacingDecoration extends RecyclerView.ItemDecoration {
    // I'm using half and whole offsets, because the items in the middle otherwise get double padding( paddingleft from the rightmost item and paddingright from the leftmost).
    // this makes the margin consistent on the sides and between the columns.
    private int horizontalOffset;
    private int halfHorizontalOffset;
    private int verticalOffset;
    private int halfVerticalOffset;

    private int numOfColumns;
    private boolean header; // the header is the first item. Enable this if your first item spans all columns
    private boolean usePaddingForHeader; // when disabled, the header has zero padding

    public ConsistentSpacingDecoration(int horizontalPaddingPx, int verticalPaddingPx, int columns) {
        this.numOfColumns = columns;
        this.verticalOffset = verticalPaddingPx;
        this.horizontalOffset = horizontalPaddingPx;
        this.halfHorizontalOffset = horizontalPaddingPx / 2;
        this.halfVerticalOffset = verticalPaddingPx / 2;
    }


    /**
     * @param enabled whether you added a "header" that spans all columns to your Recyclerview with GridLayoutManager.setSpanSizeLookup.
     * @param usePadding if you want the header to have padding.
     */
    public void setHeaderEnabled(boolean enabled, boolean usePadding){
        this.header= enabled;
        this.usePaddingForHeader = usePadding;
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int numOfRows;
        int column;
        int row;

        if (header) {
            // the header spans multiple columns off course, so that throws normal calculation of. The minus one fixes that.
            column= (position - 1 ) % numOfColumns;
            int virtualNumOfViews = parent.getAdapter().getItemCount() + numOfColumns -1;
            // the num of rows is the amount of views there would be if instead of the header there would be normal views, divided by the amount fo columns, rounded up.
            numOfRows=  (int) Math.ceil( ((double) virtualNumOfViews / (double) numOfColumns ));
            row=  (int) Math.ceil( (double) (position)  /  (double) numOfColumns  );


        }
        else{
            int actualNumOfViews = parent.getAdapter().getItemCount(); //
            numOfRows=  (int) Math.ceil( ((double) actualNumOfViews / (double) numOfColumns ));
            column= (position ) % numOfColumns;
            row=  (int) Math.floor( ((double) (position ) )/  (double) numOfColumns  );
        }


        /**Special case for the header....**/
        if (position==0 && header){

            if (usePaddingForHeader){

                //This is the top header, so it has full padding at the top and the sides.
                outRect.top=verticalOffset;
                outRect.left= horizontalOffset;
                outRect.right=horizontalOffset;

                //the bottom also has full padding, unless this is the only item in the recyclerview!
                if (parent.getAdapter().getItemCount()==1){
                    outRect.bottom=verticalOffset;
                }else{
                    outRect.bottom=halfVerticalOffset;
                }
            }

            // the paddingless header
            else {
                outRect.set(0, 0, 0, 0);
            }

            return;
        }



        /**For the columns.**/
        // if there's only one column, the item needs full padding left and right
        if (numOfColumns==1){
            outRect.right= horizontalOffset;
            outRect.left=horizontalOffset;
        }
        // if this is the leftmost column
        else if( column==0){
            outRect.left= horizontalOffset;
            outRect.right= halfHorizontalOffset;

        }
        // column is the right most
        else if (column== numOfColumns-1){
            outRect.right= horizontalOffset;
            outRect.left= halfHorizontalOffset;

        }
        // if this is a middle column, padding is half at each item
        else{
            outRect.left= halfHorizontalOffset;
            outRect.right= halfHorizontalOffset;
        }




        /**For the rows.**/
        // if there is only one row, that row needs full padding at top and bottom
        if (numOfRows==1){
            outRect.top=verticalOffset;
            outRect.bottom= verticalOffset;
        }

        //the top row has large padding at the top
        else if (row==0){
            outRect.top= verticalOffset;
            outRect.bottom=halfVerticalOffset;
        }
        // the bottom row has large padding at the bottom
        else if ( row== numOfRows-1){
            outRect.bottom= verticalOffset;
            outRect.top= halfVerticalOffset;
        }
        // all other rows have half padding at the top and bottom
        else{
            outRect.top= halfVerticalOffset;
            outRect.bottom= halfVerticalOffset;
        }
    }

}