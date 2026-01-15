package groom.backend.domain.tag.repository;

import groom.backend.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    /**
     * 태그명으로 태그를 조회합니다.
     *
     * @param tagName 태그명
     * @return 태그 정보
     */
    Optional<Tag> findByTagName(String tagName);
}
