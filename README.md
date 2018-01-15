# ConsistentSpacingDecoration
A RecyclerViewItemDecoration that provides consistent spacing on all 4 sides when working with GridlayoutManager

When using gridlayoutmanager with cards and padding/margin on your listitems, the spacing between items is doubled. This librfary fixes that.


WITHOUT ConsistentSpacingDecoration  |  WITH ConsistentSpacingDecoration:
:-------------------------:|:-------------------------:
![bad image](https://github.com/tom0334/ConsistentSpacingDecoration/blob/master/screenshots/screenshot_bad.png)  |  ![bad image](https://github.com/tom0334/ConsistentSpacingDecoration/blob/master/screenshots/screenshot_good.png)


Note the the padding between the items. 

It also supports:
* Vertical padding (the vertical padding is also consistent)
* N columns and M rows
* Headers that span all columns
* Headers that have no padding at all

How to use:

        RecyclerView recv = findViewById(R.id.mainRecyclerView);
        int paddingPxVertical = getResources().getDimensionPixelSize(R.dimen.recyclerViewSpacing);
        int paddingPxHorizontal=getResources().getDimensionPixelSize(R.dimen.recyclerViewSpacing);
                
        ConsistentSpacingDecoration betterSpacing = new ConsistentSpacingDecoration(
                paddingPxHorizontal,
                paddingPxVertical,  
                columnCount // the amount of columns your gridlayoutmanager uses. 1 if using linear
        );
        // optional
        betterSpacing.setHeaderEnabled(useHeader, headerPadding);
        
        // add it to your recyclerview, and that's it!
        recv.addItemDecoration(betterSpacing);


