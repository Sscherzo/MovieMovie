package genre.controller;

import genre.bean.GenreDTO;

public interface GenreService {
	// 입력
	public int genreWrite(GenreDTO genreDTO);
	// 수정
	public int genreModify(GenreDTO genreDTO);
	// 보기
	public GenreDTO genreSelect(String id);
	// 삭제
	public int genreDelete(String id);
}
