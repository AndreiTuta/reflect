package com.at.reflect.common.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.jooq.tools.StringUtils;

import com.at.reflect.model.entity.SubMeditation;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JsonUtil implements Util, Serializable {

	private static final long serialVersionUID = 1L;

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
}