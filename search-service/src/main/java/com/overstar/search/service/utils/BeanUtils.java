package com.overstar.search.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class BeanUtils {

    public static Map<String, Object> bean2Map(Object data) {
        Map<String, Object> map = new HashMap<>(16);
        for (Field field : data.getClass().getDeclaredFields()) {
            String name = field.getName();
            Method method = null;
            try {
                method = data.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Object invoke = null;
            try {
                assert method != null;
                invoke = method.invoke(data);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("bean2map failure...fieldName={}",name);
                e.printStackTrace();
            }
            if (null == invoke || StringUtils.isEmpty(invoke)){
                continue;
            }

            if (invoke instanceof String){
                invoke = ((String) invoke).split(",");
            }
            map.put(name, invoke);
        }
        return map;
    }

}
