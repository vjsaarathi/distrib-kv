package vjsaarathi.distrib.kv.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StateMachineTest {

  // test each method individually

  @Test
  public void testPutAndGet() {
    StateMachine stateMachine = new StateMachine();
    stateMachine.put("key1", "value1");
    assertEquals("value1", stateMachine.get("key1").orElse(null));
  }

  @Test
  public void testDelete() {
    StateMachine stateMachine = new StateMachine();
    stateMachine.put("key1", "value1");
    assertEquals(true, stateMachine.delete("key1"));
    assertEquals(false, stateMachine.contains("key1"));
  }
}
