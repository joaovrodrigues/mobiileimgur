package joaorodrigues.mobileimgur.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import joaorodrigues.mobileimgur.fragments.AlbumFragment;
import joaorodrigues.mobileimgur.fragments.ImageFragment;
import joaorodrigues.mobileimgur.fragments.ImgurAbstractFragment;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Created by joao on 21-03-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Image> mImageList;

    public ViewPagerAdapter(FragmentManager manager, List<Image> imageList)    {
        super(manager);
        this.mImageList = imageList;
    }

    public void changeData(List<Image> imageList) {
        this.mImageList = imageList;
    }

    @Override
    public Fragment getItem(int position) {
        Image image = mImageList.get(position);
        if (image.isAlbum()) {
            return AlbumFragment.newInstance(image);
        }else {
            return ImageFragment.newInstance(image);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (object instanceof ImgurAbstractFragment) {
            ((ImgurAbstractFragment) object).destroy();
        }
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }
}
