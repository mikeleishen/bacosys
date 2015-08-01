package com.xinyou.util;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.codehaus.jackson.JsonParser;

public class CustomObjectMapper extends ObjectMapper{
	
	public CustomObjectMapper()
	{
		final AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
        final AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector introspector = new AnnotationIntrospector.Pair(primary, secondary);
        
        SerializationConfig serializationConfig = super.getSerializationConfig()
        		.withAnnotationIntrospector(introspector)
        		.withSerializationInclusion(Inclusion.NON_NULL);
        super.setSerializationConfig(serializationConfig);
        
        DeserializationConfig deserializationConfig = super.getDeserializationConfig()
        		.withAnnotationIntrospector(introspector)
        		.without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        super.setDeserializationConfig(deserializationConfig);
        super.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
//        super.configure(Feature.WRAP_ROOT_VALUE, true);

		super.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//		super.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
//		super.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	}

}
