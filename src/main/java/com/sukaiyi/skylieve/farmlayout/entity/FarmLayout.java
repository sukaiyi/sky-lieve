package com.sukaiyi.skylieve.farmlayout.entity;

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
@Table(name = "farm_layout")
public class FarmLayout extends SuperEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	String id;

	@Lob
	@Column(name = "layout")
	String layout;

	@Column(name = "memo")
	String memo;
}
