package exsample.yamada.mycarousel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CarouselContentView extends FrameLayout {

    public CarouselContentView(Context context) {
        super(context);
    }

    public CarouselContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarouselContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String title) {
        ((TextView)findViewById(R.id.carousel_text)).setText(title);
    }
}