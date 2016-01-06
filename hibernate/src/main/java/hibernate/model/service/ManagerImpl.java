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
public class ManagerImpl implements Manager {

    @Autowired
    private SessionFactory sf;

    @Override
    @SuppressWarnings("unchecked")
    public List<Cure> getAllCures() {
        return sf.getCurrentSession().getNamedQuery("cure.all").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> getAllPersons() {
        return sf.getCurrentSession().getNamedQuery("person.all").list();
    }


    @Override
    public Cure getCureFromID(Long id) {
        return (Cure) sf.getCurrentSession().get(Cure.class, id);
    }

    @Override
    public Person getPersonFromID(Long id) {
        return (Person) sf.getCurrentSession().get(Person.class, id);
    }

    @Override
    public Long add(Cure cure) {
        Long id = (Long) sf.getCurrentSession().save(cure);
        cure.setId(id);
        Person person = (Person) sf.getCurrentSession().get(Person.class, cure.getPerson().getId());
        person.getCures().add(cure);
        return id;
    }

    @Override
    public Long add(Person person) {
        Long id = (Long) sf.getCurrentSession().save(person);
        person.setId(id);
        return id;
    }


}
