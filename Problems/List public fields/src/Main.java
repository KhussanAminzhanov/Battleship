import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 Get list of public fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public String[] getPublicFields(Object object) {
        StringBuilder publicFields = new StringBuilder();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                publicFields.append(field.getName()).append(" ");
            }
        }
        if (publicFields.length() == 0) return new String[]{};
        return publicFields.toString().trim().split(" ");
    }

}