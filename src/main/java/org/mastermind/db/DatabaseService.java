package org.mastermind.db;

import org.mastermind.GameResults;

import java.util.List;

public interface DatabaseService {
    List<GameResults> getAllGameResultsByTime();

    void saveGameResults(GameResults results);

    boolean deleteAllGameResults();
}