package com.paula.click2buy.repositories;

import com.paula.click2buy.domain.ShippingOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOptionRepository extends JpaRepository<ShippingOption, Long> {
}
