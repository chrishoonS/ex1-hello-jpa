package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
// @Table(name = "MBR") // DB의 table 이름을 "MBR"로 매핑, unique 제약조건은 여기서 사용해 줄것!
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 전략에 따라 기본키 타입 자동 생성
    private Long id;

    @Column(name = "name", nullable = false) // DDL 생성기능
//    @Column(unique=true, length = 10) // DDL 생성기능
    private String name;

    private int age;

    // enum 타입 매핑
    // ORDINAL : enum 순서를 db에 저장(요구사항에 의해 추가 되어도 옛날 데이터를 무시한채 저장, 운영 사용 X)
    // STRING : enum 이름을 db에 저장(권장)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // 날짜, 시간 관련 타입 매핑
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // 큰 컨텐츠 저장시 사용
    private String description;

    @Transient // 메모리에서만 사용, 매핑 x
    private int temp;

    // 동적 객체 생성을 위한 기본 생성자
    public Member() {}

}
