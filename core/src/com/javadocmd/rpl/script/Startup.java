package com.javadocmd.rpl.script;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;
import static com.javadocmd.rpl.terminal.Terminal.Alignment.CENTER;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.terminal.Puppetteer;

public class Startup implements Script {

	@Override
	public Action play(Puppetteer t) {
		return Actions.sequence(
			t.delay(1f),
			t.println("PeoplesBIOS v2"),
			t.println("SYSTEM BOOTSTRAP: COMPLETE"),
			t.delay(2f),
			
			t.println("MEM CHECK 3982 of 4024 OK"),
			t.delay(2f),
			
			t.emptyln(1),
			t.delay(SHORT),
			t.println("Detecting peripherals... OK"),
			t.delay(.5f),
			t.println("Detecting Workers pool... OK"),
			t.delay(.5f),
			t.println("Detecting Capitalist intrusion... OK"),
			t.delay(.5f),
			
			t.crawlEmpty(SHORT, 3),
			t.crawl(SHORT, CENTER,
				"                    ..                    \n" +
				"                   ....                   \n" +
				"                  ......                  \n" +
				"                 .. .....                 \n" +
				"                ..........                \n" +
				"..........................................\n" +
				"  .====================================.  \n" +
				"   ||                                ||   \n" +
				"   ||     REPUBLIK POWER & LIGhT     ||   \n" +
				"   ||                                ||   \n" +
				"   ====================================   \n" +   
				"           ....javadocmd.com...           \n" +
				"          ......................          \n" +
				"         .........      .........         \n" +
				"        ......              ......        \n" +
				"       ...                      ...       "),
			t.crawlEmpty(SHORT, 4),
			t.delay(3f)
		);
	}
}
