package br.dev.lucasena.jobs_control.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.dev.lucasena.jobs_control.domain.dtos.job.CreateJobDto;
import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.repositories.company.JpaCompanyRepository;
import br.dev.lucasena.jobs_control.utils.TestUtils;
import br.dev.lucasena.jobs_control.utils.enums.Level;
import br.dev.lucasena.jobs_control.utils.enums.Type;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
  private MockMvc mvc;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private JpaCompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
    .apply(SecurityMockMvcConfigurers.springSecurity())
    .build();
  }

  @Test
  public void should_create_a_new_job() throws Exception {
    var company = Company.builder()
        .cnpj("00000000000000")
        .email("email@email.com")
        .password("123456")
        .name("Teste")
        .website("https://companywebsite.com")
        .build();
    company = companyRepository.saveAndFlush(company);

    CreateJobDto job = CreateJobDto.builder()
        .name("Job Name")
        .description("Job Description")
        .benefits("BENEFITS_TEST")
        .level(Level.Senior)
        .salary(null)
        .type(Type.On_Site)
        .salary(3000.0f)
        .build();

    mvc.perform(MockMvcRequestBuilders.get("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJson(job))
        .header("Authorization", TestUtils.generateToken(company.getId(), "secret")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
  
  @Test
  public void should_not_create_a_new_job_if_company_not_found() throws Exception {
    CreateJobDto job = CreateJobDto.builder()
        .name("Job Name")
        .description("Job Description")
        .benefits("BENEFITS_TEST")
        .level(Level.Senior)
        .salary(null)
        .type(Type.On_Site)
        .salary(3000.0f)
        .build();

    mvc.perform(MockMvcRequestBuilders.get("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJson(job))
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "secret")))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
