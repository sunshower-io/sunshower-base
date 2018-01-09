package io.sunshower.common.rs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class MapAdapterTest {

  @Test
  public void ensureTransformingReversiblyRemovesWhitespace() {
    String transform = MapAdapter.transform("hello world");
    assertThat(transform, is("hello__world"));
  }

  @Test
  public void ensureTransformationIsReversible() {
    String transform = MapAdapter.untransform("hello__world");
    assertThat(transform, is("hello world"));
  }
}
