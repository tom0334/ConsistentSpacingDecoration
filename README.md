
# ConsistentSpacingDecoration
A RecyclerView add-on that provides consistent spacing on all 4 sides when using a GridlayoutManager. Basically a [DividerItemDecoration](https://developer.android.com/reference/android/support/v7/widget/DividerItemDecoration.html), but for recyclerviews with any amount of columns.

You cannot simply use padding or magin on your listitems, as space between two items will be double that of the top and bottom space. This library fixes that. See the image below.


WITHOUT ConsistentSpacingDecoration  |  WITH ConsistentSpacingDecoration:
:-------------------------:|:-------------------------:
![bad image](https://github.com/tom0334/ConsistentSpacingDecoration/blob/master/screenshots/screenshot_bad.png)  |  ![bad image](https://github.com/tom0334/ConsistentSpacingDecoration/blob/master/screenshots/screenshot_good.png)


Note the the padding between the items.

It supports:
* Vertical padding (the vertical padding is also consistent)
* N columns and M rows
* Headers that span all columns
* Headers that have no padding at all
* Both Vertical and horizontal recyclerviews

----------

**How to install:**

Edit your PROJECT build.gradle file to include the following line under "allprojects" (not "buildscript"!):

`maven { url 'https://jitpack.io' }`

Like so:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Then add the following line to your app build.gradle file:

	dependencies {
	        compile 'com.github.tom0334:ConsistentSpacingDecoration:1.0.1'
	}




**How to use:**

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


