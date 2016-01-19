package hibernate.model.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "cure.all", query = "SELECT k FROM Cure k")
})
public class Cure implements java.io.Serializable{
    private Long id;
    private Person person;
    private String cureName;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person",  nullable = false)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) { this.person = person; }

    @Column(nullable = false)
    public String getCureName() {
        return cureName;
    }
    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
