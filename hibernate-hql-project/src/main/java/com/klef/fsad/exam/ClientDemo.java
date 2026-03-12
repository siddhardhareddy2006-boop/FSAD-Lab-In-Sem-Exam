package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ClientDemo 
{
    public static void main(String[] args) 
    {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Service.class);

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

        // Part A: Insert Record
        Service s = new Service();
        s.setId(1);
        s.setName("Initial Service");
        s.setDate("2026-03-12");
        s.setStatus("Pending");

        session.save(s);

        tx.commit();

        // Part B: Update using HQL with Named Parameters
        session.beginTransaction();

        String hql = "update Service set name=:newName, status=:newStatus where id=:id";

        Query q = session.createQuery(hql);
        q.setParameter("newName", "Updated Service");
        q.setParameter("newStatus", "Completed");
        q.setParameter("id", 1);

        int result = q.executeUpdate();
        System.out.println("Rows Updated: " + result);

        session.getTransaction().commit();

        session.close();
        factory.close();
    }
}
