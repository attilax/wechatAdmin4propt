package com.focusx.entity.weixin;

import java.util.Date;

import net.sf.json.JSONObject;

/**
 * 微信用户信息
 */
public class UserInfo {
	private Integer subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openid;// 用户的标识，对当前公众号唯一
	private String nickname;// 用户的昵称
	private Integer sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String language;// 用户的语言，简体中文为zh_CN
	private String city;// 用户所在城市
	private String province;// 用户所在省份
	private String country;// 用户所在国家
	private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*
	// 640正方形头像），用户没有头像时该项为空
	private Date subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间

	public UserInfo(JSONObject json) {
		subscribe = json.getInt("subscribe");
		openid = json.getString("openid");
		nickname = json.getString("nickname");
		sex = json.getInt("sex");
		language = json.getString("language");
		city = json.getString("city");
		province = json.getString("province");
		country = json.getString("country");
		headimgurl = json.getString("headimgurl");
		long time = json.getLong("subscribe_time");
		this.subscribe_time = new Date(time * 1000);
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Date subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

}
