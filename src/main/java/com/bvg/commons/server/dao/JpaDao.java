package com.bvg.commons.server.dao;

import com.bvg.commons.server.domain.IEntity;
import com.bvg.commons.shared.exception.AnyServiceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
public abstract class JpaDao<E> {
    public static final int BATCH_SIZE = 50;

    public abstract Class<E> getEntityClass();

    @PersistenceContext
    protected EntityManager em;

    public Session getHibernateSession() {
        return (Session) em.getDelegate();
    }

    /**
     * AnyServiceException - будет в будущем удален
     *
     * @param entity
     * @return
     * @throws AnyServiceException
     */
    public E persist(E entity) throws AnyServiceException {
        return persist(entity, true);
    }

    public E persist(E entity, boolean needExp) throws AnyServiceException {
        log.debug(getEntityClass().getSimpleName() + " persist");
        try {
            em.persist(entity);
            em.flush();
            return entity;
        }
        catch (Exception e) {
            log.error(getEntityClass().getSimpleName() + " persist");
            if (!needExp)
                throw e;
            throw new AnyServiceException("Ошибка сохранения." + e.getLocalizedMessage(), e);
        }
    }

    /**
     * AnyServiceException - будет в будущем удален
     *
     * @param entity
     * @return
     * @throws AnyServiceException
     */
    public E merge(E entity) throws AnyServiceException {
        log.debug(getEntityClass().getSimpleName() + " merge");
        try {
            em.merge(entity);
            em.flush();
            return entity;
        }
        catch (Exception e) {
            log.error(getEntityClass().getSimpleName() + " merge");
            throw new AnyServiceException("Ошибка обновления." + e.getLocalizedMessage(), e);
        }
    }

    public void remove(E entity) {
        if (entity != null) {
            if (entity instanceof IEntity)
                log.debug(getEntityClass().getSimpleName() + " remove: " + ((IEntity) entity).getId());
            else
                log.debug(getEntityClass().getSimpleName() + " remove");
            em.remove(entity);
        }
    }

    @Transactional
    public void remove(Long id) {
        remove(findById(id));
    }

    public E findById(Object id) {
        if (id == null)
            return null;
        log.debug(getEntityClass().getSimpleName() + " findById: " + id);
        return em.find(getEntityClass(), id);
    }

    @SuppressWarnings("unchecked")
    public List<E> selectAll() {
        log.debug(getEntityClass().getSimpleName() + " selectAll");
        Query q = em.createQuery("SELECT e FROM " + getEntityClass().getName() + " e");
        return q.getResultList();
    }
}
