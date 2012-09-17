package com.rosettastone.succor.bandit;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Locale;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.JsonField;

/**
 * Universal converter from JSON to model object.
 * Supported types: Date, Integer, Boolean, Enum, Locale, String
 * @param <C> model objects
 */
public class JsonToObjectConverter<C> implements JsonConverter<C> {

    private static final Long SECOND = 1000L;

    /**
     * Target model class
     */
    private Class<C> targetClass;
    private PropertyDescriptor[] propertyDescriptors;

    public JsonToObjectConverter(Class<C> targetClass) {
        this.targetClass = targetClass;
        propertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
    }

    /**
     * Convert json object to instance of target class.
     * @param json
     * @return
     * @throws JSONException
     */
    @Override
    public C convert(JSONObject json) throws JSONException {
        C object = BeanUtils.instantiate(targetClass);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            JsonField annotation = AnnotationUtils.getAnnotation(propertyDescriptor.getReadMethod(), JsonField.class);
            if (annotation != null) {
                String jsonPropetyName = getJsonPropertyName(propertyDescriptor, annotation);
                if (!json.has(jsonPropetyName) || json.isNull(jsonPropetyName)) {
                    continue;
                }
                Object value = null;

                Class<?> propertyClass = propertyDescriptor.getPropertyType();
                value = getJsonValue(json, propertyDescriptor, jsonPropetyName, value, propertyClass);
                setPropertyValue(object, propertyDescriptor, value);
            }
        }
        return object;
    }

    /**
     * Try to get property name from annotation, or use field name
     * @param propertyDescriptor
     * @param annotation
     * @return json property name
     */
    private String getJsonPropertyName(PropertyDescriptor propertyDescriptor, JsonField annotation) {
        String banditName = (String) AnnotationUtils.getValue(annotation);
        if ("".equals(banditName)) {
            banditName = propertyDescriptor.getName();
        }
        return banditName;
    }

    /**
     * Parse json property value and convert to specified class.
     * @param json - json object of current processed
     * @param propertyDescriptor
     * @param jsonPropetyName
     * @param value
     * @param propertyClass - type that we need to get
     * @return
     * @throws JSONException
     * @throws ApplicationException if some another error occured.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object getJsonValue(JSONObject json, PropertyDescriptor propertyDescriptor, String jsonPropetyName,
            Object value, Class<?> propertyClass) throws JSONException {
        if (Date.class.equals(propertyClass)) {
            long secondSinceEpoch = json.getLong(jsonPropetyName);
            value = new Date(secondSinceEpoch * SECOND);
        } else if (Integer.class.equals(propertyClass)) {
            value = json.getInt(jsonPropetyName);
        } else if (Boolean.class.equals(propertyClass)) {
            value = json.getBoolean(jsonPropetyName);
        } else if (Long.class.equals(propertyClass)) {
            value = json.getLong(jsonPropetyName);
        } else if (Enum.class.isAssignableFrom(propertyClass)) {
            String strValue = json.getString(jsonPropetyName);
            try {
                value = Enum.valueOf((Class<Enum>) propertyClass, strValue);
            } catch (IllegalArgumentException e) {
                // Last change to convert enum value
                value = Enum.valueOf((Class<Enum>) propertyClass, "UNKNOWN");
            }
        } else if (Locale.class.equals(propertyClass)) {
            String[] valueStr = json.getString(jsonPropetyName).split("-");
            if (valueStr.length > 1) {
                value = new Locale(valueStr[0], valueStr[1]);
            }
        } else if (String.class.isAssignableFrom(propertyClass)) {
            value = json.getString(jsonPropetyName);
        } else {
            throw new ApplicationException("Can not convert property " + propertyDescriptor.getName() + " of "
                    + targetClass);
        }
        return value;
    }

    /**
     * Try to set value to the object field
     * @param object - target object
     * @param propertyDescriptor - object field descriptor
     * @param value
     * @throws ApplicationException if some error occured.
     */
    private void setPropertyValue(C object, PropertyDescriptor propertyDescriptor, Object value) {
        try {
            propertyDescriptor.getWriteMethod().invoke(object, value);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Can not parse Bandit message", e);
        } catch (IllegalAccessException e) {
            throw new ApplicationException("Can not parse Bandit message", e);
        } catch (InvocationTargetException e) {
            throw new ApplicationException("Can not parse Bandit message", e);
        }
    }

}
