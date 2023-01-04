package genre.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import genre.bean.GenreDTO;


@Controller
public class GenreController {
	@Autowired
	GenreService genreService;
	
	@RequestMapping(value = "/genre/genreWrite.do")
	public ModelAndView genreWrite(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		String id = request.getParameter("id");
		
		// DB 처리
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(id);
		
		int su = genreService.genreWrite(genreDTO);
		// json
	    String rt = "FAIL";   
	    if(su > 0) rt = "OK";
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("genre.jsp");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/genre/genreSelect.do")
	public ModelAndView genreSelect(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		// 데이터 처리		
		String id = request.getParameter("id");
		
		int        fantasy =0,
		           horror = 0,
		           mellow = 0,
		           kid =0,
		           drama =0,
		           comedy =0,
		           crime = 0,
		           character =0,
		           musical =0,
		           action =0,
		           sf =0,
		           war =0,
		           etc =0;
		
		JSONObject json = new JSONObject();
		
		// DB
		GenreDTO genreDTO = genreService.genreSelect(id);

	    // json
	    String rt = "FAIL";
	    if(genreDTO != null) {
	    	rt="OK";
			sf 			=	genreDTO.getSf();
			fantasy	    = 	genreDTO.getFantasy();
			horror 		=	genreDTO.getHorror();
			drama 		=	genreDTO.getDrama();
			mellow		=	genreDTO.getMellow();
			musical 	=	genreDTO.getMusical();
			kid 		=	genreDTO.getKid();
			crime 		=	genreDTO.getCrime();
			action		=	genreDTO.getAction();
			comedy  	=	genreDTO.getComedy();
			character   =   genreDTO.getCharacter();
			war 		=   genreDTO.getWar();
			etc 		=	genreDTO.getEtc();
			
			json = new JSONObject();
			json.put("rt", rt);
			json.put("sf",sf);
			json.put("fantasy",fantasy);
			json.put("horror",horror);
			json.put("drama",drama);
			json.put("mellow",mellow);
			json.put("musical",musical);
			json.put("kid",kid);
			json.put("crime",crime);
			json.put("action",action);
			json.put("comedy",comedy);
			json.put("character",character);
			json.put("war",war);
			json.put("etc",etc);
	    }

		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("genre.jsp");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/genre/genreModify.do")
	public ModelAndView genreModify(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		int sf = Integer.parseInt(request.getParameter("sf"));
		int fantasy = Integer.parseInt(request.getParameter("fantasy"));
		int horror = Integer.parseInt(request.getParameter("horror"));
		int drama = Integer.parseInt(request.getParameter("drama"));
		int mellow = Integer.parseInt(request.getParameter("mellow"));
		int musical = Integer.parseInt(request.getParameter("musical"));
		int kid = Integer.parseInt(request.getParameter("kid"));
		int crime = Integer.parseInt(request.getParameter("crime"));
		int action = Integer.parseInt(request.getParameter("action"));
		int comedy = Integer.parseInt(request.getParameter("comedy"));
		int character = Integer.parseInt(request.getParameter("character"));
		int war = Integer.parseInt(request.getParameter("war"));
		int etc = Integer.parseInt(request.getParameter("etc"));
		String id = request.getParameter("id");
		
		// DB 처리
		GenreDTO genreDTO = new GenreDTO();
		
		genreDTO.setSf(sf);
		genreDTO.setFantasy(fantasy);
		genreDTO.setHorror(horror);
		genreDTO.setDrama(drama);
		genreDTO.setMellow(mellow);
		genreDTO.setMusical(musical);
		genreDTO.setKid(kid);
		genreDTO.setAction(action);
		genreDTO.setComedy(comedy);
		genreDTO.setCrime(crime);
		genreDTO.setCharacter(character);
		genreDTO.setWar(war);
		genreDTO.setEtc(etc);
		genreDTO.setId(id);
	
		int su = genreService.genreModify(genreDTO);
		// json
	    String rt = "FAIL";   
	    if(su > 0) rt = "OK";
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("genre.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/genre/genreDelete.do")
	public ModelAndView genreDelete(HttpServletRequest request) throws IOException {
		// 데이터 처리
		String id = request.getParameter("id");
		
		// DB 처리
		int su = genreService.genreDelete(id);
		
		// json
	    String rt = "FAIL";   
	    if(su > 0) rt = "OK";
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("genre.jsp");
		return modelAndView;
	}
}
