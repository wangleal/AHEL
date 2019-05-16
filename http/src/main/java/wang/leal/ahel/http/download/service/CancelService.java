package wang.leal.ahel.http.download.service;

import wang.leal.ahel.http.cancel.Cancelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CancelService {
    private static CancelService cancelService;
    private Map<Object,List<Cancelable>> cancelMap = new HashMap<>();
    private CancelService(){
    }

    public static CancelService createInstance(){
        if (cancelService==null){
            synchronized (CancelService.class){
                if (cancelService==null){
                    cancelService = new CancelService();
                }
            }
        }
        return cancelService;
    }

    public void add(Object tag,Cancelable cancelable){
        if (tag==null||cancelable==null){
            return;
        }
        List<Cancelable> cancelableList = cancelMap.get(tag);
        if (cancelableList==null){
            cancelableList = new ArrayList<>();
        }
        cancelableList.add(cancelable);
        cancelMap.put(tag,cancelableList);
    }

    public void execute(Object tag){
        List<Cancelable> cancelableList = cancelMap.get(tag);
        if (cancelableList!=null){
            for (Cancelable cancelable:cancelableList){
                cancelable.cancel();
            }
            cancelableList.clear();
            cancelMap.remove(tag);
        }
    }

    public void execute(){
        for (Object tag:cancelMap.keySet()){
            execute(tag);
        }
    }
}
