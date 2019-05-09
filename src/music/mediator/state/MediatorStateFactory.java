package music.mediator.state;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class MediatorStateFactory {
    private static final Map<Class<? extends Object>, Object> classMap = new HashMap<>();

    public static void updateReferences(Object... objects) {
        for (Object object : objects) {
            Class<? extends Object> objClass = object.getClass();
            if (objClass.getInterfaces().length == 1) {
                classMap.put(objClass.getInterfaces()[0], object);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends IMediatorState> T newInstance(Class<T> stateClass) {
        Constructor<?>[] constructors = stateClass.getConstructors();
        if (constructors.length != 1) {
            throw new IllegalStateException("Can only lookup classes with 1 constructor, found " + constructors.length + " for " + stateClass);
        }
        Parameter[] parameters = constructors[0].getParameters();
        Object[] initargs = new Object[parameters.length];
        for (int i = 0; i < initargs.length; i++) {
            Object object = classMap.get(parameters[i].getType());
            if (object == null) {
                throw new IllegalStateException("No class reference found for " + parameters[i].getType());
            }
            initargs[i] = object;
        }
        try {
            return (T) constructors[0].newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalStateException("Could not create new instance of " + stateClass, e);
        }
    }
}
