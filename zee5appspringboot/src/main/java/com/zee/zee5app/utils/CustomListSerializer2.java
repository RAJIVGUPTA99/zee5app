package com.zee.zee5app.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zee.zee5app.dto.Subscription;

public class CustomListSerializer2 extends StdSerializer<Subscription> {
	@Override
	public void serialize(Subscription value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		gen.writeObject(value);
		
		
	}

	public CustomListSerializer2( ) {
		// TODO Auto-generated constructor stub
		this(null);
	}

	public CustomListSerializer2(Class<Subscription> t) {
		// TODO Auto-generated constructor stub
		super(t);
	}
	
	

}
