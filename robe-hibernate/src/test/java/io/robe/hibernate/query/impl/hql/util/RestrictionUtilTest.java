package io.robe.hibernate.query.impl.hql.util;

import io.robe.hibernate.HibernateUtil;
import io.robe.hibernate.query.HqlCriteriaTestTools;
import io.robe.hibernate.query.api.criteria.Criteria;
import io.robe.hibernate.query.api.criteria.criterion.Restriction;
import io.robe.hibernate.query.api.criteria.criterion.Restrictions;
import io.robe.hibernate.query.impl.hql.TransformerImpl;
import io.robe.hibernate.test.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

import static org.junit.Assert.*;

/**
 * Created by kamilbukum on 17/01/2017.
 */
public class RestrictionUtilTest extends HqlCriteriaTestTools {

    @Test
    public void generateRestrictionsQuery() throws Exception {

    }

    @Test
    public void generateRestrictions() throws Exception {

        Session session = sessionFactory.openSession();
        Criteria<User> criteria = Criteria.createCriteria("user", User.class, new TransformerImpl<User>(session));

        // IS NULL
        criteria.add(Restrictions.isNull("name"));
        String expectedResult = "user.name IS NULL";
        StringJoiner andJoiner = new StringJoiner(" AND ");
        StringJoiner qJoiner = new StringJoiner(" OR ");
        Map<String, Object> parameterMap = new LinkedHashMap<>();
        RestrictionUtil.generateRestrictions(criteria, criteria.getRestrictions(), andJoiner, qJoiner, parameterMap);
        assertEquals(expectedResult, andJoiner.toString());

        // IS NULL AND EQUALS
        criteria.add(Restrictions.eq("name", "Kamil","userName"));
        expectedResult = "user.name IS NULL AND user.name=:$user_userName";
        andJoiner = new StringJoiner(" AND ");
        qJoiner = new StringJoiner(" OR ");
        parameterMap.clear();
        RestrictionUtil.generateRestrictions(criteria, criteria.getRestrictions(), andJoiner, qJoiner, parameterMap);
        assertEquals(expectedResult, andJoiner.toString());

        // IS NULL AND EQUALS AND GREATER THEN
        criteria.add(Restrictions.gt("age", "Kamil","age"));
        expectedResult = "user.name IS NULL AND user.name=:$user_userName AND user.age > :$user_age";
        andJoiner = new StringJoiner(" AND ");
        qJoiner = new StringJoiner(" OR ");
        parameterMap.clear();
        RestrictionUtil.generateRestrictions(criteria, criteria.getRestrictions(), andJoiner, qJoiner, parameterMap);
        assertEquals(expectedResult, andJoiner.toString());


        // IS NULL AND EQUALS AND GREATER THEN OR (user.name EQUALS userName);
        criteria.add(Restrictions.or(new Restriction[]{Restrictions.ilike("name", "Kamil","name2"), Restrictions.gt("age", "Kamil","age2")}));
        expectedResult = "user.name IS NULL AND user.name=:$user_userName AND user.age > :$user_age AND ( user.name LIKE :$user_name2 OR user.age > :$user_age2 )";
        andJoiner = new StringJoiner(" AND ");
        qJoiner = new StringJoiner(" OR ");
        RestrictionUtil.generateRestrictions(criteria, criteria.getRestrictions(), andJoiner, qJoiner, parameterMap);
        assertEquals(expectedResult, andJoiner.toString());
    }

}