package run;

public class Caonima {

	public static void main(String[] args) {
		String s = "342 348 234 274 245 442 265 959 653 529 306 954 817 836 624 1044 767 705 483 781 544 547 902 387 395 497 492 1297 390 656 372 1121 532 576 559 1011 236 349 556 398 493 541 1969 646 257 313 211 788";
		String[] split = s.split(" ");
		for (int i = 0; i < split.length; i++) {
			System.out.println((i + 1) + " " + split[i]);
		}
	}

}
