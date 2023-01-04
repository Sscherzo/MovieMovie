package review.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import review.bean.ReviewDTO;
import reviewfiles.bean.ReviewFilesDTO;
import reviewurl.bean.ReviewUrlDTO;

@Controller
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	// 매개변수 MultipartFile photo는 <input type="file" name="photo"/> 태그의 name과 일치해야함
	@RequestMapping(value = "/review/review_insert.do")
	public ModelAndView reviewWrite(HttpServletRequest request, MultipartFile photo) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		// 데이터
		String dir = request.getSession().getServletContext().getRealPath("/storage");
		
		MultipartRequest multi = (MultipartRequest) request;
		float rate = Float.parseFloat(request.getParameter("rate"));
		String title = request.getParameter("title");
		String director = request.getParameter("director");
		String playdate = request.getParameter("playdate");
		String runtime = request.getParameter("runtime");
		String genre = request.getParameter("genre");
		String actor = request.getParameter("actor");
		String plot = request.getParameter("plot");
		String watch = request.getParameter("watch");
		String place = request.getParameter("place");
		String review = request.getParameter("review");
		String id = request.getParameter("id");
		
		// DB
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setRate(rate);
		reviewDTO.setTitle(title);
		reviewDTO.setDirector(director);
		reviewDTO.setPlaydate(playdate);
		reviewDTO.setRuntime(runtime);
		reviewDTO.setGenre(genre);
		reviewDTO.setActor(actor);
		reviewDTO.setPlot(plot);
		reviewDTO.setWatch(watch);
		reviewDTO.setPlace(place);
		reviewDTO.setReview(review);
		reviewDTO.setId(id);
		
		// 리뷰 정보 저장
		int su = reviewService.reviewWrite(reviewDTO);
		
		// json
		int review_seq = 0;
		String rt = "FAIL";
		if (su > 0) rt = "OK";
		
		// 글 데이터 저장이 성공한 경우, 파일 관련 데이터 저장
		if(rt.equals("OK")) {
			// photo는 파일이 전달되지 않으면 null임
	        if (photo != null) {
	            // 전송되어온 파일 이름
	            String originname = photo.getOriginalFilename();
	            // storage 폴더에 저장된 파일 이름
	            String filename = photo.getOriginalFilename();
	            // 저장할 파일의 확장자를 원본이름에서 추출
	            int lastIndex = originname.lastIndexOf(".");
	            String filetype = originname.substring(lastIndex + 1);
	            // 파일의 크기
	            int filesize = (int)photo.getSize();
	            // 파일 복사
	            File file = new File(dir, filename);
	            FileCopyUtils.copy(photo.getInputStream(), 
	            		new FileOutputStream(file));
	            // dto에 저장
	            ReviewFilesDTO reviewfilesDTO = new ReviewFilesDTO();
	            reviewfilesDTO.setDir(dir);
	            reviewfilesDTO.setOriginname(originname);
	            reviewfilesDTO.setFilename(filename);
	            reviewfilesDTO.setFiletype(filetype);
	            reviewfilesDTO.setFilesize(filesize);
	            // review seq 값 얻기
	            review_seq = reviewService.getReviewFirstSeq();
	            reviewfilesDTO.setReview_seq(review_seq);
	            
	            int su2 = reviewService.filesWrite(reviewfilesDTO); 
	         } else {
	        	 // 데이터
	        	 String imageUrl = request.getParameter("imageUrl");
	        	 
	        	 // DB
	        	 ReviewUrlDTO reviewUrlDTO = new ReviewUrlDTO();
	        	 reviewUrlDTO.setImageUrl(imageUrl);
	        	 // review seq 값 얻기
	        	 review_seq = reviewService.getReviewUrlFirstSeq();
	        	 reviewUrlDTO.setReview_seq(review_seq);
	        	 
	        	 int su3 = reviewService.urlWrite(reviewUrlDTO);
	         }
		}
		// json 출력
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("seq", review_seq);
	      
		// 화면 이동
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("json", json);
	    modelAndView.setViewName("review.jsp");
	    return modelAndView;
	} 
	
	@RequestMapping(value = "/review/review_delete.do")
	public ModelAndView reviewDelete(HttpServletRequest request) throws Exception {
		// 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		String id = request.getParameter("id");
		
		// 실제 폴더 위치
		String dir = request.getSession().getServletContext().getRealPath("/storage");
		
		// DB
		int su = reviewService.reviewDelete(seq, id);
		
		// json
		String rt = "FAIL";
		if(su > 0) rt = "OK";
		
		// 게시판 글을 지운 후, 파일 삭제
		if(rt.equals("OK")) {
			// 파일이 있는 지 검사
			String filename = reviewService.checkFiles(seq);
			
			if(filename != null) {	// 파일이 있으면
				int su2 = reviewService.filesDelete(seq, dir + "/" + filename);
			} else {
				// Url이 있는 지 검사
				String imageUrl = reviewService.checkUrl(seq);
				
				if (imageUrl != null) {		// Url이 있으면
					int su3 = reviewService.urlDelete(seq);
				}
			}
		}
		
		// json 출력
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		
		// 화면 이동
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/review/review_modify.do")
	public ModelAndView reviewModify(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
	    // 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		String id = request.getParameter("id");
		float rate = Float.parseFloat(request.getParameter("rate"));
		String watch = request.getParameter("watch");
		String place = request.getParameter("place");
		String review = request.getParameter("review");
		
		// DB
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setSeq(seq);
		reviewDTO.setId(id);
		reviewDTO.setRate(rate);
		reviewDTO.setWatch(watch);
		reviewDTO.setPlace(place);
		reviewDTO.setReview(review);
		
		int su = reviewService.reviewModify(reviewDTO);
		
		// json
		String rt = "FAIL";
	    if(su > 0) rt = "OK";
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
	    // 화면 이동
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/review/review_list.do")
	public ModelAndView reviewList(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		// DB
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(id);
		List<ReviewDTO> list = reviewService.reviewList(id);
		
	    // json
		String rt = "FAIL";
		int total = list.size();
		if(total > 0) {
			rt = "OK";
		}
		
		JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("total", total);
	      
	    if(rt.equals("OK")) {
	    	JSONArray item = new JSONArray();
	        for(int i=0; i<total; i++) {
	           reviewDTO = list.get(i);
	           
	           // 파일 이름 얻어오기
	           String filename = reviewService.checkFiles(reviewDTO.getSeq());
	           
	           // 파일 URL
	           String fileURL = "";
	           // 이미지 URL
	           String imageURL = "";
	           
	           if(filename != null) {
	        	   fileURL = request.getScheme() + "://" + request.getServerName() 
	        	   + ":" + request.getServerPort() + request.getContextPath() 
	        	   + "/storage/" + filename;
	           } else {
	        	   // Url 얻어오기
	        	   imageURL = reviewService.checkUrl(reviewDTO.getSeq());
	           }
	           
	           // json 객체
	           JSONObject temp = new JSONObject();
	           temp.put("seq", reviewDTO.getSeq());
	           temp.put("rate", reviewDTO.getRate()	);
	           temp.put("title", reviewDTO.getTitle() );
	           temp.put("director", reviewDTO.getDirector());
	           temp.put("playdate", reviewDTO.getPlaydate());
	           temp.put("runtime", reviewDTO.getRuntime());
	           temp.put("genre", reviewDTO.getGenre());
	           temp.put("actor", reviewDTO.getActor());
	           temp.put("plot", reviewDTO.getPlot());
	           temp.put("watch", reviewDTO.getWatch());
	           temp.put("place", reviewDTO.getPlace());
	           temp.put("review", reviewDTO.getReview());
	           temp.put("id", reviewDTO.getId());
	           if (filename != null) {
	        	   temp.put("filename", fileURL);
	           } else {
	        	   temp.put("imageUrl", imageURL);
	           }
	           
	           // JSONArray에 추가
	           item.put(i, temp);
	        }      
	        json.put("item", item);
	    }
	    
	    // 화면 이동
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/review/review_watchlist.do")
	public ModelAndView reviewWatchList(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String watch = request.getParameter("watch");
		// DB
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(id);
		reviewDTO.setWatch(watch);
		List<ReviewDTO> list = reviewService.reviewWatchList(id, watch);
		
	    // json
		String rt = "FAIL";
		int total = list.size();
		if(total > 0) {
			rt = "OK";
		}
		
		JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("total", total);
	      
	    if(rt.equals("OK")) {
	    	JSONArray item = new JSONArray();
	        for(int i=0; i<total; i++) {
	           reviewDTO = list.get(i);
	           
	           // 파일 이름 얻어오기
	           String filename = reviewService.checkFiles(reviewDTO.getSeq());
	           
	           // 파일 URL
	           String fileURL = "";
	           // 이미지 URL
	           String imageURL = "";
	           
	           if(filename != null) {
	        	   fileURL = request.getScheme() + "://" + request.getServerName() 
	        	   + ":" + request.getServerPort() + request.getContextPath() 
	        	   + "/storage/" + filename;
	           } else {
	        	   // Url 얻어오기
	        	   imageURL = reviewService.checkUrl(reviewDTO.getSeq());
	           }
	           
	           // json 객체
	           JSONObject temp = new JSONObject();
	           temp.put("seq", reviewDTO.getSeq());
	           temp.put("rate", reviewDTO.getRate()	);
	           temp.put("title", reviewDTO.getTitle() );
	           temp.put("director", reviewDTO.getDirector());
	           temp.put("playdate", reviewDTO.getPlaydate());
	           temp.put("runtime", reviewDTO.getRuntime());
	           temp.put("genre", reviewDTO.getGenre());
	           temp.put("actor", reviewDTO.getActor());
	           temp.put("plot", reviewDTO.getPlot());
	           temp.put("watch", reviewDTO.getWatch());
	           temp.put("place", reviewDTO.getPlace());
	           temp.put("review", reviewDTO.getReview());
	           temp.put("id", reviewDTO.getId());
	           if (filename != null) {
	        	   temp.put("filename", fileURL);
	           } else {
	        	   temp.put("imageUrl", imageURL);
	           }
	           
	           // JSONArray에 추가
	           item.put(i, temp);
	        }      
	        json.put("item", item);
	    }
	    
	    // 화면 이동
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/review/review_select.do")
	public ModelAndView reviewSelect(HttpServletRequest request) throws Exception {
		// 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		String id = request.getParameter("id");
		
		// DB
		ReviewDTO reviewDTO = reviewService.reviewSelect(seq, id);
		
	    // json
		String rt = "FAIL";
		int total = 0;
		if(reviewDTO != null) {
			rt = "OK";
			total = 1;
		}
		
		JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("total", total);
	      
	    if(rt.equals("OK")) {
	    	JSONArray item = new JSONArray();
	    	JSONObject temp = new JSONObject();
	    	
	    	// 파일 이름 얻어오기
	        String filename = reviewService.checkFiles(reviewDTO.getSeq());
	        // 파일 URL
	        String fileURL = "";
	        // 이미지 URL
	    	String imageURL = "";
	           
	        if(filename != null) {
	        	fileURL = request.getScheme() + "://" + request.getServerName() 
	        			+ ":" + request.getServerPort() + request.getContextPath() 
	        			+ "/storage/" + filename;
	        } else {
	        	imageURL = reviewService.checkUrl(reviewDTO.getSeq());
	        }
	           
	        // json 객체
	        temp.put("seq", reviewDTO.getSeq());
	        temp.put("rate", reviewDTO.getRate());
	        temp.put("title", reviewDTO.getTitle());
	        temp.put("director", reviewDTO.getDirector());
	        temp.put("playdate", reviewDTO.getPlaydate());
	        temp.put("runtime", reviewDTO.getRuntime());
	        temp.put("genre", reviewDTO.getGenre());
	        temp.put("actor", reviewDTO.getActor());
	        temp.put("plot", reviewDTO.getPlot());
	        temp.put("watch", reviewDTO.getWatch());
	        temp.put("place", reviewDTO.getPlace());
	        temp.put("review", reviewDTO.getReview());
	        if (filename != null) {
	        	temp.put("filename", fileURL);
	        } else {
	        	temp.put("imageUrl", imageURL);
	        }
	         
	        // JSONArray에 추가
	        item.put(0, temp);
	        json.put("item", item);
	    }   
	    
	    // 화면 이동
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/review/review_total.do")
	public ModelAndView reviewTotal(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		
		int totalCnt = reviewService.getTotalCnt(id);					// 총 글 수
		String totalRuntime = reviewService.getTotalRunTime(id);		// 총 관람 시간

		// json
		String rt = "FAIL";
		if (totalCnt > 0) {
			rt = "OK";
		}
		
		JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("totalCnt", totalCnt);
	    json.put("totalRuntime", totalRuntime);
	  
	    // 화면 이동
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;	
	}
	
	@RequestMapping(value = "/review/review_watchtotal.do")
	public ModelAndView getWatchCnt(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String watch = request.getParameter("watch");
		
//		System.out.println("id : " + id);
//		System.out.println("watch" + watch);
		
		int watchCnt = reviewService.getWatchCnt(id, watch);

		// json
		String rt = "FAIL";
		if (watchCnt > 0) {
			rt = "OK";
		}
		
		JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("watchCnt", watchCnt);
	  
	    // 화면 이동
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("review.jsp");
		return modelAndView;	
	}
}
