package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // META-INF/persistance.xml의 설정 정보를 조회하여 "hello"라는 이름의 EntityManagerFactory 생성
        // 로딩 시점에 딱 하나만 만들어야해!!!
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        // 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 조회
//            Member findMember = em.find(Member.class, 1L);
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(0) // offset
                    .setMaxResults(2)  // limit
                    .getResultList();

            for (Member member : result) {
                System.out.println("meber.name ===== " + member);
            }
//            findMember.setName("HELLO JPA"); // 트랜잭션 커밋 직전에 값이 변경 여부 체크 후 바뀌었으면 업데이트 쿼리를 만들어서 호출
//            System.out.println("findMember.id   ==== " + findMember.getId());
//            System.out.println("findMember.name ==== " + findMember.getName());

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
