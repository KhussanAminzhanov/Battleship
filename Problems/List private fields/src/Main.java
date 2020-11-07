import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Get sorted list of private fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public List<String> getPrivateFields(Object object) {
        List<String> privateFields = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field.getName());
            }
        }
        Collections.sort(privateFields);
        return privateFields;
    }

}