import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

  static List<Horse> horses30 = new ArrayList<>();
  static List<Horse> mockHorses50 = new ArrayList<>();

  @BeforeAll
  static void init30Horses() {
    for (var i = 0; i < 30; i++) {
      horses30.add(new Horse("h" + i, i));
    }
  }

  @BeforeAll
  static void init50Horses() {
    for (var i = 0; i < 50; i++) {
      mockHorses50.add(mock(Horse.class));
    }
  }

  @Test
  void initHippodromeWithNull() {
    var expected = "Horses cannot be null.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Hippodrome(null)
    );

    assertEquals(expected, exception.getMessage());
  }

  @Test
  void initHippodromeEmpty() {
    var expected = "Horses cannot be empty.";

    var exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Hippodrome(Collections.emptyList())
    );

    assertEquals(expected, exception.getMessage());
  }

  @Test
  void getHorses() {
    Hippodrome hippodrome = new Hippodrome(horses30);

    assertArrayEquals(horses30.toArray(), hippodrome.getHorses().toArray());
  }

  @Test
  void move() {
    Hippodrome hippodrome = new Hippodrome(mockHorses50);

    hippodrome.move();

    mockHorses50.forEach(horse -> verify(horse, times(1)).move());
  }

  @Test
  void getWinner() {
    Hippodrome hippodrome = new Hippodrome(new ArrayList<>(horses30));

    hippodrome.move();

    Horse expectedWinner = hippodrome.getHorses().stream()
        .max(Comparator.comparingDouble(Horse::getDistance))
        .orElse(null);

    var bestHorse = hippodrome.getWinner();

    assertSame(expectedWinner, bestHorse);
  }
}