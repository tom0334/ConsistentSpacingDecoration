package f.tom.consistentspacingdemo;

/**
 * Created by Tom on 7-1-2018.
 */

class RecyclerViewData {
    private int itemPos;

    public RecyclerViewData(int itemPos) {
        this.itemPos = itemPos;
    }

    public String getItemText(){
        return "Item " + (itemPos);
    }
}
