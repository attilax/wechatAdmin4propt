package com.focusx.entity.weixin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @模块 关注者列表
 * @日期 2013-11-20 时间：下午08:14:27
 */
public class UserList {

	private Integer total;// 关注该公众账号的总用户数
	private Integer count;// 拉取的OPENID个数，最大值为10000
	private String[] openid;// 列表数据，OPENID的列表
	private String next_openid;// 拉取列表的后一个用户的OPENID

	public UserList(JSONObject json) {
		try {
			this.total = json.getInt("total");
			this.count = json.getInt("count");
			this.next_openid = json.getString("next_openid");
			if (json.containsKey("data")) {
				JSONObject data = JSONObject.fromObject(json.getString("data"));
				JSONArray jsonArray = data.getJSONArray("openid");
				if (jsonArray != null && jsonArray.size() > 0) {
					int size = jsonArray.size();
					openid = new String[size];
					for (int i = 0; i < size; i++) {
						openid[i] = jsonArray.getString(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String[] getOpenid() {
		return openid;
	}

	public void setOpenid(String[] openid) {
		this.openid = openid;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
}
