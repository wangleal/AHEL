package wang.leal.ahel.http.json.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

/**
 * Adapt a homogeneous collection of objects.
 * 自定义CollectionTypeAdapterFactory，使json内的数组为null时，返回空数组而不是null对象
 */
public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();

        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

        @SuppressWarnings({"unchecked", "rawtypes"}) // create() doesn't define a type parameter
                TypeAdapter<T> result = new Adapter(gson, elementType, elementTypeAdapter, constructor);
        return result;
    }

    private final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        Adapter(Gson context, Type elementType,
                TypeAdapter<E> elementTypeAdapter,
                ObjectConstructor<? extends Collection<E>> constructor) {
            this.elementTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<>(context, elementTypeAdapter, elementType);
            this.constructor = constructor;
        }

        @Override
        public Collection<E> read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                //这里做了修改，原先是返回null，改为返回空数组
                return constructor.construct();
            }

            Collection<E> collection = constructor.construct();
            in.beginArray();
            while (in.hasNext()) {
                E instance = elementTypeAdapter.read(in);
                collection.add(instance);
            }
            in.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter out, Collection<E> collection) throws IOException {
            if (collection == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (E element : collection) {
                elementTypeAdapter.write(out, element);
            }
            out.endArray();
        }
    }
}

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
        this.context = context;
        this.delegate = delegate;
        this.type = type;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return delegate.read(in);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void write(JsonWriter out, T value) throws IOException {
        // Order of preference for choosing type adapters
        // First preference: a type adapter registered for the runtime type
        // Second preference: a type adapter registered for the declared type
        // Third preference: reflective type adapter for the runtime type (if it is a sub class of the declared type)
        // Fourth preference: reflective type adapter for the declared type

        TypeAdapter chosen = delegate;
        Type runtimeType = getRuntimeTypeIfMoreSpecific(type, value);
        if (runtimeType != type) {
            TypeAdapter runtimeTypeAdapter = context.getAdapter(TypeToken.get(runtimeType));
            if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                // The user registered a type adapter for the runtime type, so we will use that
                chosen = runtimeTypeAdapter;
            } else if (!(delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                // The user registered a type adapter for Base class, so we prefer it over the
                // reflective type adapter for the runtime type
                chosen = delegate;
            } else {
                // Use the type adapter for runtime type
                chosen = runtimeTypeAdapter;
            }
        }
        chosen.write(out, value);
    }

    /**
     * Finds a compatible runtime type if it is more specific
     */
    private Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
        if (value != null
                && (type == Object.class || type instanceof TypeVariable<?> || type instanceof Class<?>)) {
            type = value.getClass();
        }
        return type;
    }
}
