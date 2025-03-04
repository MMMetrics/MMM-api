![header](https://github.com/user-attachments/assets/4369585a-f487-4bb6-927a-d200554530ca)

## 주요 기능

1. **트렌딩 동영상 조회**: 현재 TikTok에서 인기 있는 동영상을 조회합니다.
2. **카테고리별 탐색**: 특정 카테고리에 속한 동영상을 탐색합니다.
3. **키워드 검색**: TikTok 동영상을 키워드로 검색합니다.

## [GraphQL Schema Specification](https://github.com/MMMetrics/MMM-api/blob/main/src/main/resources/graphql/schema.graphqls)

### `Query`

- **`tiktokTrending(n: Int)`**: TikTok의 트렌딩 동영상을 `n`개 조회합니다.
  - 반환 타입: `[TiktokVideoResponse]`
  - 파라미터:
    - `n`: 조회할 동영상의 개수 (기본값: 10)

- **`tiktokExplore(category: Int, count: Int)`**: 특정 카테고리의 동영상을 탐색합니다.
  - 반환 타입: `[TiktokVideoResponse]`
  - 파라미터:
    - `category`: 탐색할 카테고리 ID (필수)
    - `count`: 조회할 동영상의 개수 (기본값: 10)

- **`tiktokSearch(keyword: String, count: Int)`**: 키워드로 TikTok 동영상을 검색합니다.
  - 반환 타입: `[TiktokVideoResponse]`
  - 파라미터:
    - `keyword`: 검색할 키워드 (필수)
    - `count`: 조회할 동영상의 개수 (기본값: 10)

### `TiktokVideoResponse`

- **`title`**: 동영상의 제목 (`String`)
- **`hashtags`**: 동영상에 포함된 해시태그 목록 (`[String]`)
- **`heartCount`**: 좋아요 수 (`Int`)
- **`commentsCount`**: 댓글 수 (`Int`)
- **`viewsCount`**: 조회수 (`Int`)
- **`category`**: 동영상의 카테고리 (`String`)
- **`urlList`**: 동영상의 URL 목록 (`[String]`)

---

## 사용 예시

### 트렌딩 동영상 조회

```graphql
query {
  tiktokTrending(n: 2) {
    title
    hashtags
    heartCount
    viewsCount
    urlList
  }
}
```

### 카테고리별 동영상 탐색

```graphql
query {
  tiktokExplore(category: 1, count: 2) {
    title
    category
    heartCount
    urlList
  }
}
```

### 키워드 검색

```graphql
query {
  tiktokSearch(keyword: "cat", count: 2) {
    title
    hashtags
    viewsCount
    urlList
  }
}
```
