package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDaoImp(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public boolean updateUser(long oldUsersId, User newUser) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, oldUsersId);
        if (user != null) {
            user.setUsername(newUser.getUsername());
            user.setRealName(newUser.getRealName());
            user.setAge(newUser.getAge());
            user.setPassword(newUser.getPassword());
            em.getTransaction().commit();
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }

    @Override
    public User getUser(long id) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }

    @Override
    public List<User> getUsersList() {

        EntityManager em = entityManagerFactory.createEntityManager();
        List<User> usersList = em.createQuery("FROM User").getResultList();
        em.close();
        return usersList;
    }

    @Override
    public void addUser(User user) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        if (user.getRoles().isEmpty()) {
            user.setRoles(em.createQuery("FROM Role WHERE name='ROLE_USER'").getResultList());
        }
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteUser(long id) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        User deletedUser = em.find(User.class, id);
        em.remove(deletedUser);
        em.getTransaction().commit();
        em.close();
    }
}
