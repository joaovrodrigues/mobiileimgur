package joaorodrigues.mobileimgur.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.adapter.AlbumRecyclerAdapter;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Fragment that displays an album in a recyclerView;
 */
public class AlbumFragment extends ImgurAbstractFragment {

    private Image mAlbumImage;

    public static Fragment newInstance(Image image) {

        AlbumFragment fragment = new AlbumFragment();
        fragment.setImageAlbum(image);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_album, container, false);

        ((RecyclerView)rootView).setLayoutManager(new GridLayoutManager(getActivity(), 1));
        AlbumRecyclerAdapter recyclerAdapter = new AlbumRecyclerAdapter(mAlbumImage);
        ((RecyclerView)rootView).setAdapter(recyclerAdapter);

        return rootView;
    }


    public void setImageAlbum(Image image) {
        this.mAlbumImage = image;
    }

    @Override
    public void destroy() {
    }
}
