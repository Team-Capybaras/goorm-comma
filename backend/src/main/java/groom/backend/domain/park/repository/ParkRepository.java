package groom.backend.domain.park.repository;

import groom.backend.domain.park.entity.Park;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkRepository extends JpaRepository<Park, String> {
    /**
     * areaCode로 공원을 조회합니다 (이미지 포함).
     * 
     * @param areaCode 지역 코드
     * @return 공원 정보 (이미지 포함)
     */
    @EntityGraph(attributePaths = {"imageUrls"})
    Optional<Park> findByAreaCode(String areaCode);
    
    /**
     * 커서 기반 페이지네이션을 위한 조회 메서드.
     * areaCode가 주어진 값보다 큰 공원들을 areaCode 오름차순으로 조회합니다.
     * 
     * @param cursor 커서 (areaCode)
     * @param pageable 페이지 정보
     * @return 공원 리스트
     */
    @Query("SELECT p FROM Park p WHERE p.areaCode > :cursor ORDER BY p.areaCode ASC")
    @EntityGraph(attributePaths = {"imageUrls"})
    List<Park> findParksByAreaCodeGreaterThan(@Param("cursor") String cursor, Pageable pageable);
    
    /**
     * 첫 페이지 조회를 위한 메서드.
     * 가장 작은 areaCode부터 조회합니다.
     * 
     * @param pageable 페이지 정보
     * @return 공원 리스트
     */
    @Query("SELECT p FROM Park p ORDER BY p.areaCode ASC")
    @EntityGraph(attributePaths = {"imageUrls"})
    List<Park> findAllParksOrderByAreaCodeAsc(Pageable pageable);
    
    /**
     * 전체 공원 리스트를 areaCode 오름차순으로 조회합니다.
     * 
     * @return 전체 공원 리스트
     */
    List<Park> findAllByOrderByAreaCode();

    /**
     * 공원명으로 공원을 조회합니다 (이미지 포함).
     * 공원명이 정확히 일치하는 공원을 조회합니다.
     * 
     * @param areaName 공원명
     * @return 공원 정보 (이미지 포함)
     */
    @EntityGraph(attributePaths = {"imageUrls"})
    Optional<Park> findByAreaName(String areaName);

    /**
     * 공원명에 검색어가 포함된 공원들을 조회합니다 (부분 일치).
     * 공원명에 검색어가 포함된 모든 공원을 조회합니다.
     * 
     * @param searchKeyword 검색어
     * @return 공원 리스트
     */
    @Query("SELECT p FROM Park p WHERE p.areaName LIKE %:searchKeyword% ORDER BY p.areaCode ASC")
    List<Park> findByAreaNameContaining(@Param("searchKeyword") String searchKeyword);
}

