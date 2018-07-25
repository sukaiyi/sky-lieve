package com.sukaiyi.skylieve.purchase.repository;

import com.sukaiyi.skylieve.purchase.entity.PurchaseEntity;
import com.sukaiyi.skylieve.purchase.entity.PurchasedItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface PurchasedItemRepository extends JpaRepository<PurchasedItemEntity, String> {
}
