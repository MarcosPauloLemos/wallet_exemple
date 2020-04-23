package com.wallet.repository;

import com.wallet.model.WalletItem;
import com.wallet.model.enums.TypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual
            (Long wallet, Date init, Date and, Pageable page);

    List<WalletItem> findByWalletIdAndType(Long savedWalletId, TypeEnum type);

    List<WalletItem> findByType(TypeEnum type);

    @Query(value = "SELECT SUM(value) FROM WalletItem wi WHERE wi.wallet.id = :walletId")
    BigDecimal sumValueByWalletId(@Param("walletId") Long walletId);

}
