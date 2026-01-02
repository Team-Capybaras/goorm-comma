# Tag 도메인

태그 정보를 관리하는 도메인입니다.

## 개요

`tag` 도메인은 공원을 분류하기 위한 태그 정보를 관리합니다. 태그를 통해 공원을 카테고리별로 분류하고 검색할 수 있습니다.

## 엔티티

### Tag (태그 정보)
**테이블**: `tag`

공원을 분류하기 위한 태그 정보를 저장합니다.

**주요 필드:**
- `tag_id` (PK): 태그 ID
- `tag_name`: 태그명
- `description`: 설명

**특징:**
- 공원 분류를 위한 태그 마스터 데이터
- 정적 데이터 (변경 빈도 낮음)
- `park_tag` 테이블을 통해 공원과 연결

**관계:**
- 1:N: `ParkTag` (다른 도메인 `park`에서 참조)

---

## 도메인 역할

`tag` 도메인은 다음과 같은 역할을 합니다:

1. **공원 분류**: 공원을 카테고리별로 분류
2. **검색 기능**: 태그 기반 공원 검색 지원
3. **태그 관리**: 태그 마스터 데이터 관리

## 사용 예시

```java
// 모든 태그 조회
List<Tag> tags = tagRepository.findAll();

// 특정 태그로 공원 검색
List<ParkTag> parkTags = parkTagRepository.findByTagId(tagId);
List<String> areaCodes = parkTags.stream()
    .map(ParkTag::getAreaCode)
    .toList();

// 태그 생성
Tag newTag = Tag.builder()
    .tagName("한강공원")
    .description("한강 인근 공원")
    .build();
tagRepository.save(newTag);
```

## 데이터 특성

- **업데이트 빈도**: 낮음 (정적 데이터)
- **데이터 타입**: 마스터 데이터
- **용도**: 공원 분류 및 검색

