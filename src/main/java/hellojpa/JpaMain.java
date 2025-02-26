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
        // 영속성 컨텍스트 = 엔티티를 영구 저장하는 환경, 논리적인 개념
        // 엔티티 매니저를 통해 영속성 컨텍스트에 접근
        // 장점 : 1차캐시, 영속 엔티티의 동일성 보장, 트랜잭션을 지원하는 쓰기지연, 엔티티 수정 변경감지, 지연로딩
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try{
//            // 객체를 생성한 상태(비영속)
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
////            em.detach(member); // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
////            em.remove(member); // 객체를 삭제한 상태
//            System.out.println("=== BEFORE ===");
//            em.persist(member); // 객체를 저장한 상태(영속) = 1차 캐시에 저장됨
//            System.out.println("=== AFTER ===");
//
//            Member findMember = em.find(Member.class, 101L);   // 1차 캐시에서 조회
//
//            System.out.println("findMember.id === " + findMember.getId());
//            System.out.println("findMember.name === " + findMember.getName());
//
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("result === " + (findMember1 == findMember2)); // 같은 트랜잭션 안에서 엔티티를 동일하게 취급

            System.out.println("=====================");

            tx.commit(); // -> AFTER 이후에 트랜잭션 커밋 시점에서 insert 쿼리 생성

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
