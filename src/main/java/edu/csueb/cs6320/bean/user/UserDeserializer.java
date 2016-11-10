package edu.csueb.cs6320.bean.user;

import java.io.IOException;
import java.util.logging.Level;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import edu.csueb.cs6320.controller.HomeController;

public class UserDeserializer extends StdScalarDeserializer<User.Roles> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	public UserDeserializer() {
		super(String.class);
	}

	@Override
	public User.Roles deserialize(JsonParser p, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String value = p.getValueAsString();
		//String value = StringDeserializer.instance.deserialize(p, context);
		Logger.getAnonymousLogger().log(Level.INFO, "in deserialize for value: " + value);
        return User.str2role(value);
	}

}
