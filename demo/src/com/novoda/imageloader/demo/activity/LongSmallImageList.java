package com.novoda.imageloader.demo.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.novoda.imageloader.demo.DemoApplication;
import com.novoda.imageloader.demo.R;
import com.novoda.imageloader.demo.activity.base.SingleTableBaseListActivity;

/**
 * Very similar to imageLongList example.
 */
public class LongSmallImageList extends SingleTableBaseListActivity {

    private static final int SIZE = 80;

    private Animation fadeInAnimation;
    private Boolean isAnimated = false;

    /**
     * TODO
     * Generally we can keep an instance of the 
     * image loader and the imageTagFactory.
     */
    private ImageManager imageManager;
    private ImageTagFactory imageTagFactory;

    @Override
    protected String getTableName() {
        return LongSmallImageList.class.getSimpleName().toLowerCase();
    }
    
    @Override
    protected int getImageItem() {
        return R.layout.small_image_item;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_table_base_list_activity);

        if (getIntent().hasExtra("animated")) {
            isAnimated = true;
            fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        }

        /**
         * TODO
         * Need to prepare imageLoader and imageTagFactory
         */
        imageManager = DemoApplication.getImageLoader();
        imageTagFactory = new ImageTagFactory(SIZE, SIZE, R.drawable.bg_img_loading);
        imageTagFactory.setErrorImageId(R.drawable.bg_img_notfound);
        imageTagFactory.setSaveThumbnail(true);
        setAdapter();
        
        //added by dwa012
        Button button = (Button) this.findViewById(R.id.refresh_button);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				refreshData();
			
				
			}
        	
        });
    }
    
    /**
     * TODO
     * Generally you will have a binder where you have to set the image.
     * This is an example of using the imageManager to load
     */
    @Override
    protected ViewBinder getViewBinder() {
        return new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                String url = cursor.getString(columnIndex);
                ((ImageView) view).setTag(imageTagFactory.build(url));

                if (!isAnimated) {
                    imageManager.getLoader().load((ImageView) view);
                } else{
                    imageManager.getLoader().load((ImageView) view, fadeInAnimation);
                }

                return true;
            }

        };
    }

}