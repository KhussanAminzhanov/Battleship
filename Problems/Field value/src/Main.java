import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Get value for a given public field or null if the field does not exist or is not accessible.
 */
class FieldGetter {

    public Object getFieldValue(Object object, String fieldName) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals(fieldName) && Modifier.isPublic(field.getModifiers())) {
                return (field.get(object));
            }
        }
        return null;
    }

}