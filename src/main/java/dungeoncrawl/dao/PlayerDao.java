package dungeoncrawl.dao;

import dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);
    void update(PlayerModel player);
    PlayerModel get(int id);
    List<PlayerModel> getAll();
}
