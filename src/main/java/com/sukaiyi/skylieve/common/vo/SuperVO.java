package com.sukaiyi.skylieve.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author sukaiyi
 */
@Data
public class SuperVO {
	boolean dr;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	Date createDate = new Date();
}
