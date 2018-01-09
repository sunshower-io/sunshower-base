package io.sunshower.test.ws;

public enum PortType {
  Random(ServerMode.Embedded),
  Mock(ServerMode.Mocked),
  None(ServerMode.Mocked),
  Defined(ServerMode.Embedded);

  final ServerMode mode;

  PortType(ServerMode mode) {
    this.mode = mode;
  }

  public boolean isEmbedded() {
    return mode == ServerMode.Embedded;
  }
}
