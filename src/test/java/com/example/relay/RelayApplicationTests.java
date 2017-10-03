package com.example.relay;

import com.example.relay.model.Contribution;
import com.example.relay.model.User;
import com.example.relay.model.Work;
import com.example.relay.repository.ContributionRepository;
import com.example.relay.repository.UserRepository;
import com.example.relay.repository.WorkRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RelayApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private ContributionRepository contributionRepository;

	User user;
	Work work;

	@Before
	public void setup(){
		RestAssured.port = 8080;
	}

	@Test
	public void 회원가입_테스트() throws Exception {
		/*given()
				.param("email","tester@test.com")
				.param("name","tester")
				.param("password", "123")
				.when()
					.get("/addUser")
				.then()
					.statusCode(200)
					.header("Location",containsString("http://localhost:8080/index"));
*/
		User user = new User();
		user.setUsername("tester");
		user.setPassword("123");
		user.setEmail("test@test");
		userRepository.save(user);

		//확인
		User dbUser = userRepository.findOne(1L);
		assertEquals(dbUser.getUsername(),"tester");
	}

	@Test
	public void WORK_CREATE_테스트()throws Exception{
		Work work = new Work();
		work.setContent("content");
		work.setTitle("title");

		Contribution contribution = new Contribution();
		contribution.setUser(user);
		contribution.setWork(work);
		contribution.contribute("CREATE");
		//workRepository.save(work);
		contributionRepository.save(contribution);

		//확인
		Work dbWork = workRepository.findOne(1L);
		Contribution dbContribution = contributionRepository.findOne(1L);

		assertEquals(dbWork.getTitle(), "title");
		assertEquals(dbContribution.getWork().getTitle(),"title");
	}

	@Test
	public void 프로젝트참여_테스트()throws Exception{
		Contribution contribution = new Contribution();
		User creater = new User();
		creater.setUsername("creater");
		User participant = new User();
		participant.setUsername("participant");
		Work work = new Work();

		//프로젝트 생성
		contribution.setWork(work);
		contribution.setUser(creater);
		contribution.contribute("CREATE");
		contributionRepository.save(contribution);

		//프로젝트 참여
		Contribution contribution2 = new Contribution();
		contribution2.setUser(participant);
		contributionRepository.save(contribution2);

		List<Contribution> list = contributionRepository.findAll();

		for(Contribution a : list){
			System.out.println(a.getUser().getUsername()+"점수:"+a.getScore());
		}
	}

	@Test
	public void WORK_ADD_테스트()throws Exception{
		User creater = new User();
		creater.setUsername("creater");
		User participant = new User();
		participant.setUsername("participant");

		Work work = new Work();
		work.setContent("init");

		//프로젝트 생성
		Contribution contribution = new Contribution();
		contribution.setUser(creater);
		contribution.setWork(work);
		contribution.contribute("CREATE");
		contributionRepository.save(contribution);

		//프로젝트 참여
		Contribution contribution2 = new Contribution();
		contribution2.setUser(participant);
		contributionRepository.save(contribution2);

		//생성자가 내용 추가
		contribution.contribute("ADD");
		Work dbWork = workRepository.findOne(1L);
		dbWork.addContent("byCreater");
		contributionRepository.save(contribution);
		workRepository.save(dbWork);
		System.out.println("생성자추가:"+workRepository.findOne(1L).getContent());

		//참가자가 내용 추가
		contribution2.contribute("ADD");
		Work dbWork2 = workRepository.findOne(1L);
		dbWork2.addContent("byParticipant");
		contributionRepository.save(contribution2);
		workRepository.save(dbWork2);
		System.out.println("참가자추가:"+workRepository.findOne(1L).getContent());

		List<Contribution> list = contributionRepository.findAll();

		for(Contribution a : list){
			System.out.println(a.getUser().getUsername()+"점수:"+a.getScore());
		}

		System.out.println("최종추가:"+workRepository.findOne(1L).getContent());
	}


}
