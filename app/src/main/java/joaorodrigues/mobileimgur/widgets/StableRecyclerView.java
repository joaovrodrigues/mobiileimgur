package joaorodrigues.mobileimgur.widgets;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Stable recycler view that will not throw error on stop scroll
 */
public class StableRecyclerView extends RecyclerView {

    public static final int GRID_LAYOUT = 0;
    public static final int STAGGERED_LAYOUT = 1;

    private double mScale = 1d;
    private int mLayoutType;

    public StableRecyclerView(Context context) {
        super(context);
    }

    public StableRecyclerView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public StableRecyclerView(Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScale(double scale) {
        this.mScale = scale;
        setLayoutManager(mLayoutType);
    }

    public double getScale() {
        return this.mScale;
    }

    public void setLayoutManager(int layoutManagerCode) {
        this.mLayoutType = layoutManagerCode;
        switch (layoutManagerCode) {
            case (GRID_LAYOUT):
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getRows());
                setLayoutManager(layoutManager);
                break;
            case (STAGGERED_LAYOUT):
                StaggeredGridLayoutManager stagLayoutManager =
                        new StaggeredGridLayoutManager(getRows(), StaggeredGridLayoutManager.VERTICAL);
                setLayoutManager(stagLayoutManager);
                break;
        }
    }

    public int getRows() {
        return (int)(1/mScale) < 5 ? (int)(1/mScale):5;
    }

    public int getLayoutType() {
        return this.mLayoutType;
    }

    @Override
    public void stopScroll() {
        if(getLayoutManager() != null) {
            super.stopScroll();
        }
    }
}
