package com.example.goeco_amazon.responsemodels;

import java.util.ArrayList;

public class GetLeaderboardResponse {
    private ArrayList<UserLeaderboard> leaderboard;

    public GetLeaderboardResponse(ArrayList<UserLeaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public ArrayList<UserLeaderboard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<UserLeaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
