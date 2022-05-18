package io.sunshower.realms.cryptkeeper;

import io.sunshower.arcus.condensation.Alias;
import io.sunshower.arcus.condensation.Attribute;
import io.sunshower.arcus.condensation.Convert;
import io.sunshower.arcus.condensation.Element;
import io.sunshower.arcus.condensation.RootElement;
import io.sunshower.arcus.condensation.mappings.LRUCache;
import io.sunshower.crypt.vault.IdentifierConverter;
import io.sunshower.model.api.User;
import io.sunshower.persistence.id.Identifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

@RootElement
public class UserDatabase {

  @Getter
  @Convert(IdentifierConverter.class)
  @Attribute(alias = @Alias(read = "vault-id", write = "vault-id"))
  private Identifier vaultId;

  @Getter
  @Setter
  @Attribute(alias = @Alias(read = "iv", write = "iv"))
  private String initializationVector;

  @Getter
  @Setter
  @Attribute(alias = @Alias(read = "salt", write = "salt"))
  private String salt;

  @Element
  @Convert(key = IdentifierConverter.class)
  private Map<Identifier, User> users;

  private transient Map<String, Identifier> usernameCache;


  public UserDatabase() {
    users = new HashMap<>();
    usernameCache = new LRUCache<>(128);
  }

  public UserDatabase(Identifier vaultId) {
    this();
    this.vaultId = vaultId;
  }


  public User findByUsername(String username) {
    assert users != null;
    assert usernameCache != null;

    val cached = usernameCache.get(username);
    if (cached != null) {
      val result = users.get(cached);
      if (result != null) {
        return result;
      }
    }

    for (val e : users.entrySet()) {
      val value = e.getValue();
      if (Objects.equals(value.getUsername(), username)) {
        usernameCache.put(value.getUsername(), value.getId());
        return value;
      }
    }
    return null;
  }

  public void addUser(@NonNull User user) {
    users.put(user.getId(), user);
  }


  public void removeUser(@NonNull User user) {
    usernameCache.remove(user.getUsername());
    users.remove(user.getId());
  }

  public Collection<User> getUsers() {
    return users.values();
  }


  public User getUser(Identifier id) {
    return users.get(id);
  }
}
