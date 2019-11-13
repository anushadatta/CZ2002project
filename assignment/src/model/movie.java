package model;

import java.io.IOException;
import java.util.*;
import controller.movieIO;
import model.master;
import model.Cineplex;
import model.Cinema;

/**
 * This class contains all the attributes of a movie and initializes the show class object
 * to create a new show for the movie. 
 * @version 1.0
 */

public class movie {
	private int tSales;
	/**
	 * name of the movie
	 */
	private String movieName;
	
	// create a common class PERSON which contains first name and last name
	// movie directors name - object of class PERSON
	private String directorName;
	
	// all movie reviews -- Array of class AllReviews which contains all reviews and person details with PERSON class
	private String[] reviews;
	
	// all movie ratings -- Array of class AllRatings and person details with PERSON class
	private double[] ratings;
	
	/**
	 * status of the movie - preview, now showing, coming soon
	 */
	// USE ENUM
	private String showingStatus;
	
	/**
	 * synopsis of the movie
	 */
	private String Synopsis;
	
	// cast of the movie -- Array of PERSON class type (can have 2 - 3 actors)
	private String[] Cast;
	
	/**
	 * all shows of the movie
	 */
	private ArrayList<show> Shows;
	
	
	/**
	 * Constructor to set the values of all attributes of Movie
	 * @param name 			name of movie
	 * @param director		director of movie
	 * @param reviews		array of reviews of movie
	 * @param ratings		array of ratings of movie
	 * @param showingStatus	status of movie - preview, now showing, coming soon
	 * @param Synopsis		synopsis of movie
	 * @param Cast			array of actors in the movie
	 */
	public movie(String name,String director,String[] reviews, double[] ratings,
								String showingStatus, String Synopsis, String[] Cast,int tSales) {
		movieName = name;
		directorName = director;
		this.Cast = Cast;
		this.Synopsis = Synopsis;
		this.showingStatus = showingStatus;
		this.ratings = ratings;
		this.reviews = reviews;
		this.Shows = new ArrayList<show>();
		this.tSales =tSales;
//		m.addMovieListing(this);
	}
	
//	/**
//	 * constructor used by show
//	 * @param mov contains all the attributes of a movie
//	 */
//	public movie(movie mov,master m) {
//		movieName = mov.movieName;
//		directorName = mov.directorName;
//		reviews = mov.reviews;
//		ratings = mov.ratings;
//		showingStatus = mov.showingStatus;
//		Synopsis = mov.Synopsis;
//		Cast = mov.Cast;
//	}
	
	/**
	 * get the movie name
	 * @return movie name
	 */
	public String getMovieName() {
		return movieName;
	}
	
	/**
	 * get the showing status
	 * @return showing status - preview, now showing, coming soon
	 */
	public String getShowingStatus() {
		return showingStatus;
	}
	
	/**
	 * get the synopsis
	 * @return synopsis of the movie
	 */
	public String getSynopsis() {
		return Synopsis;
	}
	
	/**
	 * get the director name
	 * @return director name
	 */
	public String getDirectorName() {
		return directorName;
	}
	
	/**
	 * get the array of actors 
	 * @return array of actors in the movie
	 */
	public String[] getCast() {
		return Cast;
	}
	
	/**
	 * calculate the average of all ratings of the movie 
	 * @return average rating of the movie
	 */
	public double getTotalRating() {
		if (ratings.length == 0)
			return -1;
		double sum = 0;
		for (int i = 0;i<ratings.length;i++) {
			sum+=ratings[i];
		}
		sum/=ratings.length;
		return sum;
	}
	
	/**
	 * get all ratings 
	 * @return all ratings
	 */
	public double[] getAllRatings() {
		return ratings;
	}
	
	/**
	 * get all reviews
	 * @return all reviews
	 */
	public String[] getReviews() {
		return reviews;
	}
	
