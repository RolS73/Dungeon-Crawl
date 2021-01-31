package com.codecool.dungeoncrawl.logic.actors.items;

public interface Switch extends InteractiveObject {

    String getGroupName();

    boolean isThisFromTheSameGroup(String groupName);
}
