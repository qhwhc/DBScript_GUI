package common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

public class AllBeanUtil {
	public static Object generateObject(Map<Object, Class<?>> properties) {
        BeanGenerator generator = new BeanGenerator();
        Set<Object> keySet = properties.keySet();
        for(Iterator<Object> i = keySet.iterator(); i.hasNext();) {
            String key = (String)i.next();
            generator.addProperty(key, (Class<?>)properties.get(key));
        }
        return generator.create();
    }
	
	public static String getRealName(String proxyName) {
		return proxyName.substring("$cglib_prop_".length(),proxyName.length());
	}
	
	public static Object getValue(Object obj, String property) {
        BeanMap beanMap = BeanMap.create(obj);
        return beanMap.get(property);
    }

    public static void setValue(Object obj, String property, Object value) {
        BeanMap beanMap = BeanMap.create(obj);
        beanMap.put(property, value);
    }
    
    public static Map<String,String> bean2Map(Object object){
    	Map<String, String> map = new HashMap<String,String>();
    	Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
        	String key = getRealName(field.getName());
        	map.put(key, getValue(object,key).toString());
        }
		return map;
    };
}
