package com.zhgl.project.ebean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 人员登记
 */
@Entity
@Table(name = "t_Person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // 作为人员身份特征ID
	private String name;// 姓名
	private String pyjm; // 拼音简码
	private String idcard;// 身份证号码
	private int version = 0; // 指纹版本
	@Lob
	private String template;// 指纹模板特征数据
	@Lob
	private String template2;// 指纹模板特征数据2

	/* 司机=1；司索信号工=2；电工=3；塔机安拆工=4；架工=5；吊蓝安拆工=6 */
	@Column(nullable = false)
	private int category;//
	/* 司机1:塔式起重机司机=1;施工升降机司机=2;物料提升机司机=3;门式起重机司机=4;桥式起重机司机=5;汽车式起重机司机=5 */
	/* 司索信号工2:建筑起重信号、司索工=1 */
	/* 电工3:建筑电工=1 */
	/* 塔机安拆工4:塔机安(拆)工=1;施工升降机安(拆)工=2;物料提升机安(拆)工=3;门式起重机安(拆)工=4;桥式起重机安(拆)工=5 */
	/* 架工=5:普通脚手架工=1;附着升降脚手架工=2 */
	/* 吊蓝安拆工6:高处作业吊篮安装拆卸工=1 */
	@Column(nullable = false)
	private int subd = 1;// 操作类别:小类

	private String certificateNumberOne;// 资格证编号1建设施工特种作业操作资格证编号
	private String certificateNumberTwo;// 资格证编号2特种作业操作证编号(特种作业IC卡)
	@Temporal(TemporalType.DATE)
	private Date certificateStartTime;// 证书开始使用时间
	@Temporal(TemporalType.DATE)
	private Date certificateEndTime;// 证书结束使用时间
	@Temporal(TemporalType.DATE)
	private Date certificateCreateTime;// 证书发放时间
	@Temporal(TemporalType.DATE)
	private Date ICcardStartime;// IC卡使用开始日期
	@Temporal(TemporalType.DATE)
	private Date ICcardEndtime;// IC卡使用截止日期

	private String phone;// 联系电话
	private String note;// 备注

	private Boolean visible = true;//

	private String procede;// 用于缓存人员所属ID

	public String getCatStr() {
		if (category == 1) {
			return "司机";
		}
		if (category == 2) {
			return "司索信号工";
		}
		if (category == 3) {
			return "电工";
		}
		if (category == 4) {
			return "安装拆卸工";
		}
		if (category == 5) {
			return "架工";
		}
		if (category == 6) {
			return "吊蓝安拆工";
		}
		return "";
	}

	public String getProcede() {
		return procede;
	}

	public void setProcede(String procede) {
		this.procede = procede;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSubd() {
		return subd;
	}

	public void setSubd(int subd) {
		this.subd = subd;
	}

	public String getCertificateNumberOne() {
		return certificateNumberOne;
	}

	public void setCertificateNumberOne(String certificateNumberOne) {
		this.certificateNumberOne = certificateNumberOne;
	}

	public String getCertificateNumberTwo() {
		return certificateNumberTwo;
	}

	public void setCertificateNumberTwo(String certificateNumberTwo) {
		this.certificateNumberTwo = certificateNumberTwo;
	}

	public Date getCertificateStartTime() {
		return certificateStartTime;
	}

	public void setCertificateStartTime(Date certificateStartTime) {
		this.certificateStartTime = certificateStartTime;
	}

	public Date getCertificateEndTime() {
		return certificateEndTime;
	}

	public void setCertificateEndTime(Date certificateEndTime) {
		this.certificateEndTime = certificateEndTime;
	}

	public Date getICcardStartime() {
		return ICcardStartime;
	}

	public void setICcardStartime(Date ccardStartime) {
		ICcardStartime = ccardStartime;
	}

	public Date getICcardEndtime() {
		return ICcardEndtime;
	}

	public void setICcardEndtime(Date ccardEndtime) {
		ICcardEndtime = ccardEndtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate2() {
		return template2;
	}

	public void setTemplate2(String template2) {
		this.template2 = template2;
	}

	public String getPyjm() {
		return pyjm;
	}

	public void setPyjm(String pyjm) {
		this.pyjm = pyjm;
	}

	public Date getCertificateCreateTime() {
		return certificateCreateTime;
	}

	public void setCertificateCreateTime(Date certificateCreateTime) {
		this.certificateCreateTime = certificateCreateTime;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}