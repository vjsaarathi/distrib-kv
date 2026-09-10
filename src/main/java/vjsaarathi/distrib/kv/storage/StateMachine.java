package vjsaarathi.distrib.kv.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachine {
  private Map<String, String> kvStore = new HashMap<>();

  public void put(String key, String value) {
    kvStore.put(key, value);
  }

  public Optional<String> get(String key) {
    return Optional.ofNullable(kvStore.get(key));
  }

  public boolean delete(String key) {
    return kvStore.remove(key) != null;
  }

  public boolean contains(String key) {
    return kvStore.containsKey(key);
  }
}
