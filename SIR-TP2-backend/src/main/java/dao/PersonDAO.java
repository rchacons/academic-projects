package dao;

import domain.Person;
import domain.SupportMember;
import domain.User;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonDAO extends GenericDaoJpaImpl<Person,Long> {

    public Person getPersonByName(String userName){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"),userName));

        try{
            return this.entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    public List<Person> getListSupportMemberByName(List<String> listNamesOfSupport){
        try{
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
            Root<Person> root = criteriaQuery.from(Person.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.type(), SupportMember.class),
                    root.get("name").in(listNamesOfSupport)));
            return this.entityManager.createQuery(criteriaQuery).getResultList();
        } catch (NoResultException | NullPointerException e) {
            return null;
        }
    }

    public Person getUserByName(String userName){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.type(), User.class),
                criteriaBuilder.equal(root.get("name"),userName)));

        try{
            return this.entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    public List<Person> getAllUsers(){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.type(), User.class));

        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    public int getNumberOfUsers(){
        return getAllUsers().size();
    }


    public List<Person> getAllSupportMembers(){
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.type(), SupportMember.class));

        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    public int getNumberOfSupportMembers(){
        return getAllSupportMembers().size();
    }

    public List<Person> getAllPeople() {
        return this.entityManager.createQuery("select p from Person p",Person.class).getResultList();
    }
}
