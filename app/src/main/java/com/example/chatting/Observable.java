package com.example.chatting;

import java.util.Observer;

public interface Observable {

  void registerObserver(final Observer observer);

  void unregisterObserver(final Observer observer);

  void notifyObservers();
}
