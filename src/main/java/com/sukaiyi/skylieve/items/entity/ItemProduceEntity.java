package com.sukaiyi.skylieve.items.entity;

import com.sukaiyi.skylieve.common.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sukaiyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items_produce")
public class ItemProduceEntity extends SuperEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	String id;

	@Column(name = "produce_item_id")
	String produceItemId;

	@Column(name = "need_item_id")
	String needItemId;

	@Column(name = "count")
	int count;
}
