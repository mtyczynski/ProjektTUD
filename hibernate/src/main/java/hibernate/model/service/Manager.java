package hibernate.model.service;

import hibernate.model.domain.Cure;
import hibernate.model.domain.Person;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class Manager {

    @Autowired
    private SessionFactory sf;


    @SuppressWarnings("unchecked")
    public List<Cure> getAllCures() {
        return sf.getCurrentSession().getNamedQuery("cure.all").list();
    }


    @SuppressWarnings("unchecked")
    public List<Person> getAllPersons() {
        return sf.getCurrentSession().getNamedQuery("person.all").list();
    }


    public Cure getCureFromID(Long id) {
        return (Cure) sf.getCurrentSession().get(Cure.class, id);
    }


    public Person getPersonFromID(Long id) {
        return (Person) sf.getCurrentSession().get(Person.class, id);
    }


    public Long add(Cure cure) {
        Long id = (Long) sf.getCurrentSession().save(cure);
        cure.setId(id);
        Person person = (Person) sf.getCurrentSession().get(Person.class, cure.getPerson().getId());
        person.getCures().add(cure);
        return id;
    }


    public Long add(Person person) {
        Long id = (Long) sf.getCurrentSession().save(person);
        person.setId(id);
        return id;
    }


    public void delete(Cure c) {
        c = (Cure) sf.getCurrentSession().get(Cure.class, c.getId());
        Person p = (Person) sf.getCurrentSession().get(Person.class, c.getPerson().getId());
        p.getCures().remove(c);
        sf.getCurrentSession().delete(c);
    }


    public void delete(Person p) {
        p = (Person) sf.getCurrentSession().get(Person.class, p.getId());
        sf.getCurrentSession().delete(p);
    }


    public void edit(Person p, String name, String lastname) {
        p = (Person) sf.getCurrentSession().get(Person.class, p.getId());
        p.setFirstName(name);
        p.setLastName(lastname);
        sf.getCurrentSession().update(p);
    }


    public void edit(Cure cure, Person person, String name, String desc) {
        cure = (Cure) sf.getCurrentSession().get(Cure.class, cure.getId());
      //  Person p = (Person) sf.getCurrentSession().get(Person.class, cure.getPerson().getId());
        cure.setPerson(person);
        cure.setCureName(name);
        cure.setDescription(desc);
        sf.getCurrentSession().update(cure);
    }


    public List<Cure> findCures(Person p) {
        List<Cure> allCures = getAllCures();
        List<Cure> cures = new ArrayList<Cure>();
        for (Cure c : allCures)
            if (c.getPerson().getId() == p.getId())
                cures.add(c);
        return cures;
    }

}