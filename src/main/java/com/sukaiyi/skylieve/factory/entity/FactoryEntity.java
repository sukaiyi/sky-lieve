package com.sukaiyi.skylieve.factory.entity;

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
@Table(name = "factory")
public class FactoryEntity extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	String id;

	@Column(name = "item_id")
	String itemId;

	@Column(name = "name")
	String name;
}
