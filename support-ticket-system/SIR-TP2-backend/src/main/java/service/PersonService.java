package service;

import dao.EntityManagerHelper;
import dao.PersonDAO;
import domain.*;
import dto.PersonDTO;
import dto.SupportMemberDTO;
import dto.TicketDTO;
import dto.UserDTO;

import javax.naming.InvalidNameException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

public class PersonService {

    PersonDAO personDAO = new PersonDAO();

    private EntityManager entityManager;

    public PersonService() { entityManager = EntityManagerHelper.getEntityManager();
    }

    public PersonDTO getPersonById(String id) {

        Person person = personDAO.read(Long.valueOf(id));

        if(person != null){

            if(person instanceof User){
                return populateUserDTO(person);
            }
            else if (person instanceof SupportMember){
                return populateSupportMemberDTO(person);
                                }
        }

        return null;
    }

    private void populateTicketList(List<TicketDTO> ticketDTOList, Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        TicketDTO.TicketType type = (ticket instanceof BugForm) ? TicketDTO.TicketType.BUG : TicketDTO.TicketType.FEATURE;

        ticketDTO.setId(ticket.getId());
        ticketDTO.setType(type);
        ticketDTO.setTitle(ticket.getTitle());
        ticketDTO.setState(ticket.getState().toString());
        ticketDTO.setTag(ticket.getTag().toString());
        ticketDTO.setCreator(ticket.getUser().getName());
        ticketDTO.setAssignedSupport(ticket.getSupportMemberList().stream().map(s->s.getName()).collect(Collectors.toList()));
        ticketDTOList.add(ticketDTO);
    }

    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) throws InvalidNameException {

        // We verify if there's any user with the same name
        if(isUsernameAvailable(personDTO.getName())){

            Person person = null;

            if(personDTO instanceof UserDTO){
                person = new User();
                personDTO.setType(PersonDTO.PersonType.USER);

            }
            else if(personDTO instanceof SupportMemberDTO) {
                person = new SupportMember();
                personDTO.setType(PersonDTO.PersonType.SUPPORT_MEMBER);
            }

            person.setName(personDTO.getName());

            try{
                entityManager.getTransaction().begin();
                Person personTmp = personDAO.save(person);
                entityManager.getTransaction().commit();

                personDTO.setId(personTmp.getId());

            } catch (Exception e){
                entityManager.getTransaction().rollback();
                throw e;
            }


        }
        else{
            throw new InvalidNameException("The username is already taken "+personDTO.getName());
        }

        return personDTO;
    }

    private boolean isUsernameAvailable(String name) {
        return personDAO.getPersonByName(name) == null;
    }

    public List<PersonDTO> getUsers() {

        List<Person> userList = Optional.ofNullable(personDAO.getAllUsers()).orElse(Collections.emptyList());
        List<PersonDTO> userDTOList = new ArrayList<>();

        if(!userList.isEmpty()){
            for(Person user : userList){
                UserDTO userDTO = populateUserDTO(user);
                userDTOList.add(userDTO);
            }
        }

        return userDTOList;

    }

    public List<PersonDTO> getSupportMembers(){

        List<Person> supportMemberList = Optional.ofNullable(personDAO.getAllSupportMembers()).orElse(Collections.emptyList());
        List<PersonDTO> supportMemberDTOList = new ArrayList<>();

        if(!supportMemberList.isEmpty()){
            for(Person supportMember : supportMemberList){
                SupportMemberDTO supportMemberDTO = populateSupportMemberDTO(supportMember);
                supportMemberDTOList.add(supportMemberDTO);
            }
        }

        return supportMemberDTOList;
    }

    private SupportMemberDTO populateSupportMemberDTO(Person person) {
        SupportMemberDTO supportMemberDTO = new SupportMemberDTO();
        supportMemberDTO.setId(person.getId());
        supportMemberDTO.setName(person.getName());
        supportMemberDTO.setType(PersonDTO.PersonType.SUPPORT_MEMBER);
        List<TicketDTO> ticketDTOList = new ArrayList<>();

        for(Ticket ticket : ((SupportMember) person).getTicketList()){
            populateTicketList(ticketDTOList, ticket);
        }
        supportMemberDTO.setAssignedTicketsList(ticketDTOList);

        return supportMemberDTO;

    }

    private UserDTO populateUserDTO(Person user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for(Ticket ticket : ((User) user).getTicketList()){
            populateTicketList(ticketDTOList, ticket);
        }
        userDTO.setCreatedTicketsList(ticketDTOList);
        userDTO.setType(PersonDTO.PersonType.USER);

        return userDTO;

    }
}
