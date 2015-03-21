package joaorodrigues.mobileimgur.widgets;

import android.content.Context;
import android.widget.Button;
import android.widget.PopupMenu;

import joaorodrigues.mobileimgur.R;

/**
 * Simple general use popup menu.
 */
public class PopupSelectorMenu extends PopupMenu{


    public PopupSelectorMenu(Context context, Button anchor, String[] options) {
        super(context, anchor);
        inflate(R.menu.menu_imgurselector);
        for (String option : options) {
            if(!option.equals(anchor.getText().toString()))
                getMenu().add(option);
        }

    }

}
