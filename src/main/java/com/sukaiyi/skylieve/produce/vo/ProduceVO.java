package com.sukaiyi.skylieve.produce.vo;

import com.sukaiyi.skylieve.common.vo.SuperVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author sukaiyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProduceVO extends SuperVO {
	String id;
	String itemId;
	int need;
}
