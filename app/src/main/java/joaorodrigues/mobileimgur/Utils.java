package joaorodrigues.mobileimgur;

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


}
