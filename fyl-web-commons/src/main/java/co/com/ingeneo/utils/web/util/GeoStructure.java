package co.com.ingeneo.utils.web.util;


import java.io.Serializable;

public class GeoStructure implements Serializable {


	private static final long serialVersionUID = -5733675066024520478L;
	/**
	 * GeoStructure ID
	 */
	private Long id;
	/**
	 * Value GeoStructure
	 */
	private String value;

	/**
	 * childs of GeoStructure
	 */
	private GeoStructure[] child;

	public GeoStructure() {
		super();
	}

	public GeoStructure(Long id, String value, GeoStructure[] child) {
		super();
		this.id = id;
		this.value = value;
		this.child = child;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GeoStructure[] getChild() {
		return child;
	}

	public void setChild(GeoStructure[] child) {
		this.child = child;
	}
}
