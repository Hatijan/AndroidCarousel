package exsample.yamada.mycarousel;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * c_ALL_PAGE_COUNTに値を設定し、その半分の地点を初期値とするViewPager<br/>
 * 見けけ上ループしている様に見えるが、ALL_PAGE_COUNTに到達するとそれ以上進めない
 */
public class LoopedViewPager extends ViewPager {

    private static final int ALL_PAGE_COUNT = Integer.MAX_VALUE;
    private int mPages;
    private int mFirstPos;
    private int mCurrentPage;
    private int mAdapterPages;
    private LoopedViewPagerListener mListener;
    private Handler mHandler;

    private final int CAROUSEL_TIMER = 3000;

    public interface LoopedViewPagerListener {
        /**
         * ページのViewインスタンス生成要求.
         *
         * @param page 見せかけ上のページ番号.
         */
        View OnInstantiateItem(int page);

        /**
         * ページスクロール完了.
         *
         * @param page 現在のページ。0 <= page
         */
        void onPageScrollChanged(int page);
    }

    public LoopedViewPager(Context context) {
        super(context);
    }

    public LoopedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize(Context context, int pages, LoopedViewPagerListener listener) {
        if (context == null || listener == null) {
            throw new RuntimeException();
        }

        mPages = pages;

        if (pages == 0) {
            return;
        }

        if (pages == 1) {
            mAdapterPages = 1;
        } else {
            mAdapterPages = ALL_PAGE_COUNT;
        }

        mListener = listener;
        setAdapter(new MyPagerAdapter());
        addOnPageChangeListener(new MyOnPageChangeListener());

        int maxSets = ALL_PAGE_COUNT / mPages;
        mFirstPos = (maxSets / 2) * mPages;
        setCurrentItem(mFirstPos);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        startMoveTimer();
    }

    private class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mHandler != null && positionOffset != 0) {
                mHandler.removeCallbacksAndMessages(null);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage = pos2page(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                mListener.onPageScrollChanged(mCurrentPage);
                if (mHandler != null) {
                    mHandler.postDelayed(getRunnable(), CAROUSEL_TIMER);
                }
            }
        }
    }

    public void startMoveTimer() {
        if (mHandler == null) {
            mHandler = new Handler();
            mHandler.postDelayed(getRunnable(), CAROUSEL_TIMER);
        }
    }

    public void stopMoveTimer() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                setCurrentItem(getCurrentItem() + 1, true);
                if (mHandler != null) {
                    mHandler.postDelayed(this, CAROUSEL_TIMER);
                }
            }
        };
    }

    private int pos2page(int pos) {
        return (pos % mPages);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mListener.OnInstantiateItem(pos2page(position));
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent == null) {
                container.addView(v);
            }
            return (v);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return (mAdapterPages);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
