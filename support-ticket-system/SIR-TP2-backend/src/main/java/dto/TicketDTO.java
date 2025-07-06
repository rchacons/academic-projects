package dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class TicketDTO {


    public Long id;

    @Schema(description = "Title of the ticket")
    public String title;

    @Schema(description = "Type of the ticket : BUG or FEATURE")
    public TicketType type;

    @Schema(description = "Name of the ticket's creator (user)",nullable = false)
    public String creator;

    @Schema(description = "List of names of the support members in charge of the ticket.",nullable = true)
    public List<String> assignedSupport;

    @Schema(description = "State of ticket : OPEN by default.")
    public String state;

    @Schema(description = "Tag of the ticket : TAG1 or TAG2 (for the moment)")
    public String tag;

    public TicketDTO(){
        // Empty constructor
    }

    public TicketDTO(String title, TicketType type,String userName, String state, String tag){
        this.title=title;
        this.type=type;
        this.creator =userName;
        this.state=state;
        this.tag=tag;
    }

    public enum TicketType {
        BUG, FEATURE
    }


    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<String> getAssignedSupport() {
        return assignedSupport;
    }

    public void setAssignedSupport(List<String> assignedSupport) {
        this.assignedSupport = assignedSupport;
    }


    @Override
    public String toString() {
        return "TicketDTO{" +
                "id='"+id+'\''+
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", userName='" + creator + '\'' +
                ", state='" + state + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
