package io.sunshower.common.config;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SunshowerEnvironment {

  static final Logger logger = LoggerFactory.getLogger(SunshowerEnvironment.class);

  static final String SUNSHOWER_HOME_ENV_KEY = "SUNSHOWER_HOME";
  static final String SUNSHOWER_HOME_PROPERTY_KEY = "sunshower.home";

  public static final File getSunshowerHome() {
    logger.info("Resolving Sunshower home...");

    String sunshowerHome = resolveFromProperty(SUNSHOWER_HOME_PROPERTY_KEY);
    if (sunshowerHome != null) {
      return resolveFile(sunshowerHome);
    }

    sunshowerHome = resolveFromKey(SUNSHOWER_HOME_ENV_KEY);
    if (sunshowerHome != null) {
      return resolveFile(sunshowerHome);
    }

    sunshowerHome = resolveFromDefault(".sunshower");
    if (sunshowerHome == null) {
      return new File(resolveFromDefault(sunshowerHome));
    }

    throw new IllegalStateException(
        "Sunshower home directory not found or specified.  Please start sunshower with "
            + "-Dsunshower.home=<homedir> or set the environment variable "
            + "SUNSHOWER_HOME=/path/to/sunshower/directory or modify permissions so that"
            + "Sunshower can create a directory in your home directory");
  }

  private static File resolveFile(String sunshowerHome) {
    return new File(sunshowerHome);
  }

  private static String resolveFromKey(String sunshowerHomeEnvKey) {
    logger.info("Looking for environment variable $SUNSHOWER_HOME");
    String sunshowerHome = System.getenv("SUNSHOWER_HOME");
    if (sunshowerHome == null) {
      logger.info("No SUNSHOWER_HOME set.  Looking elsewhere");
    }
    logger.info("SUNSHOWER_HOME set to '{}'", sunshowerHome);
    return sunshowerHome;
  }

  private static String resolveFromDefault(String s) {

    final String userHome = System.getProperty("user.home");
    logger.info("Using user.home='{}'", userHome);
    final File sunshowerHome = new File(userHome, s);

    if (sunshowerHome.exists() && sunshowerHome.isDirectory()) {
      logger.info("Found sunshower home: '{}'", sunshowerHome);
      return sunshowerHome.getAbsolutePath();
    }

    if (!sunshowerHome.isDirectory()) {
      logger.error("Error: sunshower home '%s' exists, but is not a directory", sunshowerHome);
      throw new IllegalArgumentException(
          String.format(
              "Error:  sunshower home '%s' exists, but is not a directory", sunshowerHome));
    }
    logger.info("sunshower home %s does not exist. Attempting to create it...");
    if (!sunshowerHome.mkdirs()) {
      logger.warn("Failed to create sunshower home at {}", sunshowerHome);
      return null;
    }
    logger.info("Successfully created sunshower home at '{}'", sunshowerHome);
    return sunshowerHome.getAbsolutePath();
  }

  private static String resolveFromProperty(String sunshowerHomePropertyKey) {
    logger.info(
        "Attempting to resolve sunshower " + "home directory: looking for -Dsunshower.home...");

    String result = System.getProperty(SUNSHOWER_HOME_PROPERTY_KEY);

    if (result == null) {
      logger.info("No sunshower.home property set...");
      return null;
    }

    final File file = new File(result);
    if (file.exists() && file.isDirectory()) {
      logger.info(
          "sunshower.home specified as property, which I've resolved to {}.  Using that...",
          file.getAbsolutePath());
      return file.getAbsolutePath();
    }

    if (!file.exists()) {
      logger.info("File {} does not exist.  Attempting to create...");
      if (file.mkdirs()) {
        logger.warn("Could not create directory {}...attempting something else", file);
      } else {
        return file.getAbsolutePath();
      }
    }
    return null;
  }
}
