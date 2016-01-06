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



}
