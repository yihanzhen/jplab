package com.hzyi.jplab.application;

import com.hzyi.jplab.util.Buildable;

public interface Application extends Buildable {

  static class Assembly{}
  static class Solver{}
  static class Controller{}
  static class Visualizer{}

  String getApplicationName();

  Assembly getAssembly();

  Solver getSolver();

  Controller getController();

  Visualizer getVisualizer();

}