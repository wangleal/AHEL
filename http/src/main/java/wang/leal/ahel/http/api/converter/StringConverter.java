package wang.leal.ahel.http.api.converter;

final class StringConverter implements Converter<Object, String> {
    static final StringConverter INSTANCE = new StringConverter();
    @Override
    public String convert(Object value) {
        return value.toString();
    }
}
