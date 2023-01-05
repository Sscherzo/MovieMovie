package genre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import genre.bean.GenreDTO;
import genre.dao.GenreDAO;

@Service
public class GenreServiceImpl implements GenreService{
	@Autowired
	GenreDAO genreDAO;

	@Override
	public int genreWrite(GenreDTO genreDTO) {
		return genreDAO.genreWrite(genreDTO);
	}
	public int genreModify(GenreDTO genreDTO) {
		return genreDAO.genreModify(genreDTO);
	}
	@Override
	public GenreDTO genreSelect(String id) {
		return genreDAO.genreSelect(id);
	}
	@Override
	public int genreDelete(String id) {
		return genreDAO.genreDelete(id);
	}
}
