package com.sukaiyi.skylieve.farmlayout.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sukaiyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPosition {
	String id;
	int x;
	int y;
	int rotate;
	String type;

	public enum ItemType {
		Factory,
		Decoration,
		Beehive,
		BaseBuilding,
		PetHouse,
		Yard,
		Field,
		FruitTree,
		FlowerBushes;
	}
}
