package exsample.yamada.mycarousel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CarouselContentView> frameLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = LayoutInflater.from(this);
        frameLayouts = new ArrayList<>();
        CarouselContentView contentView = (CarouselContentView) inflater.inflate(R.layout.carousel_content, null);
        contentView.setTitle("一枚目");
        contentView.setBackgroundColor(Color.RED);
        frameLayouts.add(contentView);

        CarouselContentView contentView2 = (CarouselContentView) inflater.inflate(R.layout.carousel_content, null);
        contentView2.setTitle("二枚目");
        contentView2.setBackgroundColor(Color.BLUE);
        frameLayouts.add(contentView2);

        CarouselContentView contentView3 = (CarouselContentView) inflater.inflate(R.layout.carousel_content, null);
        contentView3.setTitle("三枚目");
        contentView3.setBackgroundColor(Color.GREEN);
        frameLayouts.add(contentView3);

        CarouselContentView contentView4 = (CarouselContentView) inflater.inflate(R.layout.carousel_content, null);
        contentView4.setTitle("四枚目");
        contentView4.setBackgroundColor(Color.YELLOW);
        frameLayouts.add(contentView4);

        LoopedViewPager carouselView = (LoopedViewPager) findViewById(R.id.pager);
        carouselView.initialize(this, frameLayouts.size(), new LoopedViewPager.LoopedViewPagerListener() {
            @Override
            public View OnInstantiateItem(int page) {
                return frameLayouts.get(page);
            }

            @Override
            public void onPageScrollChanged(int page) {
            }
        });

        LoopPageIndicator pageIndicator = new LoopPageIndicator(this, (LinearLayout) findViewById(R.id.pages_container), carouselView, R.drawable.indicator_circle);
        pageIndicator.setPageCount(frameLayouts.size());
        pageIndicator.show();

        carouselView.startMoveTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((LoopedViewPager) findViewById(R.id.pager)).startMoveTimer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ((LoopedViewPager) findViewById(R.id.pager)).stopMoveTimer();
    }
}
