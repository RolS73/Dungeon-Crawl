package com.codecool.dungeoncrawl.logic.actors.items.interactablilty;

public interface Switch extends InteractiveObject {

    String getGroupName();

    void setGroupName(String hiddenEnemyGroup1);

    boolean isThisFromTheSameGroup(String groupName);
}
