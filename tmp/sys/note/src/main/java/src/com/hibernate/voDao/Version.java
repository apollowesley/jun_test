package com.hibernate.voDao;

/**
 * version entity. @author MyEclipse Persistence Tools
 */

public class Version implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;
	private String content;
	private String downloadurl1;
	private String downloadurl2;
	private String downloadurl3;
	private String downloadurl4;
	private String downloadurl5;
	private String downloadurl6;
	private String downloadurl7;

	// Constructors

	/** default constructor */
	public Version() {
	}

	/** full constructor */
	public Version(Integer id, String version, String content, String downloadurl1, String downloadurl2, String downloadurl3, String downloadurl4, String downloadurl5,
			String downloadurl6, String downloadurl7) {
		super();
		this.id = id;
		this.version = version;
		this.content = content;
		this.downloadurl1 = downloadurl1;
		this.downloadurl2 = downloadurl2;
		this.downloadurl3 = downloadurl3;
		this.downloadurl4 = downloadurl4;
		this.downloadurl5 = downloadurl5;
		this.downloadurl6 = downloadurl6;
		this.downloadurl7 = downloadurl7;
	}

	// Property accessors

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDownloadurl1() {
		return downloadurl1;
	}

	public void setDownloadurl1(String downloadurl1) {
		this.downloadurl1 = downloadurl1;
	}

	public String getDownloadurl2() {
		return downloadurl2;
	}

	public void setDownloadurl2(String downloadurl2) {
		this.downloadurl2 = downloadurl2;
	}

	public String getDownloadurl3() {
		return downloadurl3;
	}

	public void setDownloadurl3(String downloadurl3) {
		this.downloadurl3 = downloadurl3;
	}

	public String getDownloadurl4() {
		return downloadurl4;
	}

	public void setDownloadurl4(String downloadurl4) {
		this.downloadurl4 = downloadurl4;
	}

	public String getDownloadurl5() {
		return downloadurl5;
	}

	public void setDownloadurl5(String downloadurl5) {
		this.downloadurl5 = downloadurl5;
	}

	public String getDownloadurl6() {
		return downloadurl6;
	}

	public void setDownloadurl6(String downloadurl6) {
		this.downloadurl6 = downloadurl6;
	}

	public String getDownloadurl7() {
		return downloadurl7;
	}

	public void setDownloadurl7(String downloadurl7) {
		this.downloadurl7 = downloadurl7;
	}

}