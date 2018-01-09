package io.sunshower.test.ws.cfg;

public class DefaultTestService implements TestService {

  @Override
  public TestEntity save(TestEntity testEntity) {
    return testEntity;
  }

  @Override
  public String call(String input) {
    return String.format("Called with: %s", input);
  }
}
