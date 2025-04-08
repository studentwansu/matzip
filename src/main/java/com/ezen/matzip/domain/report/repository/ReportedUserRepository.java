package com.ezen.matzip.domain.report.repository;

import com.ezen.matzip.domain.report.dto.ReportedUserDTO;
import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportedUserRepository extends JpaRepository<User, Integer> {

    @Query("select new com.ezen.matzip.domain.report.dto.ReportedUserDTO(" +
            "u.userId, " +
            "u.userReportCount, " +
            "case when u.accountStatus = 1 then true else false end, " +
            "u.accountStatus) " +
            "from User u " +
            "where u.userReportCount >= :reportCountThreshold " +
            "and (:userId = '' or u.userId like concat('%', :userId, '%')) " +
            "and u.accountStatus in :accountStatuses")
    Page<ReportedUserDTO> findReportedUsers(@Param("reportCountThreshold") int reportCountThreshold,
                                            @Param("userId") String userId,
                                            @Param("accountStatuses") List<Integer> accountStatuses,
                                            Pageable pageable);

    // 신고횟수 1 이상인 경우에만 카운트
    @Query("select count(u) from User u where u.accountStatus = :accountStatus and u.userReportCount >= 1")
    int countByAccountStatus(@Param("accountStatus") int accountStatus);
}
