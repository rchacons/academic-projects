package jpa;

import dao.EntityManagerHelper;
import dao.PersonDAO;
import dao.TicketDAO;
import domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class JpaTest {

	private EntityManager manager;
	public JpaTest(EntityManager manager){
		this.manager=manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EntityManager manager = EntityManagerHelper.getEntityManager();
		JpaTest jpaTest = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();

		tx.begin();

		try {
			jpaTest.createUsers();
			jpaTest.createSupportMembers();
			jpaTest.createTickets();

		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		jpaTest.listUsers();

		// implementing criteria queries
		jpaTest.listBugTickets();
		jpaTest.listFeatureTickets();


		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}



	private void createSupportMembers() {
		PersonDAO personDAO = new PersonDAO();
		int numOfSupportMembers = personDAO.getNumberOfSupportMembers();

		if(numOfSupportMembers == 0){

			SupportMember sp1 = new SupportMember();
			sp1.setName("SupportMember1");
			personDAO.save(sp1);

			SupportMember sp2 = new SupportMember();
			sp2.setName("SupportMember2");
			personDAO.save(sp2);

			SupportMember sp3 = new SupportMember();
			sp3.setName("SupportMember3");
			personDAO.save(sp3);

		}
	}

	private void createUsers() {
		PersonDAO personDAO = new PersonDAO();

		int numOfUsers = personDAO.getNumberOfUsers();

		if(numOfUsers == 0){
			User user1 = new User();
			user1.setName("User1");

			User user2 = new User();
			user2.setName("User2");

			personDAO.save(user1);
			personDAO.save(user2);
		}
	}

	private void createTickets() {

		TicketDAO ticketDAO = new TicketDAO();
		PersonDAO personDAO = new PersonDAO();

		List<Person> users = personDAO.getAllUsers();
		List<Person> supportMembers = personDAO.getAllSupportMembers();
		int nbOfTickets = ticketDAO.getNumberOfAllTickets();

		if(users.size()>=1 && nbOfTickets==0){
			User user = (User) users.get(0);
			SupportMember sp1 = (SupportMember) supportMembers.get(0);
			SupportMember sp2 = (SupportMember) supportMembers.get(1);

			// Bug ticket
			Ticket ticket = new BugForm();
			ticket.setUser(user);
			ticket.setSupportMemberList(List.of(sp1,sp2));
			ticket.setTitle("Bug Ticket test");
			ticket.setState(StateEnum.OPEN);
			ticket.setTag(TagEnum.TAG1);
			ticketDAO.save(ticket);


			Person user1 = users.get(1);

			Ticket ticket1 = new FeatureRequestForm();
			ticket1.setUser(user1);
			ticket1.setTitle("Request form test");
			ticket1.setState(StateEnum.OPEN);
			ticket1.setTag(TagEnum.TAG2);
			ticketDAO.save(ticket1);
		}


	}

	private void listUsers() {
		PersonDAO personDAO = new PersonDAO();
		List<Person> resultList = personDAO.getAllUsers();
		for(Person nextUser : resultList){
			System.out.println("user:"+nextUser.getName());
		}
	}

	private void listBugTickets() {
		TicketDAO ticketDAO = new TicketDAO();

		List<Ticket> tickets = ticketDAO.getBugTickets();
		for(Ticket ticket : tickets){
			System.out.println("Ticket:"+ticket.getTitle());
		}

	}

	private void listFeatureTickets() {

		TicketDAO ticketDAO = new TicketDAO();

		List<Ticket> tickets = ticketDAO.getFeatureRequestTickets();
		for(Ticket ticket : tickets){
			System.out.println("Ticket:"+ticket.getTitle());
		}

	}

}
