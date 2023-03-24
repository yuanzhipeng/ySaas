package cc.sybx.saas.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class KsBeanUtil {

    /**
     * 用于源集合和目标集合之间的深度转换
     * 注意点：目标集合对象与源集合对象中的属性名称必须一致，否则无法拷贝
     * @param sourceList    源集合
     * @param targetClass   目标对象的Class对象
     * @param <S>           源集合对象类型
     * @param <T>           目标集合对象类型
     * @return              目标集合
     */
    public static <S,T> List<T> convert(List<S> sourceList, Class<T> targetClass, SerializerFeature... features) {
        return sourceList.stream().map(s -> convert(s, targetClass,features)).collect(Collectors.toList());
    }


    /**
     * 用于源对象和目标对象中属性类型不同的深度拷贝
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     * @param source        源对象
     * @param targetClass   目标对象的Class对象
     * @param <T>
     * @return              目标对象
     */
    public static <T> T convert(Object source, Class<T> targetClass,SerializerFeature... features) {
        String sourceJsonStr = JSONObject.toJSONString(source,features);
        T target = JSONObject.parseObject(sourceJsonStr, targetClass);
        return target;
    }

    /**
     * 复制List
     * @param source
     * @param target
     */
    public static void copyList(List source, List target){
        source.forEach(o -> {
            try {
                Object c = o.getClass().newInstance();
                BeanUtils.copyProperties(o, c);
                target.add(c);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
