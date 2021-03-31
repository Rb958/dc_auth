package com.sabc.digitalchampions.repository;

import com.sabc.digitalchampions.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    boolean existsByCode(String code);
    boolean existsByMatriculeAndEmail(String matricule, String email);

    Otp findByCode(String code);
    Otp findByMatriculeAndEmail(String matricule, String email);
}
