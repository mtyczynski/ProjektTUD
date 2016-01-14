package hibernate.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import hibernate.model.domain.Cure;
import hibernate.model.domain.Person;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ManagerTest {

    @Autowired
    Manager manager;

    private final String fname1 = "TESTfname";
    private final String lname1 = "TESTlname";
    private final String fname2 = "TESTfname2";
    private final String lname2 = "TESTlname2";
    private final String cure1 = "TESTcure";
    private final String desc1 = "TESTdesc";
    private final String cure2 = "TESTcure2";
    private final String desc2 = "TESTdesc2";
    private List<Long> cureIds = new ArrayList<Long>();
    private List<Long> personIds = new ArrayList<Long>();

    @Before
    public void PrepareDataForTests() {

        List<Cure> allCures = manager.getAllCures();
        List<Person> allPersons = manager.getAllPersons();

        for (Cure cure : allCures)
            cureIds.add(cure.getId());

        for (Person person : allPersons)
            personIds.add(person.getId());
    }

    @After
    public void deleteTestingData() {
        boolean del;
        List<Cure> cureList = manager.getAllCures();
        List<Person> personList = manager.getAllPersons();

        for (Cure cure : cureList) {
            del = true;
            for (Long cureID : cureIds)
                if (cure.getId() == cureID) {
                    del = false;
                    break;
                }
            if (del)
                manager.delete(cure);
        }

        for (Person person : personList) {
            del = true;
            for (Long personID : personIds)
                if (person.getId() == personID) {
                    del = false;
                    break;
                }
            if (del)
                manager.delete(person);
        }
    }

    @Test
    public void fetchFromID() {

        Person p = new Person();
        p.setFirstName(fname1);
        p.setLastName(lname1);
        Cure c = new Cure();
        c.setCureName(cure1);
        c.setDescription(desc1);
        c.setPerson(p);
        Long newPersonID = manager.add(p);
        Long newCureID = manager.add(c);
        Cure newCure = manager.getCureFromID(newCureID);
        Person newPerson = manager.getPersonFromID(newPersonID);

        assertEquals(newPersonID, newPerson.getId());
        assertEquals(newCureID, newCure.getId());
        assertEquals(newPerson.getId(),newCure.getPerson().getId());
    }

    @Test
    public void addChecker() {

        Person p = new Person();
        p.setFirstName(fname1);
        p.setLastName(lname1);
        Cure c = new Cure();
        c.setCureName(cure1);
        c.setDescription(desc1);
        c.setPerson(p);

        Long newPersonID = manager.add(p);
        Long newCureID = manager.add(c);

        Cure newCure = manager.getCureFromID(newCureID);
        Person newPerson = manager.getPersonFromID(newPersonID);

        assertEquals(newPersonID, newPerson.getId());
        assertEquals(newCureID, newCure.getId());

    }

    @Test
    public void editChecker() {

        Person p = new Person();
        p.setFirstName(fname1);
        p.setLastName(lname1);
        Cure c = new Cure();
        c.setPerson(p);
        c.setCureName(cure1);
        c.setDescription(desc1);

        Long newPersonID = manager.add(p);
        Long newCureID = manager.add(c);

        Cure c1 = manager.getCureFromID(newCureID);
        Person p1 = manager.getPersonFromID(newPersonID);

        manager.edit(p, fname2, lname2);
        manager.edit(c, p, cure2, desc2);

        assertEquals(cure2, c1.getCureName());
        assertEquals(desc2, c1.getDescription());
        assertEquals(fname2, p1.getFirstName());
        assertEquals(lname2, p1.getLastName());
    }


}