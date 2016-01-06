package hibernate.model.service;

import hibernate.model.domain.Cure;
import hibernate.model.domain.Person;

import java.util.List;

public interface Manager {

    Cure getCureFromID(Long id);
    Person getPersonFromID(Long id);

    Long add(Cure cure);
    Long add(Person person);

    void edit(Cure k, Person person, String nazwa, String kontakt);
    void edit(Person r, String religia, String opis);

    void delete(Cure k);
    void delete(Person r);

    List<Cure> getAllCures();
    List<Person> getAllPersons();

    List<Cure> findCures(Person r);

    void deleteDependencies(Person r);

}
