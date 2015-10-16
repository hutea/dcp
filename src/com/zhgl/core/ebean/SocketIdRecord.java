package com.zhgl.core.ebean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_socketidrecord")
public class SocketIdRecord {
	@Id
	/**socket通讯ID*/
	private int sid;

	@OneToOne
	@JoinColumn(name = "soim_id", unique = true)
	private SocketImei socketImei;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

}
