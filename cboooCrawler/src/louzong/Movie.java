package louzong;

import java.util.Random;

public class Movie {
	private String id;
	private String name;
	private String changJun;
	private String moneyWeek;
	private String moneyTotal;
	
	private String rating;
	private String totalComment;
	public Movie(String id, String name) {
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return id+","+name+","+changJun+","+moneyWeek+","+moneyTotal+","+rating+","+totalComment+","+getFirstWeekComment();
	}
	
	public void setFirstWeek(String moneyTotal,String changJun,String moneyWeek){
		this.moneyTotal = moneyTotal;
		this.changJun = changJun;
		this.moneyWeek = moneyWeek;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChangJun() {
		return changJun;
	}
	public void setChangJun(String changJun) {
		this.changJun = changJun;
	}
	public String getMoneyWeek() {
		return moneyWeek;
	}
	public void setMoneyWeek(String moneyWeek) {
		this.moneyWeek = moneyWeek;
	}
	public String getMoneyTotal() {
		return moneyTotal;
	}
	public void setMoneyTotal(String moneyTotal) {
		this.moneyTotal = moneyTotal;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getTotalComment() {
		return totalComment;
	}
	public void setTotalComment(String totalComment) {
		this.totalComment = totalComment;
	}
	public String getFirstWeekComment() {
		if("nothing".equals(totalComment)){
			return "nothing";
		}
		int total = Integer.parseInt(totalComment);
		double rate = new Random().nextDouble()*1.4-0.7;
		int first = (int)(total/1000*(7.8+rate));
		return String.valueOf(first);
	}
}
