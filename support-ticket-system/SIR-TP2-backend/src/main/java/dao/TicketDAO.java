package dao;

import domain.BugForm;
import domain.FeatureRequestForm;
import domain.Ticket;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TicketDAO extends GenericDaoJpaImpl<Ticket,Long> {

    public List<Ticket> getBugTickets(){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> root = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.type(),BugForm.class));

        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Ticket> getFeatureRequestTickets(){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> root = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.type(), FeatureRequestForm.class));

        return this.entityManager.createQuery(criteriaQuery).getResultList();


    }

    public List<Ticket> getTicketsByUser(String userName){
        Query query = this.entityManager.createQuery("select t from Ticket as t where t.user.name = :user");
        query.setParameter("user",userName);
        return query.getResultList();
    }

    public List<Ticket> getAllTickets(){
        return this.entityManager.createQuery("select t from Ticket t", Ticket.class).getResultList();
    }
    public int getNumberOfAllTickets() {
        return this.entityManager.createQuery("select t from Ticket t", Ticket.class).getResultList().size();
    }
}
