package com.sukaiyi.skylieve.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author sukaiyi
 */
@Data
@MappedSuperclass
public class SuperEntity {

	@Column(name = "dr")
	boolean dr;
	@Column(name = "create_date")
	Date createDate = new Date();
}
