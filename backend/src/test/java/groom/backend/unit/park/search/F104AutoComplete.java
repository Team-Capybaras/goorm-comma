package groom.backend.unit.park.search;

@WebMvcTest(ParkSearchSuggestController.class)
class ParkSearchSuggestF104ContractTest {

  @Autowired MockMvc mockMvc;

  @Test
  @DisplayName("F104 자동완성 결과에 상세 페이지 이동 가능한 식별자가 포함된다")
  void shouldContainIdentifierInSuggest() throws Exception {
    mockMvc.perform(get("/api/v1/parks/search/suggest")
                    .param("q", "중"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.suggestions[*].areaCode").exists())
            .andExpect(jsonPath("$.data.suggestions[*].label").exists());
  }
}
