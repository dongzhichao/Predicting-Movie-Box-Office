package louzong;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchInfo {
	
	public static List<Movie> matchCboo(){
		List<Movie> movies = new ArrayList<>();
		Movie oldMovie = null;
		int index=0;
		int[] areas={50,29,37,40,1,25,16};
		Crawler crawler = new Crawler();
		for(int i=5;i<7;i++){
			int area = areas[i];
			for(index=1;;index++){
				System.out.println(area+"***"+index);
				crawler.getContent("http://www.cbooo.cn/Mdata/getMdata_movie?area="+area+"&type=0&year=2016&pIndex="+index);
				matchOnePage(crawler,movies);
				if(oldMovie==null){
					oldMovie = movies.get(0);
					DataWrite.writeToFile(movies);
					movies.removeAll(movies);
					continue;
				}
				if(movies.get(0).getId().equals(oldMovie.getId())){
					movies.removeAll(movies);
					break;
				}
				DataWrite.writeToFile(movies);
				oldMovie = movies.get(0);
				movies.removeAll(movies);
			}
		}
		return movies;
	}
	
	private static boolean matchOnePage(Crawler crawler, List<Movie> movies) {
		String id = null;
		String name = null;
		String idRegex="ID\":\"(\\d+?)\"";
		String nameRegex = "MovieName\":\"(.+?)\"";
		String result = crawler.getResult();
		do{
			id=matchString(result, idRegex);
			if (id=="nothing") {
				break;
			}
			name = matchString(result, nameRegex);
			Movie movie = new Movie(id,name);
			if(movies.size()>=1 && movie.getId().equals(movies.get(movies.size()-1).getId())){
				return true;
			}
			movie = matchThisMovieOnCboo(movie);
			movie = matchThisMovieOnDouban(movie);
			movies.add(movie);
			result = result.substring(result.indexOf(name));
		}while(true);		
		return false;
	}
	
	//根据id爬一部电影的首周数据
	private static Movie matchThisMovieOnCboo(Movie movie){
		String url = "http://www.cbooo.cn/m/"+movie.getId();
		Crawler cbboCrawler = new Crawler();
		cbboCrawler.getContent(url);
		String result = cbboCrawler.getResult();
		String moneyTotal = null;
		String changJun = null;
		String moneyWeek = null;
		String moneyTotalRegex = "m-span\">累计票房<br />(.+?)万";
		String changJunRegex = "td class=\"arrow\">(\\d+?)<";
		String moneyWeekRegex = "td class=\"arrow\".+?<td>(\\d+?)</td>";
		moneyTotal = matchString(result, moneyTotalRegex);
		int index = result.indexOf("第1周");
		if(index == -1){
			changJun = "nothing";
			moneyWeek = "nothing";
		}else{
			result = result.substring(index);
			changJun = matchString(result, changJunRegex);
			moneyWeek = matchString(result, moneyWeekRegex);
		}
		movie.setFirstWeek(moneyTotal, changJun, moneyWeek);
		return movie;
	}
	
	private static Movie matchThisMovieOnDouban(Movie movie){
		String doubanResult = getDoubanResult(movie);
		String ratingNum = null;
		String totalComment = null;
		String ratingNumRegex = "v:average\">(.+?)<";
		String totalCommentRegex = "v:votes\">(.+?)<";
		ratingNum = matchString(doubanResult, ratingNumRegex);
		totalComment = matchString(doubanResult, totalCommentRegex);
		movie.setRating(ratingNum);
		movie.setTotalComment(totalComment);
		return movie;
	}
	
	private static String getDoubanResult(Movie movie){
		String movieName = movie.getName();
		System.out.println(movieName);
		movieName = movieName.replaceAll(" ", "");
		String searchurl = "https://movie.douban.com/j/subject_suggest?q="+movieName;
		String idRegex = "subject\\\\/(\\d+?)\\\\/";
		Crawler doubanCrawler = new Crawler();
		doubanCrawler.getContent(searchurl);
		String result = doubanCrawler.getResult();
		String movieId = matchString(result, idRegex);
		doubanCrawler = new Crawler();
		if(movieId.equals("nothing")){
			result = "nothing";
		}else{
			doubanCrawler.getContent("https://movie.douban.com/subject/"+movieId);			
			result = doubanCrawler.getResult();
		}
		return  result;
	}
	
	private static String matchString(String originStr, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(originStr);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return "nothing";
		}
	}
}
