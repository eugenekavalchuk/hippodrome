import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class HorseTest {

  @Test
  void initHorseWithNullName() {
    var expected = "Name cannot be null.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse(null, 1)
    );

    assertEquals(expected, exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {" ", "\r", "\t", "\n"})
  void initHorseWithBlankName(String arg) {
    var expected = "Name cannot be blank.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse(arg, 1)
    );

    assertEquals(expected, exception.getMessage());
  }

  @Test
  void initHorseWithNegativeSpeed() {
    var expected = "Speed cannot be negative.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse("horse", -1)
    );

    assertEquals(expected, exception.getMessage());
  }

  @Test
  void initHorseWithNegativeDistance() {
    var expected = "Distance cannot be negative.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse("horse", 1, -1)
    );

    assertEquals(expected, exception.getMessage());
  }

  @Test
  void getName() {
    var expected = "horse";

    var horse = new Horse(expected, 1, 1);

    assertEquals(horse.getName(), expected);
  }

  @Test
  void getSpeed() {
    var expected = 1;

    var horse = new Horse("horse", expected, 1);

    assertEquals(horse.getSpeed(), expected);
  }

  @Test
  void getDistance() {
    var expected = 1;

    var horse = new Horse("horse", 1, expected);

    assertEquals(horse.getDistance(), expected);
  }

  @Test
  void getDistanceDefaultZero() {
    var expected = 0;

    var horse = new Horse("horse", 1);

    assertEquals(horse.getDistance(), expected);
  }

  @Test
  void move() {
    try (var mockStatic = Mockito.mockStatic(Horse.class)) {
      var horse = new Horse("horse", 1);

      horse.move();

      mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
    }
  }

  @ParameterizedTest
  @ValueSource( doubles = { 0.2, 0.3, 0.4 })
  void move(double value) {
    try (var mockStatic = Mockito.mockStatic(Horse.class)) {
      mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(value);
      var horse = new Horse("horse", 1, 1);
      var expected = horse.getDistance() + horse.getSpeed() * value;

      horse.move();

      assertEquals(expected, horse.getDistance());
    }
  }
}