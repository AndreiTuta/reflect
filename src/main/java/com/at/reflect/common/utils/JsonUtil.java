package com.at.reflect.common.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.jooq.tools.StringUtils;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.entity.SubMeditation;
import com.at.reflect.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JsonUtil implements Util, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();

	@Override
	public UtilType getType() {
		return UtilType.JSON;
	}

	public static boolean toBoolean(Object object) {
		String string = object.toString();
		return StringUtils.equals(string, "true");
	}

	public static List<SubMeditation> toSubMeditationList(JsonArray jsonArray) {
		SubMeditation[] ts = new Gson().fromJson(jsonArray, SubMeditation[].class);
		return Arrays.asList(ts);
	}

	public static JsonArray usersToJson(Iterable<User> users) {
		JsonArray jsonArray = new JsonArray();
		users.forEach(user -> {
			// Java object to JSON string
			String jsonUserString = gson.toJson(user);
			jsonArray.add(jsonUserString);
		});

		return jsonArray;

	}

	public static JsonArray meditationsToJson(Iterable<Meditation> meditations) {
		JsonArray jsonArray = new JsonArray();
		meditations.forEach(meditation -> {
			// TODO: Break this in a separate method
			String jsonMeditationString = gson.toJson(meditation);
			jsonArray.add(jsonMeditationString);
		});

		return jsonArray;
	}
}