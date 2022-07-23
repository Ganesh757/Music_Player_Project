package com.music.My_Music_Player.DAO;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.music.My_Music_Player.DTO.SongDTO;

public class SongDAO {

	private static EntityManagerFactory factory;
	private static EntityManager manager;
	private static EntityTransaction transaction;
	static Scanner sc = new Scanner(System.in);
	static int choice;
	static int remove;
	static int update;

	private static void openConnection() {
		factory = Persistence.createEntityManagerFactory("hibernate");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
	}

	private static void closeConnection() {
		factory.close();
		manager.close();
		try {
			transaction.rollback();
		} catch (Exception e) {
			System.out.println("Transaction is Commited");
		}
	}

	public static void menu() {

		System.out.println("Choose Type : \n 1)Add New Song : " + "\n 2)Remove Song : " + "\n 3)Show All Song : "
				+ "\n 4)Update Song : " + "\n 5)Play Random Song :" + "\n 6) Exit :");
		choice = sc.nextInt();

		switch (choice) {
		case 1:
			addSong();
			System.out.println("Song Added Succesfully");
			break;

		case 2:
			removeSong();
			System.out.println();
			System.out.println("Song Removed Succesfully");
			break;

		case 3:
			showSong();
			System.out.println();
			System.out.println("Song Showing Succesfully");
			break;

		case 4:
			updateSong();
			System.out.println();
			System.out.println("Song Updated Succesfully");
			break;

		case 5:
			playRandomSong();
			System.out.println();
			System.out.println("Song Play Randomly");
			break;

		case 6:
			System.out.println();
			System.out.println("Music Player Stopped Succesfully");
			break;

		default:
			System.out.println("Invalid Choice");
			break;
		}
	}

	private static void playRandomSong() {
		double id = sc.nextDouble();
		double i = Math.random();
	}

	private static void updateSong() {

		factory = Persistence.createEntityManagerFactory("hibernate");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();

		String name, singer, length, movie;

		System.out.println("Which Song Update (ID) : ");
		update = sc.nextInt();

		System.out.println("Update Song Name : ");
		name = sc.next();

		System.out.println("Update Song Movie : ");
		movie = sc.next();
		
		System.out.println("Update Song Singer : ");
		singer = sc.next();
		
		System.out.println("Update Song Length : ");
		length = sc.next();

		SongDTO find = manager.find(SongDTO.class, update);
		find.setName(name);
		find.setLength(length);
		find.setSinger(singer);
		find.setMovie(movie);

		manager.persist(find);

		transaction.commit();

	}

	private static void showSong() {

		factory = Persistence.createEntityManagerFactory("hibernate");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();

		Query query = manager.createQuery("from SongDTO");
		List<SongDTO> list = query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Song " + (i) + " Details " + list.get(i));
			System.out.println();
		}

		transaction.commit();
	}

	private static String removeSong() {

		factory = Persistence.createEntityManagerFactory("hibernate");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();

		System.out.println("Which Song Remove (ID) : ");
		remove = sc.nextInt();
		SongDTO find = manager.find(SongDTO.class, remove);
		manager.remove(find);

		transaction.commit();

		return "Ok";
	}

	private static void addSong() {
		factory = Persistence.createEntityManagerFactory("hibernate");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();

		String name, singer, length, movie;
		System.out.println("Add Song Name : ");
		name = sc.next();
		System.out.println("Add Song Movie : ");
		movie = sc.next();
		System.out.println("Add Song Singer : ");
		singer = sc.next();
		System.out.println("Add Song Length : ");
		length = sc.next();

		SongDTO song = new SongDTO();
		song.setName(name);
		song.setMovie(movie);
		song.setSinger(singer);
		song.setLength(length);
		manager.persist(song);
		transaction.commit();
	}

	public static void main(String[] args) {

		openConnection();
		transaction.begin();

//		SongDTO s1=new SongDTO();
//		s1.setName("Chandra");
//		s1.setMovie("Chandramukhi");
//		s1.setSinger("Shreya Ghoshal");
//		s1.setLength("3:15");
//		
//		manager.persist(s1);
		transaction.commit();

		menu();

		closeConnection();
	}
}
