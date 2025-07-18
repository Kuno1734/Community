package com.icom.community.project;

import com.icom.community.domain.issue.entity.Issue;
import com.icom.community.domain.issue.repository.IssueRepository;
import com.icom.community.domain.project.entity.Project;
import com.icom.community.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ProjectIssueJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 정리
        issueRepository.deleteAll();
        projectRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("기본 JPA 메서드로 Project 3개와 각 프로젝트별 Issue 2개씩 생성 및 조회 테스트")
    void createProjectsAndIssuesWithBasicJpaTest() {
        // Given: 프로젝트 3개 생성
        Project project1 = new Project(
            "프로젝트 1",
            'A',
            "한정민 대표",
            "첫 번째 프로젝트입니다.",
            LocalDateTime.now(),
            LocalDateTime.now().plusMonths(6),
            null
        );

        Project project2 = new Project(
            "프로젝트 2",
            'A',
            "류은영 부장",
            "두 번째 프로젝트입니다.",
            LocalDateTime.now(),
            LocalDateTime.now().plusMonths(3),
            null
        );

        Project project3 = new Project(
            "프로젝트 3",
            'P',
            "최은복 차장",
            "세 번째 프로젝트입니다.",
            LocalDateTime.now(),
            LocalDateTime.now().plusMonths(12),
            null
        );

        // 프로젝트들 저장
        project1 = projectRepository.save(project1);
        project2 = projectRepository.save(project2);
        project3 = projectRepository.save(project3);

        // 각 프로젝트별로 이슈 2개씩 생성
        // 프로젝트 1의 이슈들
        Issue issue1_1 = new Issue(project1, "엔지니어1", "프로젝트1 이슈1", "첫 번째 프로젝트의 첫 번째 이슈입니다.");
        Issue issue1_2 = new Issue(project1, "엔지니어2", "프로젝트1 이슈2", "첫 번째 프로젝트의 두 번째 이슈입니다.");

        // 프로젝트 2의 이슈들
        Issue issue2_1 = new Issue(project2, "엔지니어1", "프로젝트2 이슈1", "두 번째 프로젝트의 첫 번째 이슈입니다.");
        Issue issue2_2 = new Issue(project2, "엔지니어2", "프로젝트2 이슈2", "두 번째 프로젝트의 두 번째 이슈입니다.");

        // 프로젝트 3의 이슈들
        Issue issue3_1 = new Issue(project3, "엔지니어1", "프로젝트3 이슈1", "세 번째 프로젝트의 첫 번째 이슈입니다.");
        Issue issue3_2 = new Issue(project3, "엔지니어2", "프로젝트3 이슈2", "세 번째 프로젝트의 두 번째 이슈입니다.");

        // 이슈들 저장
        issueRepository.save(issue1_1);
        issueRepository.save(issue1_2);
        issueRepository.save(issue2_1);
        issueRepository.save(issue2_2);
        issueRepository.save(issue3_1);
        issueRepository.save(issue3_2);

        entityManager.flush();
        entityManager.clear();

        // When: 기본 JPA 메서드로 모든 프로젝트 조회
        System.out.println("=== findAll() 호출 ===");
        List<Project> projects = projectRepository.findAll();


        
        System.out.println("=== 프로젝트 기본 정보 출력 ===");
        for (Project project : projects) {
            System.out.println("프로젝트 ID: " + project.getId());
            System.out.println("프로젝트 이름: " + project.getProjectName());
            System.out.println("매니저: " + project.getManagerName());
        }

        // Then: 검증
        assertThat(projects).hasSize(3);
        
        // 이슈 리스트에 접근할 때 lazy loading 발생 확인
        System.out.println("=== 이슈 리스트 접근 시작 ===");
        for (Project project : projects) {
            System.out.println("프로젝트 '" + project.getProjectName() + "'의 이슈 개수: " + project.getIssueList().size());
            
            // 각 이슈의 정보 출력
            for (Issue issue : project.getIssueList()) {
                System.out.println("  - 이슈: " + issue.getSubject() + " (작성자: " + issue.getUserName() + ")");
            }
        }

        // 각 프로젝트별로 이슈 2개씩 있는지 확인
        for (Project project : projects) {
            assertThat(project.getIssueList()).hasSize(2);
        }

        // 전체 이슈 개수 확인
        long totalIssueCount = projects.stream()
            .mapToLong(p -> p.getIssueList().size())
            .sum();
        assertThat(totalIssueCount).isEqualTo(6);
    }

    @Test
    @DisplayName("Lazy Loading 동작 확인 - 이슈에 접근하지 않는 경우")
    void lazyLoadingTestWithoutAccessingIssues() {
        // Given: 프로젝트와 이슈 데이터 준비
        Project project = new Project(
            "테스트 프로젝트",
            'A',
            "테스트매니저",
            "테스트 프로젝트 내용",
            LocalDateTime.now(),
            LocalDateTime.now().plusMonths(1),
            null
        );
        
        project = projectRepository.save(project);

        Issue issue1 = new Issue(project, "테스터1", "테스트 이슈1", "테스트 이슈 내용1");
        Issue issue2 = new Issue(project, "테스터2", "테스트 이슈2", "테스트 이슈 내용2");

        issueRepository.save(issue1);
        issueRepository.save(issue2);

        entityManager.flush();
        entityManager.clear();

        // When: 프로젝트만 조회하고 이슈에는 접근하지 않음
        System.out.println("=== 프로젝트 조회만 수행 ===");
        List<Project> projects = projectRepository.findAll();
        
        System.out.println("=== 프로젝트 기본 정보만 출력 (이슈 리스트 접근 안함) ===");
        for (Project p : projects) {
            System.out.println("프로젝트: " + p.getProjectName() + ", 매니저: " + p.getManagerName());
            // 이슈 리스트에 접근하지 않음 - lazy loading 발생 안함
        }

        // Then
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getProjectName()).isEqualTo("테스트 프로젝트");
    }
}