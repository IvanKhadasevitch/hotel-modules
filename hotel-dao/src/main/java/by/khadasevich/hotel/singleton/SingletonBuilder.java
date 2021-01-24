package by.khadasevich.hotel.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonBuilder {
    /**
     * Storage of singleton instances.
     */
    private static volatile ConcurrentHashMap<Class<?>, Object> INSTANCES_MAP =
            new ConcurrentHashMap<>();

    /**
     *
     * Returns the implementation of the interface in the form of a singleton
     * with a private constructor without arguments from the package: [interface package].impl
     * named: [interface simple name]Impl
     *
     * for example:
     * for interface: dao.DillDao
     * Will be returned implementation: dao.impl.BillDaoImpl as singleton.
     *
     * @param interfaceType value<T> to be associated with the interface implementation
     * @return Returns the implementation of the interface in the form of a singleton
     * with a private constructor without arguments from the package: [interface package].impl
     * named: [interface simple name]Impl
     * @throws SingletonException if interfaceType is null or no implementation for interface
     */
    public static <T> T getInstanceImpl(Class<T> interfaceType) {
        T instanceImpl;

        if (interfaceType == null) {
            throw new SingletonException("Interface type is null");
        }

        if (!INSTANCES_MAP.containsKey(interfaceType)) {
            synchronized (SingletonBuilder.class) {
                if (!INSTANCES_MAP.containsKey(interfaceType)) {
                    String implClassName =
                            interfaceType.getPackage()
                                         .getName()
                                         .concat(".impl.")
                                         .concat(interfaceType.getSimpleName())
                                         .concat("Impl");
                    Constructor constructorImpl = null;
                    try {
                        constructorImpl = Class.forName(implClassName)
                                .getDeclaredConstructor();
                        constructorImpl.setAccessible(true);
                        instanceImpl = interfaceType.cast(constructorImpl
                                .newInstance());
                    } catch (NoSuchMethodException | ClassNotFoundException
                            | IllegalAccessException | InstantiationException
                            | InvocationTargetException e) {
                        throw new SingletonException(String.format("\n"
                                        + "There is no implementation %s with"
                                        + " a private constructor without"
                                        + " arguments for the interface %s",
                                implClassName,
                                interfaceType.getCanonicalName()), e);
                    }
                    INSTANCES_MAP.put(interfaceType, instanceImpl);
                }
            }
        }
        instanceImpl = interfaceType.cast(INSTANCES_MAP.get(interfaceType));

        return instanceImpl;
    }
}
