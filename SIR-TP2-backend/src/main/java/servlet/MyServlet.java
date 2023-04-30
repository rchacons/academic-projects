package servlet;

import dao.EntityManagerHelper;
import dao.PersonDAO;
import dao.TicketDAO;
import domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "myServlet",
        urlPatterns = {"/myServlet"})
public class MyServlet extends HttpServlet {

    PersonDAO personDAO;
    TicketDAO ticketDAO;
    EntityManager manager;

    @Override
    public void init() throws ServletException {
        personDAO = new PersonDAO();
        ticketDAO = new TicketDAO();
        manager = EntityManagerHelper.getEntityManager();
        super.init();
    }

    Logger log = Logger.getLogger(String.valueOf(MyServlet.class));

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        switch (request.getParameter("type")) {
            case "ticket":
                Ticket ticket;
                if(request.getParameter("typeTicket").equals("bug")){
                    ticket = new BugForm();
                }
                else{
                    ticket = new FeatureRequestForm();
                }

                ticket.setUser(personDAO.read(Long.valueOf(request.getParameter("userId"))));
                ticket.setTitle(request.getParameter("title"));
                //ticket.setComments(request.getParameter("comments"));
                ticket.setTag(TagEnum.valueOf(request.getParameter("tag")));
                ticket.setState(StateEnum.OPEN);

                ticketDAO.save(ticket);
                break;
            case "user":
                //Insert user
                User user = new User();
                user.setName(request.getParameter("name"));
                personDAO.save(user);
                break;
            case "support":
                SupportMember supportMember = new SupportMember();
                supportMember.setName(request.getParameter("name"));
                personDAO.save(supportMember);
                break;
            case "assign":
                if(request.getParameter("ticketId") != null && request.getParameter("supportId") != null){
                    Ticket ticket1 = ticketDAO.read(Long.valueOf(request.getParameter("ticketId")));
                    Person supportMember1 = personDAO.read(Long.valueOf(request.getParameter("supportId")));

                    if(ticket1 != null && supportMember1 != null){

                        SupportMember supportM = (SupportMember) supportMember1;

                        if(ticket1.getSupportMemberList() != null){

                            ticket1.getSupportMemberList().add(supportM);
                        }
                        else{
                            ticket1.setSupportMemberList(List.of(supportM));
                        }
                        ticketDAO.update(ticket1);

                    }

                }
        }

        transaction.commit();

        response.sendRedirect("/myform.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Ticket> ticketList = ticketDAO.getAllTickets();
        List<Person> personList = personDAO.getAllPeople();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        writeTableOfTickets(out,ticketList);
        writeTableOfPeople(out,personList);
        writeTableOfTicketsAssignedToSupport(out,ticketList);

        out.close();

    }


    public void writeTableOfTickets(PrintWriter printWriter, List<Ticket> tickets){

        printWriter.println("<h2>Tickets:<h2>");
        printWriter.println("<table>");
        printWriter.println("<thead>");
        printWriter.println("<tr>");

        printWriter.println("<th>Id</th>");
        printWriter.println("<th>Creator</th>");
        printWriter.println("<th>Type</th>");
        printWriter.println("<th>Title</th>");
        printWriter.println("<th>Comments</th>");
        printWriter.println("<th>Tag</th>");

        printWriter.println("</tr>");
        printWriter.println("</thead>");

        printWriter.println("<tbody>");

        for (Ticket ticket : tickets){
            printWriter.println("<tr>");

            printWriter.println("<td>"+ticket.getId()+"</td>");
            printWriter.println("<td>"+ticket.getUser().getName()+"</td>");
            printWriter.println("<td>"+ticket.getClass().getSimpleName()+"</td>");
            printWriter.println("<td>"+ticket.getTitle()+"</td>");
            printWriter.println("<td>"+ticket.getComments()+"</td>");
            printWriter.println("<td>"+ticket.getTag()+"</td>");

            printWriter.println("</tr>");
        }

        printWriter.println("</tbody>");

        printWriter.println("</table>");
    }


    private void writeTableOfPeople(PrintWriter printWriter, List<Person> personList) {

        printWriter.println("<h2>Person:<h2>");
        printWriter.println("<table>");
        printWriter.println("<thead>");
        printWriter.println("<tr>");

        printWriter.println("<th>Id</th>");
        printWriter.println("<th>Type</th>");
        printWriter.println("<th>Name</th>");

        printWriter.println("</tr>");
        printWriter.println("</thead>");
        printWriter.println("<tbody>");

        for(Person person : personList){
            printWriter.println("<tr>");

            printWriter.println("<td>"+person.getId()+"</td>");
            printWriter.println("<td>"+person.getClass().getSimpleName()+"</td>");
            printWriter.println("<td>"+person.getName()+"</td>");

            printWriter.println("</tr>");
       }

        printWriter.println("</tbody>");

        printWriter.println("</table>");

    }

    private void writeTableOfTicketsAssignedToSupport(PrintWriter printWriter, List<Ticket> ticketList) {

        printWriter.println("<h2>Tickets assigned to support members :<h2>");
        printWriter.println("<table>");
        printWriter.println("<thead>");
        printWriter.println("<tr>");

        printWriter.println("<th>Ticket id</th>");
        printWriter.println("<th>SupportMember in charge</th>");

        printWriter.println("</tr>");
        printWriter.println("</thead>");
        printWriter.println("<tbody>");

        for (Ticket ticket : ticketList){
            if(ticket.getSupportMemberList() != null){
                for(SupportMember supportMember : ticket.getSupportMemberList()){
                    printWriter.println("<tr>");

                    printWriter.println("<td>"+ticket.getId()+"</td>");
                    printWriter.println("<td>"+supportMember.getName()+"</td>");

                    printWriter.println("</tr>");
                }
            }

        }


        printWriter.println("</tbody>");

        printWriter.println("</table>");

    }
}
