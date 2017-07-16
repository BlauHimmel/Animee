package com.zhouyuming.animee.event;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class QRCodeEvent {

	private String content;

	public QRCodeEvent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "QRCodeEvent{" +
				"content='" + content + '\'' +
				'}';
	}
}
