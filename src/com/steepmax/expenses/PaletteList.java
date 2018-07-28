package com.steepmax.expenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaletteList {

	public ArrayList<LabelRecord> palette = new ArrayList<LabelRecord>();

	public PaletteList() {

		// palette.add(new LabelRecord(0, "-", 0x00FF0000));
		// palette.add(new LabelRecord(1, "Add New Label", 0x0000FF00));

		// palette.add(new LabelRecord(0, "Red", 0xFFFF0000));
		// palette.add(new LabelRecord(1, "Green", 0xFF00FF00));
		// palette.add(new LabelRecord(2, "Blue", 0xFF0000FF));

		if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("èeština")) {

			palette.add(new LabelRecord(0, "Støíbrná", 0xFFC0C0C0));
			palette.add(new LabelRecord(1, "Šedá", 0xFF808080));
			palette.add(new LabelRecord(2, "Èervená", 0xFFFF0000));
			palette.add(new LabelRecord(3, "Maroon", 0xFF800000));
			palette.add(new LabelRecord(4, "Oranž", 0xFFFFFF00));

			palette.add(new LabelRecord(5, "Oliva", 0xFF808000));
			palette.add(new LabelRecord(6, "Limetka", 0xFF00FF00));
			palette.add(new LabelRecord(7, "Zelená", 0xFF008000));
			palette.add(new LabelRecord(8, "Voda", 0xFF00FFFF));
			palette.add(new LabelRecord(9, "Teal", 0xFF008080));

			palette.add(new LabelRecord(10, "Modá", 0xFF0000FF));
			palette.add(new LabelRecord(11, "Navy", 0xFF000080));
			palette.add(new LabelRecord(12, "Fuchsie", 0xFFFF00FF));
			palette.add(new LabelRecord(13, "Fialová", 0xFF800080));
			palette.add(new LabelRecord(14, "Èerná", 0xFF000000));

			palette.add(new LabelRecord(15, "Indianská", 0xFFCD5C5C));
			palette.add(new LabelRecord(16, "Losová", 0xFFFA8072));
			palette.add(new LabelRecord(17, "Crimson", 0xFFDC143C));
			palette.add(new LabelRecord(18, "Cihlová", 0xFFB22222));
			palette.add(new LabelRecord(19, "Korálová", 0xFFF08080));

			palette.add(new LabelRecord(20, "Rùžová", 0xFFFF69B4));
			palette.add(new LabelRecord(21, "tm. Rùžová", 0xFFFF1493));
			palette.add(new LabelRecord(22, "Violet med.", 0xFFC71585));
			palette.add(new LabelRecord(23, "Violet pale", 0xFFDB7093));
			palette.add(new LabelRecord(24, "sv. Øùžová", 0xFFFFB6C1));

			palette.add(new LabelRecord(25, "Rajèatová", 0xFFFF6347));
			palette.add(new LabelRecord(26, "Oranžová", 0xFFFF4500));
			palette.add(new LabelRecord(27, "tm. Oranžová", 0xFFFF8C00));
			palette.add(new LabelRecord(28, "Oranžová", 0xFFFFA500));
			palette.add(new LabelRecord(29, "Zlatá", 0xFFFFD700));

			palette.add(new LabelRecord(30, "Rajèatová", 0xFFFF6347));
			palette.add(new LabelRecord(31, "Oranžová", 0xFFFF4500));
			palette.add(new LabelRecord(32, "tm. Oranžová", 0xFFFF8C00));
			palette.add(new LabelRecord(33, "Oranžová", 0xFFFFA500));
			palette.add(new LabelRecord(34, "Zlatá", 0xFFFFD700));

			palette.add(new LabelRecord(35, "Magenta", 0xFF8B008B));
			palette.add(new LabelRecord(36, "Indigo", 0xFF4B0082));
			palette.add(new LabelRecord(37, "tm. Modrá", 0xFF483D8B));
			palette.add(new LabelRecord(38, "Modrá", 0xFF6A5ACD));
			palette.add(new LabelRecord(39, "stø. Modrá", 0xFF7B68EE));

			palette.add(new LabelRecord(40,  "Žluto-zelená", 0xFFADFF2F));
			palette.add(new LabelRecord(41, "Jarní zelená", 0xFF00FF7F));
			palette.add(new LabelRecord(42, "Lesní zelená", 0xFF228B22));
			palette.add(new LabelRecord(43, "Žluto-zelená", 0xFF9ACD32));
			palette.add(new LabelRecord(44, "Olivová", 0xFF6B8E23));

			palette.add(new LabelRecord(45, "Turquise sv.", 0xFFAFEEEE));
			palette.add(new LabelRecord(46, "Aquamarine", 0xFF7FFFD4));
			palette.add(new LabelRecord(47, "Turquise dark", 0xFF00CED1));
			palette.add(new LabelRecord(48, "Cadet blue", 0xFF5F9EA0));
			palette.add(new LabelRecord(49, "Ocelová modø", 0xFF4682B4));

			palette.add(new LabelRecord(50, "Písková hnìdá", 0xFFF4A460));
			palette.add(new LabelRecord(51, "Zlatý prut", 0xFFDAA520));
			palette.add(new LabelRecord(52, "Tmavì zlatá", 0xFFB8860B));
			palette.add(new LabelRecord(53, "Peru", 0xFFCD853F));
			palette.add(new LabelRecord(54, "Èokoládová", 0xFFD2691E));

		} else {

			palette.add(new LabelRecord(0, "Silver", 0xFFC0C0C0));
			palette.add(new LabelRecord(1, "Gray", 0xFF808080));
			palette.add(new LabelRecord(2, "Red", 0xFFFF0000));
			palette.add(new LabelRecord(3, "Maroon", 0xFF800000));
			palette.add(new LabelRecord(4, "Yellow", 0xFFFFFF00));

			palette.add(new LabelRecord(5, "Olive", 0xFF808000));
			palette.add(new LabelRecord(6, "Lime", 0xFF00FF00));
			palette.add(new LabelRecord(7, "Green", 0xFF008000));
			palette.add(new LabelRecord(8, "Aqua", 0xFF00FFFF));
			palette.add(new LabelRecord(9, "Teal", 0xFF008080));

			palette.add(new LabelRecord(10, "Blue", 0xFF0000FF));
			palette.add(new LabelRecord(11, "Navy", 0xFF000080));
			palette.add(new LabelRecord(12, "Fuchsia", 0xFFFF00FF));
			palette.add(new LabelRecord(13, "Purple", 0xFF800080));
			palette.add(new LabelRecord(14, "Black", 0xFF000000));

			palette.add(new LabelRecord(15, "Indian red", 0xFFCD5C5C));
			palette.add(new LabelRecord(16, "Salmon", 0xFFFA8072));
			palette.add(new LabelRecord(17, "Crimson", 0xFFDC143C));
			palette.add(new LabelRecord(18, "Fire brick", 0xFFB22222));
			palette.add(new LabelRecord(19, "Light coral", 0xFFF08080));

			palette.add(new LabelRecord(20, "Hot pink", 0xFFFF69B4));
			palette.add(new LabelRecord(21, "Deep pink", 0xFFFF1493));
			palette.add(new LabelRecord(22, "Violet med.", 0xFFC71585));
			palette.add(new LabelRecord(23, "Violet pale", 0xFFDB7093));
			palette.add(new LabelRecord(24, "Light pink", 0xFFFFB6C1));

			palette.add(new LabelRecord(25, "Tomato", 0xFFFF6347));
			palette.add(new LabelRecord(26, "Orange red", 0xFFFF4500));
			palette.add(new LabelRecord(27, "Dark orange", 0xFFFF8C00));
			palette.add(new LabelRecord(28, "Orange", 0xFFFFA500));
			palette.add(new LabelRecord(29, "Gold", 0xFFFFD700));

			palette.add(new LabelRecord(30, "Tomato", 0xFFFF6347));
			palette.add(new LabelRecord(31, "Orange red", 0xFFFF4500));
			palette.add(new LabelRecord(32, "Dark orange", 0xFFFF8C00));
			palette.add(new LabelRecord(33, "Orange", 0xFFFFA500));
			palette.add(new LabelRecord(34, "Gold", 0xFFFFD700));

			palette.add(new LabelRecord(35, "Magenta", 0xFF8B008B));
			palette.add(new LabelRecord(36, "Indigo", 0xFF4B0082));
			palette.add(new LabelRecord(37, "Dark Blue", 0xFF483D8B));
			palette.add(new LabelRecord(38, "Slate Blue", 0xFF6A5ACD));
			palette.add(new LabelRecord(39, "Medium Blue", 0xFF7B68EE));

			palette.add(new LabelRecord(40, "Green yell.", 0xFFADFF2F));
			palette.add(new LabelRecord(41, "Spring green", 0xFF00FF7F));
			palette.add(new LabelRecord(42, "Forest green", 0xFF228B22));
			palette.add(new LabelRecord(43, "Yellow green", 0xFF9ACD32));
			palette.add(new LabelRecord(44, "Olive drab", 0xFF6B8E23));

			palette.add(new LabelRecord(45, "Turquise pale", 0xFFAFEEEE));
			palette.add(new LabelRecord(46, "Aquamarine", 0xFF7FFFD4));
			palette.add(new LabelRecord(47, "Turquise dark", 0xFF00CED1));
			palette.add(new LabelRecord(48, "Cadet blue", 0xFF5F9EA0));
			palette.add(new LabelRecord(49, "Steel blue", 0xFF4682B4));

			palette.add(new LabelRecord(50, "Sandy brown", 0xFFF4A460));
			palette.add(new LabelRecord(51, "Golden rod", 0xFFDAA520));
			palette.add(new LabelRecord(52, "Dark Gold", 0xFFB8860B));
			palette.add(new LabelRecord(53, "Peru", 0xFFCD853F));
			palette.add(new LabelRecord(54, "Chocolate", 0xFFD2691E));

		}
	}

}
