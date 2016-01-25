package com.github.programmerr47.vkgroups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.github.programmerr47.vkgroups.pager.pages.GroupDetailPage;

import static com.github.programmerr47.vkgroups.VKGroupApplication.getImageWorker;

/**
 * @author Michael Spitsin
 * @since 2016-01-23
 */
public class TestDetailActivity extends AppCompatActivity {

    private GroupDetailPage page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String image = getIntent().getStringExtra("TEST_IMAGE");

        page = new GroupDetailPage(this);
        setContentView(page.getAttachedView());
        getImageWorker().loadImage(
                image,
                (ImageView) page.getAttachedView().findViewById(R.id.group_image),
                new ImageWorker.LoadBitmapParams(200, 200, false));
    }
}
