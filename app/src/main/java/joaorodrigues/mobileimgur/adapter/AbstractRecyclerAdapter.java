package joaorodrigues.mobileimgur.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import joaorodrigues.mobileimgur.model.Image;

/**
 * Abstract adapter that implements changeData and setScale;
 */
public abstract class AbstractRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private OnGridItemClickListener mListener;

    protected void notifyItemClicked(int position) {
        if (mListener != null) {
            mListener.onGridItemClicked(position);
        }
    }

    public abstract void changeData(List<Image> imageList);

    public abstract void setScale(double scale);

    public void setOnItemClickListener(OnGridItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnGridItemClickListener {

        public void onGridItemClicked(int position);

    }

}
