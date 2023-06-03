package tb.greentea;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tb.greentea.enums.Fruit;
import tb.greentea.enums.Vegetable;

@SpringBootTest
class GreenteaApplicationTests {

	@DisplayName("시간 포맷 변환시 생각대로 되는지 보기")
	@Test
	void timeFormatTest() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

		assertThat(LocalTime.of(0,0)).isEqualTo(LocalTime.parse("00:00", fmt));
		assertThat(LocalTime.of(1,0)).isEqualTo(LocalTime.parse("01:00", fmt));
		assertThat(LocalTime.of(13,0)).isEqualTo(LocalTime.parse("13:00", fmt));
		assertThat(LocalTime.of(23,0)).isEqualTo(LocalTime.parse("23:00", fmt));

		assertThatThrownBy(() -> LocalTime.of(24,0))
			.isInstanceOf(DateTimeException.class);
		assertThatThrownBy(() -> LocalTime.of(36,0))
			.isInstanceOf(DateTimeException.class);
	}

	@DisplayName("localTimeToNumber 라는 메서드 잘되는지 테스트")
	@Test
	void localTimeToNumberMethodTest() {
		LocalTime checkin = LocalTime.of(10, 0);
		LocalTime checkout = LocalTime.of(3, 0);
		boolean isOverMidnight = true;

		LocalTime extraCheckin = LocalTime.of(13, 0);

		Function<LocalTime, Integer> localTimeToNumber =
			localTime -> {
				return localTime.getHour() * 100 + localTime.getMinute();
			};

		int begin = localTimeToNumber.apply(checkin); //1000
		int end = (isOverMidnight ? 2400 : 0) + localTimeToNumber.apply(checkout); //2400 + 300 = 2700
		int extra = localTimeToNumber.apply(extraCheckin); //1300

		assertThat(begin <= extra && extra <= end).isTrue(); //1000 <= 1300
	}

	@DisplayName("비교 구문에서 null 들어가면 어떻게 되는지 보기")
	@Test
	void nullTest() {
		Integer a = null;
		assertThatThrownBy(() -> {
			boolean b = a == 1;
		}).isInstanceOf(NullPointerException.class);
	}

	@DisplayName("datesBetween 메서드 테스트")
	@Test
	void datesBetweenTest() {
		BiFunction<LocalDate, LocalDate, List<LocalDate>> datesbetween =
			(begin, end) -> {
				return Stream.iterate(begin, date -> date.compareTo(end) <= 0, date -> date.plusDays(1))
					.collect(Collectors.toList());
			};

		assertThat(datesbetween.apply(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 1, 15)))
			.hasSize(0);

		assertThat(datesbetween.apply(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1)))
			.hasSize(1)
			.containsExactly(LocalDate.of(2023, 1, 1));

		assertThat(datesbetween.apply(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10)))
			.hasSize(10)
			.containsExactly(
				LocalDate.of(2023, 1, 1),
				LocalDate.of(2023, 1, 2),
				LocalDate.of(2023, 1, 3),
				LocalDate.of(2023, 1, 4),
				LocalDate.of(2023, 1, 5),
				LocalDate.of(2023, 1, 6),
				LocalDate.of(2023, 1, 7),
				LocalDate.of(2023, 1, 8),
				LocalDate.of(2023, 1, 9),
				LocalDate.of(2023, 1, 10));

		assertThat(datesbetween.apply(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 2, 28)))
			.hasSize(59);
	}

	@DisplayName("enum 테스트")
	@Test
	void enumTest() {
		assertThat(Vegetable.of(Fruit.APPLE)).isEqualTo(Vegetable.BROCCOLI);
		assertThat(Vegetable.of(Fruit.BANANA)).isEqualTo(Vegetable.CARROT);
		assertThat(Vegetable.of(Fruit.ORANGE)).isEqualTo(Vegetable.TOMATO);
		Vegetable vegetable = Vegetable.of(null);
		assertThat(vegetable).isNotEqualTo(Vegetable.TOMATO);
	}

	@DisplayName("java16 개선된 스위치 케이스 테스트")
	@Test
	void switchTest() {
		//given
		Function<Integer, String> fn =
			number -> switch (number) {
				case 1 -> {
					String result = "one";
					yield result + "=" + number;
				}
				case 2 -> {
					String result = "two";
					yield result + "=" + number;
				}
				default -> "etc";
			};

		//when
		String when1 = fn.apply(1);
		String when2 = fn.apply(2);
		String when3 = fn.apply(3);
		String when4 = fn.apply(4);

		//then
		assertThat(when1).isEqualTo("one=1");
		assertThat(when2).isEqualTo("two=2");
		assertThat(when3).isNotEqualTo("three=3");
		assertThat(when4).isEqualTo("etc");
	}

	@DisplayName("swtich 에 null 을 넣으면 처리 할까 ?")
	@Test
	void switchNullTest2() {

		String a = "aa";

		String b = null;

		boolean result =
			switch (a) {
				case "aa" -> true;
				case "bb" -> true;
				default -> false;
			};

		boolean result2 =
			switch (b) {
				case "cc" -> true;
				case "dd" -> true;
				default -> false;
			};

		assertThat(result).isTrue();
		assertThat(result2).isFalse();
	}

}
