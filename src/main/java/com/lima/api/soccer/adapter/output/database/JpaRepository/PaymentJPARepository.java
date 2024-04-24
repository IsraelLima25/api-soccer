package com.lima.api.soccer.adapter.output.database.JpaRepository;

import com.lima.api.soccer.adapter.output.database.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJPARepository extends JpaRepository<PaymentModel, Long> {

    Optional<PaymentModel> findByCode(String code);
}
