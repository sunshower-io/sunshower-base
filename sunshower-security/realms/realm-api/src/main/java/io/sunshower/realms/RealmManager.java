package io.sunshower.realms;

import io.sunshower.model.api.User;
import io.sunshower.persistence.id.Identifier;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RealmManager extends AutoCloseable {

  String getName();

  void lock();

  void save();

  boolean isLocked();

  void unlock(CharSequence password);

  Identifier saveUser(User user);

  void saveAll(Collection<User> users);

  void deleteUser(User user);

  List<User> getUsers();

  Optional<User> findByUsername(String username);

  Optional<User> authenticate(String username, String password);

  User getUser(Identifier id);

  void setOwner(Identifier id);

  List<Identifier> getAdministrators();

  void addAdministrator(User admin);

  void removeAdministrator(User admin);

  void setOwner(User user);

  boolean isOwner(User user);
}
