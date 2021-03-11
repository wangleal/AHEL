package wang.leal.ahel.http.api.converter;

import kotlin.Unit;
import okhttp3.ResponseBody;

final class UnitResponseBodyConverter implements Converter<ResponseBody, Unit> {
    static final UnitResponseBodyConverter INSTANCE = new UnitResponseBodyConverter();

    @Override
    public Unit convert(ResponseBody value) {
        value.close();
        return Unit.INSTANCE;
    }
}
