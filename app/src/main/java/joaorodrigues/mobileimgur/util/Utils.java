package joaorodrigues.mobileimgur.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

/**
 * Utils class.
 */
public final class Utils {

    public static String identity(Object o) {
        if (o == null) {
            return "(null)";
        }
        return o.getClass().getSimpleName() + '@' + Integer.toHexString(System.identityHashCode(o));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Spanned linkifyText(String text) {
        String[] textSplit = text.split(" ");
        for (int i = 0; i < textSplit.length; i++) {
            String piece = textSplit[i];
            if (piece.startsWith("http")) {
                textSplit[i] = "<a href=" + piece + ">" + piece + "</a>";
            }
        }
        String textFormatted = "";
        for (String piece : textSplit) {
            if (textFormatted.length() == 0)
                textFormatted += piece;
            else
                textFormatted += " " + piece;
        }
        return Html.fromHtml(textFormatted);
    }


}
