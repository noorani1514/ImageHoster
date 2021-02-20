package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment addComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return comment;
    }


    public List<Comment> getComment(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> typedQuery = em.createQuery("SELECT i from Comment i where image.id =:image_id", Comment.class).setParameter("image_id", imageId);
        List<Comment> resultList = typedQuery.getResultList();
        return resultList;
    }


}



