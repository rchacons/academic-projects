package dao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Class impl taken from teacher's solution of the TD3
 * @param <T>
 * @param <PK>
 */
public class GenericDaoJpaImpl<T, PK extends Serializable>
implements GenericDao<T,PK>{

    protected Class<T> entityClass;

    protected EntityManager entityManager;

    public GenericDaoJpaImpl(){
        this.entityManager = EntityManagerHelper.getEntityManager();
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public T save(T t) {
        this.entityManager.persist(t);
        return t;
    }

    @Override
    public T read(PK id) {
        return this.entityManager.find(entityClass,id);
    }

    @Override
    public T update(T t) {
        return this.entityManager.merge(t);
    }

    @Override
    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);

    }
}
