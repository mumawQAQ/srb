package net.dreamparadise.srb.core;

import net.dreamparadise.srb.core.controller.admin.AdminDictController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {

  @Autowired
  private AdminDictController adminDictController;

  @Test
  public void test() {
    System.out.println(adminDictController);
  }
}
