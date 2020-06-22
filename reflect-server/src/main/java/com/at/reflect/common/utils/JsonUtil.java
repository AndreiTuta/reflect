package com.at.reflect.common.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.jooq.tools.StringUtils;
import org.jooq.tools.json.JSONObject;
import org.springframework.stereotype.Component;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.entity.SubMeditation;
import com.at.reflect.model.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author at
 */
@Component
public class JsonUtil implements Util, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static boolean toBoolean(Object object) {
		String string = object.toString();
		return StringUtils.equals(string, "true");
	}

	public static List<SubMeditation> toSubMeditationList(JsonArray jsonArray) {
		SubMeditation[] ts = new Gson().fromJson(jsonArray, SubMeditation[].class);
		return Arrays.asList(ts);
	}

	public static JsonArray usersToJsonArray(Iterable<User> users) {
		JsonArray jsonArray = new JsonArray();
		users.forEach(user -> {
			// Java object to JSON string
			String jsonUserString = gson.toJson(user);
			jsonArray.add(jsonUserString);
		});
		return jsonArray;
	}

	public static JsonArray meditationsToJsonObject(Iterable<Meditation> meditations) {
		JsonArray jsonArray = new JsonArray();
		meditations.forEach(meditation -> {
			jsonArray.add(JSONObject.escape(mapObject(meditation)));
		});
		return jsonArray;
	}

	public static JsonObject meditationToJsonObject(Meditation meditation) {
		JsonObject jsonArray = new JsonObject();
		jsonArray.addProperty(meditation.getId().toString(), mapObject(meditation));
		return jsonArray;
	}

	/**
	 * @param T
	 * @return
	 */
	public static String mapObject(Object object) {
		try {
			String carAsString = mapper.writeValueAsString(object);
			return carAsString;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public UtilType getType() {
		return UtilType.JSON;
	}
}