	/**
	 * add a review to the reviews array 
	 * @param rev new review
	 */
	public void writeReview(String rev) {
		String[] temp;
		if (reviews == null) {
			temp = new String[1];
			temp[0] = rev;
			}
		else {
		temp = new String[reviews.length+1];
		for (int i=0;i<reviews.length;i++) {
			temp[i] = reviews[i];
		}
		temp[reviews.length] = rev;
	}
		reviews = temp;
	}
	
	/**
	 * add a rating to the ratings array
	 * @param rat new rating
	 */
	public void giveRating(double rat) {
		double[] temp;
		if(ratings == null) {
			temp = new double[1];
			temp[0] = rat;
		}
		else{
			temp = new double[ratings.length+1];
		for (int i=0;i<ratings.length;i++) {
			temp[i] = ratings[i];
		}
		temp[ratings.length] = rat;
	}
		ratings = temp;
	}
	
	/**
	 * create a show for this movie, currently set to protected to be used by admin module
	 * @param dt date-time of show
	 * @param screenNum screen number
	 * @param cineplexID ID of the CinePlex
	 * @param is3D bool true if the movie is 3D
	 */
	public show createShowListing(String dt,int screenNum,int cineplexID, boolean is3D) {
		show s = new show(this,dt,screenNum,cineplexID,is3D);
		Shows.add(s);
		return s;
	}
	
	/**
	 * save all shows to a text file under the Shows folder with file name as the movie name
	 */
	public void saveShowDetails() {
		movieIO m = new movieIO();
		try {
		m.saveShows("data/Shows/"+movieName+".txt", Shows);
		}catch(IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}
	
	/**
	 * read show details from the shows folder with file name as movie name
	 */
	public void readShowDetails(master Master) {
		show temp;
		ArrayList<Cineplex> tempCineplexArray;
		ArrayList<Cinema> tempCinemaArray;
		Cineplex tempCineplex;
		Cinema tempCinema;
		movieIO m = new movieIO();
		try {
		ArrayList arr = m.readShows(this,"data/Shows/"+movieName+".txt");
		Shows = arr;
		
		for(int i=0;i<Shows.size();i++) {
			temp = Shows.get(i);
			tempCineplexArray = Master.getCineplexes();
			tempCineplex = tempCineplexArray.get(temp.getCineplexID());
			tempCinemaArray = tempCineplex.getCinemaList();
			tempCinema = tempCinemaArray.get(temp.getScreenNum());
			tempCinema.addShow(temp);
		}
		
		}catch(IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}
	
	public ArrayList<show> getShows(){
		return Shows;
	}
	
	public void setShowingStatus(String st) {
		showingStatus = st;
	}
	
	//method to get top five movies with highest rankings
	public static Comparator<movie> topratings = new Comparator<movie>() {

		public int compare(movie m1, movie m2) {

		   double rating1 = m1.getTotalRating();
		   double rating2 = m2.getTotalRating();

		   /*For ascending order*/
		   return (int) rating1- (int) rating2; } };

	public void deleteShow(master m, int ind) {
		show s = Shows.get(ind);
		ArrayList<Cineplex> temp = m.getCineplexes();
		ArrayList<Cinema> temp2 = temp.get(s.getCineplexID()).getCinemaList();
		ArrayList<show> temp3 = temp2.get(s.getScreenNum()).getCinemaShows();
		temp3.remove(s);
		Shows.remove(ind);
	}
	
	public void deleteShow(master m, show s) {
		ArrayList<Cineplex> temp = m.getCineplexes();
		ArrayList<Cinema> temp2 = temp.get(s.getCineplexID()).getCinemaList();
		ArrayList<show> temp3 = temp2.get(s.getScreenNum()).getCinemaShows();
		temp3.remove(s);
		Shows.remove(s);
	}
	
	public void incTsales() {
		tSales++;
	}
	
	public int getTsales() {
		return tSales;
	}
}
