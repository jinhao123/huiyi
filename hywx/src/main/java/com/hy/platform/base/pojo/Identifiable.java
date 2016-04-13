package com.hy.platform.base.pojo;

import java.io.Serializable;

public interface Identifiable extends Serializable
{
	/**
	 * 
	 * @return long
	 */
	Long getId();

	/**
	 * 
	 * @param id
	 *            id
	 */
	void setId(Long id);
}
